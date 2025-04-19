import React, { useState } from 'react';
import { redirect } from 'react-router-dom';
import logo from '../logo.svg';
import biu from '../biu.svg';
import './RegistrationScreen.css';
import Title from './title/Title';
import RegistrationButton from './registrationButton/RegistrationButton.js';
import { Link, useNavigate } from "react-router-dom";

function RegistrationScreen({addUser}) {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [nickname, setNickname] = useState('');
  const [error, setError] = useState('');
  const [redirect, setRedirect] = useState(false);
  const [pictureURL, setPictureURL] = useState('');

  const titles = [{title:"Join FooBar - the new Facebook",name:"b"}]
  const titleList = titles.map((title1, key)=>{
    return  <Title {...title1} key={key}/>
 })
 
 const buttons = [
  { title:"Username", buttonExplanation: "" },
  { title:"Password", buttonExplanation: "" },
  { title:"Confirm Password", buttonExplanation: "Your password must be at least p characters long and include a combination of letters and numbers to ensure strong security" }
];
 const buttonList = buttons.map((button, key)=>{
  return <RegistrationButton information={{...button}} key={key} />
})
const toLogIn = ()=> {
  navigate("/")
}
const submit = async () => {
  // Validate password
  const hasLetter = /[a-zA-Z]/.test(password);
  const hasNumber = /\d/.test(password);
  const name = /[a-zA-Z]/.test(username);
  const nick = /[a-zA-Z]/.test(nickname);
  if (password.length < 6 || !hasLetter || !hasNumber) {
    setError('Password must be at least 6 characters long and contain both letters and numbers.');
    return;
  }
  // Check password match
  if (password !== passwordConfirm) {
    setError('Passwords do not match.');
    return;
  }

  if ( !nick || !name) {
    setError('Name and nickName must contain characters.');
    return;
  }
  if ( pictureURL=='') {
    setError('You mast add picture.');
    return;
  }
   
  try {
    // Send a request to the server to check if the username already exists
    
    const response = await fetch('http://localhost:12345/api/users/exist', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username: username })
    });
    if (response.ok) {
      const data = await response.json();
     
      if (data.message === 'The username is already taken, choose another name.') {
        setError(data.message);
        return;
      } else {
        // Username is available, proceed with user creation
        addTheUser();
        // Reset form fields
        setUsername('');
        setPassword('');
        setPasswordConfirm('');
        setNickname('');
        setError('');
        setPictureURL('');
        //setRedirect(true);
        navigate("/");
      }
    } else {
      console.error('Failed to check username existence');
      // Handle error case
    }
  } catch (error) {
    console.error('Error occurred while checking username existence:', error);
    // Handle error case
  }
};

const addTheUser = async () => {
  // Create a user object
  const newUser = {
    username: username,
    password: password,
    image: pictureURL, // Set picture to the URL stored in state
    nickname: nickname,
    post: []
  };

  // Call the function to add the user
  try {
    // Send a POST request to the server to add the user
    const response = await fetch('http://localhost:12345/api/users', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newUser)
    });

    // Check if the request was successful
    if (response.ok) {
      // User added successfully
      console.log('User added successfully');
      // You might want to handle this success case in your React app (e.g., show a success message)
    } else {
      // User addition failed
      console.error('Failed to add user');
      // You might want to handle this error case in your React app (e.g., show an error message)
    }
  } catch (error) {
    console.error('Error occurred while adding user:', error);
    // You might want to handle this error case in your React app (e.g., show an error message)
  }
};

const handleImageUpload = (e) => {
  const file = e.target.files[0]; // Get the selected file
  const reader = new FileReader(); // Create a FileReader object
  reader.onloadend = () => {
    // When the file is loaded, set the image URL
    setPictureURL(reader.result);
  };

  if (file) {
    reader.readAsDataURL(file); // Read the file as a data URL
  }
};
  return (
<div className="RegistrationScreen">

  <div className="header">
    <img src={logo} className="RegistrationScreen-logo" alt="logo" />
    {titleList}
  </div>

  <div className="content">
    <div className="content1">
      <div>
        <input type="text" placeholder="UserName" className="input-field" value={username} onChange={(e) => setUsername(e.target.value)} />
      
      <div>
        
       <input type="password" placeholder="Password" className="input-field" value={password} onChange={(e) => setPassword(e.target.value)} />
      </div>
      <div>
        <input type="password" placeholder="Confirm Password" className="input-field" value={passwordConfirm} onChange={(e) => setPasswordConfirm(e.target.value)} />
      </div>
      <div>
        <input type="text" placeholder="Nickname" className="input-field" value={nickname} onChange={(e) => setNickname(e.target.value)} />
      </div>
      <div>📷📸<input type="file" accept="image/*" onChange={handleImageUpload} /> {/* Add file input for image upload */}
        </div>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <button onClick={submit}>Sign Up</button>
      <button onClick={toLogIn}>Already have an account? Sign In</button>
    </div>
    </div>
    <img src={biu} className="RegistrationScreen-biu" alt="biu" />
    </div>
 
    
  </div>

  );
}

export default RegistrationScreen;

