
import Post from './post/Post'; // Assuming the location of the Post component file
import logo from '../logo.svg';
import Search from './search/Search';
import NewPost from './newpost/NewPost';
import Menu from './menu/Menu';
import './Feed.css';
import avatar from '../avatar.jpg';
import avatarr from '../avatarr.jpg';
import Profile_Image from '../Profile Image.png';
import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from "react-router-dom";
//import postsdata from './posts.json';
import PostList from './postlist/PostList';
function Feed({myUser,token,setPageOwner}) {//my user is token
    const [posts, setPosts] = useState([]);
    const [mytext,setMytext]=useState('');
    const [imageurl, setImegeurl]=useState('');
    const [searchQ,setSearchQ]= useState('');
    const [theRealList,setTheRealList]= useState([]);
    {/*set dark or light */}
    const [isDarkMode, setisDarkMode] = useState(false);
    const navigate = useNavigate();
    const [toUpdateFeed, setToUpdateFeed]= useState(0)
    const [darklight,setDarkLight]=useState('Feed')
   // const [token,setToken]=useState(token1);
  //  useEffect(()=>{setToken(token1)},[token])
    /*useEffect(() => {
    
        setPosts(postsdata);
    }, []);*/
   /* const addPost = (details) =>{
        const image = imageurl;
        //alert("app:  " + myUser.nickname)
        const post = {
            content: details.content,
            image : details.imageUrl,
            title: details.title,
            //author_nickname: details.author_nickname,
            author_nickname: myUser.nickname,
            icon: image,
            date: new Date().toLocaleString(),
            author_image: avatarr
        };
        


        // Update the state with the updated list of posts
        setPosts([post, ...posts]);
        setMytext('');
        setImegeurl('');
        setTheRealList(prevList => [post, ...prevList]);
    }*/
    useEffect(() => {
        if(token==""){
            navigate('/')
        }
        const fetchPosts = async () => {
            try {//console.log("feed-token:",token)
                //const token = await fetch('http://localhost:12345/api/posts')
                const response = await fetch('http://localhost:12345/api/posts', {
                    'headers': {
                    'Content-Type': 'application/json',
                    'authorization': 'Bearer ' + token // attach the token
                    },
                    });
                //console.log("feed posts response:",response)
                if (response.ok) {
                    const data = await response.json();
                   // console.log("Feed",data)
                    setPosts(data); // Update state with fetched posts
                    //console.log(data)
                } else {
                    console.error('Failed to fetch posts');
                }
            } catch (error) {
                console.error('Error occurred while fetching posts:', error);
            }
        };

        fetchPosts();

        return () => {
            // Cleanup logic here (if needed)
        };
    }, [toUpdateFeed])
     // Empty dependency array to run the effect only once

    const addPost = async (details) => {
        //alert(myUser.nickname)
        try {
            const post = {
                content: details.content,
                image: details.imageUrl,
                /*title: details.title,*/
                author_nickname: myUser.nickname,
                icon: imageurl,
                date: new Date(),
                author_image: myUser.image
            };
           // console.log(post.image)
    
            // Send a POST request to the server to add the post
            const response = await fetch('http://localhost:12345/api/users/:id/posts', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': 'Bearer ' + token 
                },
                body: JSON.stringify(post)
            });
            
            if (response.ok) {
                const responseData = await response.json(); // Parse JSON response
                console.log('response.ok');
                console.log('responseData.message',responseData.message);
                if (responseData.message == "Post uncreated"){
                    console.log('Post contain Malicious link');
                    alert("This post contain Malicious link and cannot be added")
                    setToUpdateFeed(toUpdateFeed+1)
                }
                //console.log(responseData)
                //setPosts(prevPosts => [responseData, ...prevPosts]);
                //fetchPosts();
                // Handle success (if needed)
            } else {
                console.error('Failed to add post');
                // Handle failure (if needed)
            }
            //fetchPosts();
        } catch (error) {
            console.error('Error occurred while adding post:', error);
            // Handle error (if needed)
        }
        setToUpdateFeed(toUpdateFeed+1)
    };
   
 /*   const updatePostInList = (updatedPost) => {
        const updatedPosts = posts.map(post => {
            if (post.postID === updatedPost.postID) {
                return updatedPost;
            }
            return post;
        });
        setPosts(updatedPosts);
    };*/
    const updatePostInList = async (updatedPost) => {
        const postId = updatedPost.postID
        console.log("postid", updatedPost)
        try {
            const postId = updatedPost._id;
            console.log("postId:", postId); // Log postId to verify it's not undefined
            const response = await fetch(`http://localhost:12345/api/users/posts/${postId}`, {
          method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': 'Bearer ' + token 
                },
                body: JSON.stringify({postId,...updatedPost})
            });
/*  if (response.ok) {
                const responseData = await response.json(); // Parse JSON response
                console.log('response.ok');
                console.log('responseData.message',responseData.message);
                if (responseData.message == "Post uncreated"){
                    console.log('Post contain Malicious link');
                    alert("This post contain Malicious link and cannot be added")
                }*/
            if (response.ok) {
                const responseData = await response.json(); // Parse JSON response
                console.log('response.ok');
                console.log('responseData.message',responseData.message);
                if (responseData.message == "There is a link"){
                    console.log('Post contain Malicious link');
                    alert("The content you are trying to insert contains malicious links. In order to protect the other users, we will not allow the address to be distributed. The published content will remain the same as the content before the update. You will be able to see this when you log back in.")
                }
                if (responseData.message == "Post uncreated"){
                    console.log('Post contain Malicious link');
                    alert("The content you are trying to insert contains malicious links. In order to protect the other users, we will not allow the address to be distributed, if you want to continue using the application, you will have to log in again.")
                    navigate("/")
                }
            } else {
                console.error('Failed to update post');
            }
        
        } catch (error) {
            console.error('Error occurred while updating post:', error);
        }
        setToUpdateFeed(toUpdateFeed+1)
    };   

    const deletePost = async (postId) => {
        try {
            
            const response = await fetch(`http://localhost:12345/api/users/posts/${postId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token 
                }
            });
    
            const responseData = await response.json();
    
            if (response.ok) {
                console.log('Post deleted successfully');
                // Optionally, you can perform any additional actions after successful deletion
            } else {
                console.error('Failed to delete post:', responseData.message);
                // Handle failure (if needed)
            }
        } catch (error) {
            console.error('Error occurred while deleting post:', error);
            // Handle error (if needed)
        }
        setToUpdateFeed(toUpdateFeed+1)
    };
    
    const friend = (owner) => {
        setPageOwner(owner)
        navigate("/personalPage");
    };
    
    const doSearch = (q) => {
      //  setPosts(theRealList) // Update the search query state
      setSearchQ(q);
      // You may need to fetch data from the server based on the search query
        
        // Filter posts based on the current search query
      //  const filteredPosts = postsdata.filter((post) => post.content.includes(q));
      //  setPosts(filteredPosts);
    }
    
    const on_off = () => {
        if(isDarkMode){
          setDarkLight('Feed')
          setisDarkMode(false)
          return;
        }
        if( !isDarkMode) {
            setDarkLight('DarkModeFeed')
            setisDarkMode(true)
            return;
          }
    };
    const signOut=()=>{
       // console.log("logout")
        navigate("/")
    };
    const myspace=()=>{
         
         navigate("/myPage")
     };


    return (
         <div className={darklight}>
            <div className="header">
                <button onClick={myspace} data-testid="dark-mode-button-dark">my place</button>
                <img src={logo} className="Feed-logo" alt="logo" />
                <Search setSearchQ={doSearch} />
                <button onClick={signOut} data-testid="dark-mode-button-dark">sign out</button>
                <div className='feeddarkmodebutton'>
                {isDarkMode?( <button onClick={() => on_off()} data-testid='dark-mode-button-light'>Light Mode</button>
                          ):( <button onClick={() => on_off()} data-testid="dark-mode-button-dark">Dark Mode</button>)}
               </div>
              
            </div>
            <div className="content">
                <Menu user = {myUser}/>
                <NewPost addPost={addPost} />
                <div className='posts'>
                    <PostList posts = {posts}  updatePost={updatePostInList} deletePost={deletePost} friend={friend}/>
                </div>
            </div>
        </div>
    );
}

export default Feed;

