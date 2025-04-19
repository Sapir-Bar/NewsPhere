import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import RegistrationScreen from '../registrationscreen/RegistrationScreen';
import LoginScreen from '../loginscreen/LoginScreen';
import Feed from '../feed/Feed';
import MyPage from '../mypage/myPage';

import PersonalPage from '../personalpage/PersonalPage';


function App() {
  const [myUser,setUser]= useState([]);
  const[users, setUsers]= useState([]);
  const[myToken, setToken]= useState([]); 
  const[pageOwner,setPageOwner]= useState([]);
  const addUser = (user) => {
        setUsers([...users, user]);
  };
  const setToken1 = (token) => {
    //console.log("app-token: ", token)
    setToken(token);
};
  const setUser1 = (user) => {
   // alert("app" + user.nickname)
    setUser( user);
    //console.log("app-user",user);
   
};

const setPageOwner1 = (owner) => {
  // alert("app" + user.nickname)
   setPageOwner( owner);
   console.log("myuser", myUser)
   //console.log("app-user",user);
};

  return (
    
    <Router>
      <Routes>
      
          <Route path="/register" element={<RegistrationScreen addUser={addUser}/>} />
          <Route path="/" element={<LoginScreen users={users} setUser={setUser1} setToken={setToken1} />} />
          <Route path="/feed" element={<Feed myUser={myUser } token={myToken} setPageOwner={setPageOwner1}/>} />
          <Route path="/personalPage" element={<PersonalPage PageOwner={pageOwner } token={myToken}/>} />
          <Route path="/myPage" element={<MyPage myUser={myUser} token={myToken}/>} />
        
      </Routes>
    </Router>
    
  );
}

export default App;