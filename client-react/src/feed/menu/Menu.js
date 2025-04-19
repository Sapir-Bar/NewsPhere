import React from 'react';
import './Menu.css';

function Menu({user}) {
  console.log("nickname",user.nickname)
  return (
    <div className="Menu">
      <div className="vertical-menu">
        <button>{user.nickname}(me)</button>
        <button>News Feed</button>
        <button>Messages</button>
        <button>Events</button>
        <button>Close Friends</button>
      </div>
    </div>
  );
}

export default Menu;
