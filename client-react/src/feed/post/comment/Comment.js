import React, { useState } from 'react';
import './Comment.css';

function Comment({ comment, index, onEdit }) {
  const [isEditing, setIsEditing] = useState(false);
  const [editedText, setEditedText] = useState(comment.text);

  const handleEdit = () => {
    setIsEditing(true);
  };

  const handleSave = () => {
    setIsEditing(false);
    onEdit(index, editedText);
  };

  const handleCancel = () => {
    setIsEditing(false);
    setEditedText(comment.text);
  };

  return (
    <div className="Comment">
     <div className='comment-container'>
      <div className="comment-author">{comment.author}</div>
      {isEditing ? (
        <div>
          <textarea
            className="edit-comment"
            value={editedText}
            onChange={(e) => setEditedText(e.target.value)}
          />
          <button className="edit-button" onClick={handleSave}>Save</button>
          <button className="edit-button"onClick={handleCancel}>Cancel</button>
        </div>
      ) : (
        <div>
          <div className="comment-text">{comment.text}</div>
          <div className="comment-date">{comment.date}</div>
          <button className="edit-button" onClick={handleEdit}>
          ✎
          </button>
        </div>
      )}
      </div>
    </div>
  );
}

export default Comment;
