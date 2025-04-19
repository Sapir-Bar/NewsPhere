const postService = require('../services/post');
const net = require('net');

const createUserPost = async (req, res) => {
   console.log("enter to create post controller");
   const content = req.body.content;
   const image = req.body.image;
   const date = req.body.date;
   try {
    const safe = await checkContent(content);

    if (safe) { // Promise resolved 
        const result = await postService.createUserPost({ author: req.user._id, content, image, date });
        res.json({ status: 200, message: "Post created successfully", data: result });
    } else { // Promise rejected
        res.json({ status: 200, message: "Post uncreated", data: null });
        return;
    }
    } catch (error) {
        // Handle any errors that occur during the process
        res.status(500).json({ status: 500, message: error.message, data: null });
    }
};

const deleteUserPost = async (req, res) => {
    
    try {
        const userId = req.user._id;
        const postId = req.params.pid;
        const result = await postService.deleteUserPost(userId, postId);
        if (result) {
            res.json({status: 200, message: "Post deleted successfully", data: result});
        } else {
            res.json({status: 404, message: "Post not found", data: null });
        }
    } catch (error) {
        res.json({status: 500, message: error.message, data: null });
    }
};

const updateUserPost = async (req, res) => {
        console.log("enter to update post controller");
        const userId = req.user._id;
        const postId = req.params.pid;
        const postContent = req.body.postContent;
        const postImage = req.body.postImage;

        try {
            const safe = await checkContent(postContent);
        
            if (safe) {   
                const result = await postService.updateUserPost(userId, postId, postContent, postImage);
                // Promise resolved 
                if (result) {
                    res.json({status: 200, message: "Post updated successfully", data: result });
                } else {
                    res.json({status: 404, message: "Post not found", data: null });
                }
            } else { // Promise rejected
                res.json({ status: 200, message: "Post not updated", data: null });
                return;
            }
        } catch (error) {
            // Handle any errors that occur during the process
            res.status(500).json({ status: 500, message: error.message, data: null });
        }
}

const getFeedPosts = async (req, res) => {
    try {
        const result = await postService.getFeedPosts(req.user._id);
        if (result) {
            res.json({status: 200, message: "Posts extracted successfully", data: result});
        } else {
            res.json({status: 404, message: "Posts not found", data: null });
        }
    } catch (error) {
        res.json({status: 500, message: error.message, data: null });
    }
 }

 const getFriendPosts = async(req, res) => {
    const userId = req.user._id;
    const friendId = req.params.id;
    try {
        const result = await postService.getFriendPosts(userId, friendId);
        if (result) {
            res.json({status: 200, message: "Posts extracted successfully", data: result });
        } else {
            res.json({status: 404, message: "Posts not found", data: null });
        }
    } catch (error) {
        res.json({status: 500, message: error.message, data: null });
    }
 }

function connectToServer() {
    const serverAddress = '127.0.0.1'; 
    const serverPort = 5555;
    const sock = new net.Socket();
   
    // Connect to the server
    sock.connect(serverPort, serverAddress, () => {
        console.log('Succesfully connected');
        // Send initialization data 
        const data = "100 2";
        sock.write(data);
    });

    // Handle server response 
    sock.on('data', (data) => {
        const response = data.toString().trim();
        console.log('Server response: ', response);
        // Once the initialization is successful, add the link
        if (response === 'Bloom Filter created successfully') {
            createBlackList(sock) 
            .then(() => {
                console.log('All addresses processed successfully');
                sock.destroy();
            })
            .catch(error => {
                console.error('Error processing addresses:', error);
                sock.destroy();
            });
        } else if (response === 'Failed to create the Bloom Filter') {
            console.error('Failed to create the Bloom Filter');
            sock.destroy();
            // don't continue the server operation! 
        }
    });

    // Handle errors
    sock.on('error', (error) => {
        console.error('Error:', error.message);
        sock.destroy(); // Close the connection
    });

    // Handle connection close
    sock.on('close', () => {
        console.log('Connection closed');
        sock.destroy();
    });
}

function createBlackList(sock) {
    const addresses = ["https://example1.com", "https://example2.com", "https://example3.com"];
    let promiseChain = Promise.resolve();
    addresses.forEach(address => {
        const command = '1' + ' ' + address;
        promiseChain = promiseChain.then(() => sendToServer(command, sock));
    });
    return promiseChain;
}

function sendToServer(address, sock) { // can use for check address? 
    return new Promise((resolve, reject) => {
        sendAddressToServer(address, sock)
            .then(response => {
                // Handle server response
                console.log('Received response from server: ', response);
                resolve(response); // Resolve the promise to indicate success
            })
            .catch(error => {
                // Handle error if sending address to server fails
                console.error('Error sending address to server: ', error);
                reject(error); // Reject the promise to indicate failure
            });
    });
}

function sendAddressToServer(address, sock) {
    return new Promise((resolve, reject) => {
        sock.once('data', (data) => {
            const response = data.toString().trim();
            resolve(response);
        });    
        sock.write(address);
    });  
}

const checkContent = async (content) => {

    const linkRegex = /https?:\/\/[^\s]+/g;
    // 'match' returns an array containing all the matches found in the given post's content
    const links = content.match(linkRegex);
    // No links found, content is safe
    if (!links) { 
        return true;
    // found at least 1 address -> check or safety
    } else {

        const serverAddress = '127.0.0.1'; 
        const serverPort = 5555;
        const sock = new net.Socket();

        // Handle errors
        sock.on('error', (error) => {
            console.error('Error:', error.message);
            sock.destroy(); // Close the connection
        });

        // Handle connection close
        sock.on('close', () => {
        console.log('Connection closed');
        sock.destroy();
        });
    
        // Establish connection to the server
        sock.connect(serverPort, serverAddress, () => {
        console.log('Succesfully connected');
        });

        let promiseChain = Promise.resolve();

        // Loop through each link
        links.forEach(address => {
            const command = '2' + ' ' + address;
            promiseChain = promiseChain.then(() => sendToServer(command, sock))
                .then(response => {
                        if (response === "true true") {
                            console.log(`Link ${address} is not safe.`);
                            return Promise.reject(new Error('Link ${address} is not safe.'));
                        } else {
                            console.log(`Link ${address} is safe.`);
                            return Promise.resolve;
                        }
                    });
            });

        return promiseChain.finally(() => {
            sock.destroy(); // Close the connection after all links are checked
        });
    }
}

module.exports = { createUserPost, updateUserPost, deleteUserPost, getFeedPosts, getFriendPosts, connectToServer, createBlackList, sendToServer,
     sendAddressToServer, checkContent }