import React from 'react';

function CommentButton({ onClick }) {
    return (
        <span className="comment-button" onClick={onClick}>
            <span className="icon">💬</span> Comment
        </span>
    );
}

export default CommentButton;
