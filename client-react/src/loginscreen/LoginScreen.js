import logo from '../logo.svg';
import biu from '../biu.svg';
import './LoginScreen.css';
import { useState } from 'react';
import { redirect } from 'react-router-dom';
import { Link, useNavigate } from "react-router-dom";


function LoginScreen({toggleForm,users,setUser, setToken}) {
  const [redirectToFeed, setRedirectToFeed] = useState(false);
  const navigate = useNavigate();
  const [error, setMessage] = useState('');
  //const [userToken, setUserToken] = useState(null);


 /* const enterFeed = () => {
    const enteredUsername = document.querySelector('input[type="text"]').value;
    const enteredPassword = document.querySelector('input[type="password"]').value;
  
    // Check if there's a user with the entered username and password
    const user = users.find(user => user.username === enteredUsername && user.password === enteredPassword);
    
    if (user) {
      setUser(user)
      // If user found, navigate to feed
      navigate("/feed");
      setRedirectToFeed(true);
    } else {
      // If no matching user found, you can set an error state or handle it as you like
      setError("Invalid username or password");
      return;
    }
  };*/

  const enterFeed = async () => {
    const enteredUsername = document.querySelector('input[type="text"]').value;
    const enteredPassword = document.querySelector('input[type="password"]').value;
   
    try {
     // const response = await fetch('http://localhost:12345/users/login', {
      const response = await fetch('http://localhost:12345/api/tokens', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
          },
          
          body: JSON.stringify({ username: enteredUsername, password: enteredPassword }), // Fixing the variable names here
      });
      console.log("response after login1:",response)
      const theResponse = await response.json();
      
      if (response.ok) {
          console.log(theResponse.data)
          
          const  {token}  =  theResponse.data;
          console.log("token:", token)
          //setUserToken(token);
          setMessage(theResponse.message);
          //todo - make theResponse.user available
          // If user found, navigate to feed
          if(theResponse.message == "Login successful"){
            console.log("response after login2:", theResponse)
            //setUser(theResponse.user)
            setUser(theResponse.user)
            setToken(token)
            
            //alert(theResponse.user.username)
            navigate("/feed");
          
        }
         // setRedirectToFeed(true);
          // Redirect or perform any other action upon successful login
      } else {
          setMessage(theResponse.message);
      }
  } catch (error) {
      console.error('Error logging in:', error);
      setMessage('An error occurred while logging in.');
  }
};


const registerScreen = () => {
  // Toggle between login and registration screens
  navigate("/register");
};
if (redirectToFeed) {
  return <redirect to="../feed/Feed.js" />;
}
  return (
<div className="LoginScreen">

      <div className="header">
          <img src={logo} className="LoginScreen-logo" alt="logo" />
          <div className="signUpt">You don't have an account?</div>

          <button onClick={registerScreen}> Sign Up</button>
      </div>
      <div className="content">
      <div className="login">
          Login
        </div>
      <div className="input-container">
          <input type="text" placeholder="Username" className="input-field" />
        </div>
        <div className="input-container">
          <input type="password" placeholder="Password" className="input-field" />
          {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
        <button onClick={enterFeed}>Login ❀</button>
        
      </div>
</div>
  );
}

export default LoginScreen;

