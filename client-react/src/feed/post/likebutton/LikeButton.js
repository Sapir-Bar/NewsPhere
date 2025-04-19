import React, { useState } from 'react';

function LikeButton() {
    const [liked, setLiked] = useState(false);

    const toggleLike = () => {
        setLiked(!liked);
    };

    return (
        <span className="like-button" onClick={toggleLike}>
            <span className="icon">{liked ? "❤️️" : "♡"}</span> {liked ? "Liked" : "Like"}
        </span>
    );
}

export default LikeButton;