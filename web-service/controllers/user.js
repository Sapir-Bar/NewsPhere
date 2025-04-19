const { connect } = require('mongoose');
const userService = require('../services/user');
const jwt = require('jsonwebtoken')
const customEnv = require('custom-env');
const user = require('../models/user');
customEnv.env(process.env.NODE_ENV, '../config');

const createUser = async (req, res) =>{
    const username = req.body.username
    const password = req.body.password
    const nickname = req.body.nickname
    const image = req.body.image
    const posts = req.body.posts || []; // Set to empty array if not provided
    const friends = req.body.friends || [] // Set to empty array if not provided
    result = await userService.createUser({ username, password, nickname, image, posts, friends });
    return res.json({message: 'User Created Successfully', data: result, status: 200 });
 };
 
const toggleFriend = async (req,res) => {
    const userId = req.user._id
    const friendId = req.params.friendId
    res.json(await userService.toggleFriend(userId, friendId))
}

 const checkUserExistence = async (req, res) => {
    const { username } = req.body;
    try {
        const users = await userService.getUsers(); 
       
        // Check if username match any of the users
        const foundUser = users.find(user => user.username === username);

        if (foundUser) {
            return res.json({ message: 'The username is already taken, choose another name.', data: foundUser, status: 200}); 
        } else {
            return res.json({ message: 'Username available', data: null, status: 401 }); 
        }
    } catch (error) {}
};

const getUsers = async (req, res) => {
    result = await userService.getUsers();
    if (result) {
        return res.json({ message: 'Users found successfuly', data: result, status: 200});
    } else {
        res.json({ message: 'Users not found', data: null, status: 401 });
    } 
};

const updateUser1 = async (req, res) => {
   const { id } = req.params;
   const { username, password,nickname, image, posts } = req.body;

   try {
       const userToUpdate = await userService.getUserById(id);
       if (!userToUpdate) {
           return res.status(404).json({ message: "User not found" });
       }

       const updatedUser = await userService.updateUser(id, {
           username: username || userToUpdate.username,
           password: password || userToUpdate.password,
           nickname: nickname || userToUpdate.nickname,
           image: image || userToUpdate.image,
           posts: posts !== undefined ? posts : userToUpdate.posts
       });

       res.json(updatedUser);
   } catch (error) {
       res.status(400).json({ message: error.message });
   }
};
const addFriend = async (req, res) => {
    const userId = req.user._id; // Assuming req.user._id contains the current user's ID from the authentication token
    const friendId = req.params.id; // Assuming the friend's ID is provided in the URL parameter
    const result = await userService.addFriend(userId, friendId);
    res.json({ status: 200, message: "friendship created successfully", data: result })
}
const updateUser = async (req, res) => {
    try {
        const userId = req.user._id;
        
        const postContent = req.body.name;
        const postImage = req.body.Image;
        const result = await userService.updateUser(userId, postContent, postImage);
        if (result) {
            res.json({status: 200, message: "User updated successfully", data: result});
        } else {
            res.json({status: 404, message: "Post not found", data: null });
        }
    } catch (error) {
        res.json({status: 500, message: error.message, data: null });
    }
};

const login = async (req, res) => {
    const { username, password } = req.body;
    try {
        const users = await userService.getUsers(); 
        const foundUser = users.find(user => user.username === username && user.password === password);

        if (foundUser) {
            const data = { _id: foundUser._id }
            // Generate the token.
            const token = jwt.sign(data, process.env.JWT_SECRET)

            // Return the token to the browser       
            return res.json({ message: 'Login successful', data: {token},user:foundUser, status: 200}); 
        } else {
            return res.json({ message: 'The username or password are incorrect, try again or create a new user.', data: null, status: 404}); // Return early after sending response
        }
    } catch (error) {
        return res.json({ message: 'Error occurred while logging in', data: null, status: 500}); 
    }
};

const me = async (req, res) => {
    const id = req.user._id
        try {
            const user = await userService.me(id);
            res.status(200).json({message: "User details", data: user, status: 200});
        } catch (error) {
            res.status(400).json({ message: error.message });
        }
} 


module.exports = { createUser, getUsers, updateUser, checkUserExistence, login, toggleFriend, me,updateUser, addFriend};