import React from 'react';
import Post from "../post/Post";

function PostList({ posts, updatePost,deletePost, friend }) {
   // console.log("postlistp:", posts.data);
    posts= posts.data;
    // Check if posts is an array before mapping over it
    const postsList = Array.isArray(posts) ? (
       
        posts.map((post) => (
            <Post key={post._id} {...post} updatePost={updatePost} deletePost={deletePost} friend={friend} />
        ))
    ) : (
        <p>No posts available</p>
    );

    return (
        <div className="posts">
            {postsList}
        </div>
    );
}

export default PostList;