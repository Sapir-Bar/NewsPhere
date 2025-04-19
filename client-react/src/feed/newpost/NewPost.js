import './NewPost.css';
import React, { useState } from 'react';

function NewPost({ addPost, user }) {
  const [mytext, setMytext] = useState('');
  const [imageurl, setImageurl] = useState('');

  const handleImageUpload = (e) => {
    const file = e.target.files[0]; // Get the selected file
    const reader = new FileReader(); // Create a FileReader object
    reader.onloadend = () => {
      // When the file is loaded, set the image URL
      setImageurl(reader.result);
    };

    if (file) {
      reader.readAsDataURL(file); // Read the file as a data URL
    }
  };

  const add_Post = () => {
    const content = mytext;

    const post = {
      content: mytext,
      imageUrl: imageurl, // Use the image URL state directly
      author_nickname: "Alice",
      icon: imageurl, // We dont use this line.

      date: new Date().toLocaleString(),
      author_image: "details.avatar"
    }
    addPost(post)
    setMytext('')
    setImageurl('');
  };

  return (
    <div className="container">
      <div id="textPost" className="section">
        <input type="text" placeholder=" What's on your mind?" className="input-field" value={mytext} onChange={(e) => setMytext(e.target.value)} />

        <hr className="divider" />
        <div id="button" className="section">
          
          <button onClick={() => add_Post()}>Publish</button>
        </div>
      </div>

      <hr className="divider" />

      <div id="Add image" className="Add image">
        <h2>Add image   </h2>
        <input type="file" accept="image/*" onChange={handleImageUpload} /> {/* Add file input for image upload */}
        <hr className="divider" />
        <h2>Tag friends   </h2>
        <hr className="divider" />
        <h2>Feeling or activity   </h2>
      </div>
    </div>
  );
}

export default NewPost;
