import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from "react-router-dom";
import './myPage.css';
import Search from '../feed/search/Search';

import '../personalpage/PersonalPage.css';


function MyPage( {myUser, token} ) {
    const [nickname, setNickname] = useState('');
    const [image, setImage] = useState('');
    const [tempImage,setTempImage] = useState('');
    const [friends, setFriends] = useState([]);
    const navigate = useNavigate();
    const [imageurl, setImegeurl] = useState('');
    const [searchQ, setSearchQ] = useState('');
    {/*set dark or light */}
    const [isDarkMode, setisDarkMode] = useState(false);
    const [darklight, setDarkLight] = useState('Feed');

    const [NewNickname, setNewNickname] = useState(myUser.nickname);
    const [tempNewNickname, settempNewNickname] = useState(myUser.nickname);
    
    
   // const [myUser,setmyUser]= useState('');
    //useEffect(()=>{setEditedContent(setmyUser)},[myUser])

    useEffect(() => {
        if(token==""){
            navigate('/')
        }
        // Fetch user details and friends list from the server
        // Example API call to fetch user details
        //fetchUserDetails();
        // Example API call to fetch friends list
       // fetchFriendsList();
      // settempNewNickname(myUser.nickname)
    }, []);
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
       // console.log("mypage:",myUser)
        navigate("/feed");
    };
    const doSearch = (q) => {

        setSearchQ(q);
  
      }
    const fetchUserDetails = async () => {
        try {
            // Example fetch request to get user details
            const response = await fetch('http://localhost:12345/api/user/details', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + myUser.token
                }
            });
            if (response.ok) {
                const data = await response.json();
                settempNewNickname(data.nickname);
                setImage(data.image);
            } else {
                console.error('Failed to fetch user details');
            }
        } catch (error) {
            console.error('Error occurred while fetching user details:', error);
        }
    };

    const fetchFriendsList = async () => {
        try {
            // Example fetch request to get friends list
            const response = await fetch('http://localhost:12345/api/user/friends', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + myUser.token
                }
            });
            if (response.ok) {
                const data = await response.json();
                setFriends(data);
            } else {
                console.error('Failed to fetch friends list');
            }
        } catch (error) {
            console.error('Error occurred while fetching friends list:', error);
        }
    };

    const handleEdit = () => {
        // Handle edit profile logic, navigate to edit profile page
        navigate('/edit-profile');
    };

    const handleCancel = () => {
        
        // Reset edited content and image URL to original values
        settempNewNickname(NewNickname)
        setTempImage(imageurl)
    };
    const  handleSave  = async () => {
        
        // Reset edited content and image URL to original values
        settempNewNickname(NewNickname)
        setTempImage(imageurl)
      
        try {
            console.log("tempImage",tempImage)
            console.log("NewNickname",NewNickname)
            const response = await fetch(`http://localhost:12345/api/users/:id`, {
          method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': 'Bearer ' + token 
                },
                body: JSON.stringify({name: tempNewNickname,Image: tempImage})
            });

            if (response.ok) {
                console.log('Post updated successfully');
                //setPosts(prevPosts => prevPosts.map(post => post.postID === updatedPost.postID ? updatedPost : post));
            } else {
                console.error('Failed to update post');
            }
        } catch (error) {
            console.error('Error occurred while updating post:', error);
        }
        navigate("/");
    }; 

    const goToFriendPage = (friendId) => {
        // Navigate to friend's personal page
       navigate(`/friend/${friendId}`);
    };

    if (!myUser) {
        return <div>Loading...</div>; // or any other loading state
    }

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        const reader = new FileReader();
        reader.onloadend = () => {
            setTempImage(reader.result);
        };
        if (file) {
            reader.readAsDataURL(file);
            setTempImage(reader.result);
        }
    };

    return (    
         <div className={darklight}>
            <div className="header">
            <span className="content111" onClick={home}><span className="iconn"></span> 🏠</span>
                <img src={myUser.image} className="Feed-logo" alt="logo" />
               
                <div className='content111' >{tempNewNickname} </div>
                <Search setSearchQ={doSearch} />
                

                <div className='feeddarkmodebutton'>
                {isDarkMode?( <button onClick={() => on_off()} data-testid='dark-mode-button-light'>Light Mode</button>
                          ):( <button onClick={() => on_off()} data-testid="dark-mode-button-dark">Dark Mode</button>)}
               </div>
            </div>
            
            <div className="content">
     
            <div className='content111' >{tempNewNickname} </div>
        
            Here you can edit your details to keep them up to date:
            <div></div>
            <div></div>
            <div>. . . . . .</div>
            <div>. .  . . . . . . .</div>
            <div>. . . . . . </div>
            <div>.  . . .</div>
            <div>..</div>
            

           <div>
                    <textarea
                        className="edit-content"
                        value={tempNewNickname}
                        onChange={(e) => settempNewNickname(e.target.value)}
                    />
                    <input
                        type="file"
                        accept="image/*"
                        onChange={handleImageChange}
                    />
                    {tempImage && (
                        <img src={tempImage} alt="Selected" className="post-image" />
                     )}
                    {/*editedImageUrl && <img src={editedImageUrl} alt="Post Image" className="post-image" />*/}
                    <button onClick={handleSave}>Save</button>
                    <button onClick={handleCancel}>Cancel</button>
                </div>
                Once you click save you will have to reconnect to the app and the user will be updated💗
                </div>
            
        </div>

    );
}

export default MyPage;
