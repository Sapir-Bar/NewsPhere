
import React, { useState } from 'react';

function EditPost({ editPost }) {
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

  const edit_Post = () => {
    const content = mytext;

    const post = {
      content: mytext,
      imageUrl: imageurl, // Use the image URL state directly
      title: "hi",
      author: "Sapir",
      icon: imageurl, // Assuming you want the icon to be the same as the post image

      date: new Date().toLocaleString(),
      avatarUrl: "details.avatar"
    }
    editPost(post)
    setMytext('')
    setImageurl('');
  };

  return (
    <div className="container">
      <div id="textPost" className="section">
        <input type="text" placeholder=" What's on your mind?" className="input-field" value={mytext} onChange={(e) => setMytext(e.target.value)} />

        <hr className="divider" />
        <div id="button" className="section">
          
          <button onClick={() => edit_Post()}>Publish</button>
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

export default EditPost;
