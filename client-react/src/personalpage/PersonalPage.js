import Post from '../feed/post/Post'; // Assuming the location of the Post component file
import logo from '../logo.svg';
import Search from '../feed/search/Search';
import NewPost from '../feed/newpost/NewPost';
import Menu from '../feed/menu/Menu';
import './PersonalPage.css';
import avatar from '../avatar.jpg';
import avatarr from '../avatarr.jpg';
import Profile_Image from '../Profile Image.png';
import React, { useState, useEffect } from 'react';
//import postsdata from './posts.json';
import PostList from '../feed/postlist/PostList';
import { Link, useNavigate } from "react-router-dom";

function PersonalPage({ PageOwner,token }) {
    const [posts, setPosts] = useState([]);
    const [mytext, setMytext] = useState('');
    const [imageurl, setImegeurl] = useState('');
    const [searchQ, setSearchQ] = useState('');
    const [theRealList, setTheRealList] = useState([]);
    const navigate = useNavigate();
    {/*set dark or light */}
    const [isDarkMode, setisDarkMode] = useState(false);
    const [darklight, setDarkLight] = useState('Feed');
    const [isfriend, setIsFriend] = useState(true);

    useEffect(() => { 
    const fetchFriendPosts = async () => {
        try {
            const friendId = PageOwner._id; // Replace with the actual friend's user ID
            console.log("friendid",friendId)

           /* const response = await fetch(`http://localhost:12345/api/users/:id/posts/${friendId}`, {*/
           const response = await fetch(`http://localhost:12345/api/users/${friendId}/posts`, {
                method: 'GET',
                headers: {
                    // Replace 'your_token' with the actual token
                    'Content-Type': 'application/json',
                    'authorization': 'Bearer ' + token
                } 
            });
            console.log("friendpost",response)

            if (!response.ok) {
                throw new Error('Failed to fetch friend posts');
            }

            const data1 = await response.json();
            
            const theposts= await data1.data
            if(theposts==null){
                setIsFriend(false)
            }
            setPosts(data1);
            console.log(posts)
        } catch (error) {
            console.error('Error fetching friend posts:', error);
        }
    };
   fetchFriendPosts();
   return () => {
    // Cleanup logic here (if needed)
};
}, [])
   
    const doSearch = (q) => {

      setSearchQ(q);

    }
    
    const updatePostInList= ()=>{
        alert("You can only update your own posts. ")
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

    const friend = () => {
        alert("You can only enter frind page from home page. press 🏠 ")
    };

    const  deletePost= () => {
        alert("You can only delete your own posts. ")
    };
    
    
    const home = () => {
        navigate("/feed");
    };
    const friendreq = () => {
        const friendId = PageOwner._id;
    
        try {
            const response = fetch(`http://localhost:12345/api/users/${friendId}/friends`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                }
            });
    
            if (response.ok) {
                console.log('Friend request sent successfully');
                // Additional logic if needed
            } else {
                console.error('Failed to send friend request');
            }
        } catch (error) {
            console.error('Error occurred while sending friend request:', error);
        }
        navigate("/feed");
    }; 
    
    return (
         <div className={darklight}>
            <div className="header">
                <img src={PageOwner.image} className="Feed-logo" alt="logo" />
                <span className="content1" onClick={home}><span className="iconn"></span> 🏠</span>
                <div className='content1' >{PageOwner.nickname} </div>
                <Search setSearchQ={doSearch} />
                

                <div className='feeddarkmodebutton'>
                {isDarkMode?( <button onClick={() => on_off()} data-testid='dark-mode-button-light'>Light Mode</button>
                          ):( <button onClick={() => on_off()} data-testid="dark-mode-button-dark">Dark Mode</button>)}
               </div>
            </div>
            
            <div className="content">
                
              
                <div className='friend posts'>
                {isfriend?(<div className='posts'>
                   
                    <PostList posts = {posts}  updatePost={updatePostInList} deletePost={deletePost} friend={friend}/>
                </div>
                          ):( <div className = 'friend reqwest'>
                            
                            {PageOwner.image && ( <img src={PageOwner.image} alt="Post Image" className="post-image" />  )}
                          <button onClick={() => friendreq()}>friend request</button>
                          </div>)
                          }
               </div>
                
                
            </div>
            
        </div>
    );
}

export default PersonalPage;
