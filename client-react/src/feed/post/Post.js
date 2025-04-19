import React, { useEffect, useState } from 'react';
import CommentButton from './commentbutton/commentButton';
import './Post.css';
import LikeButton from './likebutton/LikeButton';
import Comment from './comment/Comment';
function Post({ _id,postID,author_nickname, content, image, author_image, date , updatePost,deletePost,friend,likes,comments1,author}) {
    const [comments, setComments] = useState([]);
    const [newCommentText, setNewCommentText] = useState('');
    const [isEditing, setIsEditing] = useState(false);

    const [editedContent, setEditedContent] = useState('');
    const [tempContent, setTempContent] = useState('')
    const [editedImageUrl, setEditedImageUrl] = useState();
    const [tempImage, setTempImage] = useState('');
    
    useEffect(()=>{setEditedContent(content)},[content])
    useEffect(()=>{setEditedImageUrl(image)},[image])
    useEffect(()=>{setTempContent(content)},[content])
    useEffect(()=>{setTempImage(image)},[image])

    const addComment = () => {
        if (newCommentText.trim() !== '') {
            const newComment = {
                text: newCommentText,
                author_nickname: 'Alice',
                date: new Date().toLocaleString()
            };
            setComments([...comments, newComment]);
            setNewCommentText('');
        }
    };

    const handleEdit = () => {
        setIsEditing(true);
    };

    const handleSave = () => {
        setIsEditing(false);
        // Apply changes
        setEditedImageUrl(tempImage);
        setEditedContent(tempContent);
        updatePost({ _id,  postContent: tempContent, postImage: tempImage, date });
    };

    const deletePost1=()=>{
        console.log("posttodelete:",_id)
        deletePost(_id);
    }

    const handleCancel = () => {
        setIsEditing(false);
        // Reset edited content and image URL to original values
        setTempContent(editedContent)
        setTempImage(editedImageUrl)
    };
    const handleCommentEdit = (index, newText) => {
        const updatedComments = [...comments];
        updatedComments[index].text = newText;
        setComments(updatedComments);
      };
    const handleImageChange = (e) => {
        const file = e.target.files[0];
        const reader = new FileReader();
        reader.onloadend = () => {
            setTempImage(reader.result);
        };
        if (file) {
            reader.readAsDataURL(file);
            setTempImage(reader.result);
        }
    };
   
    return (
        <div className="post">
            <div className="post-header">
               {/* <img src={author_image} alt="Avatar" className="avatar" />
                <img src={author.image} alt="Selected" className="post-image" />*/}
               
                <img src={author.image} alt="Post Image" className="post-authorimage" />
                <span className="share-button" onClick={() => friend(author)}><span className="icon"></span> {author.nickname}🫂</span>
                <div className="post-date">{date}</div>
                {isEditing && <div className="editing-label">(Editing)</div>}
            </div>
            {isEditing ? (
                <div>
                    <textarea
                        className="edit-content"
                        value={tempContent}
                        onChange={(e) => setTempContent(e.target.value)}
                    />
                    <input
                        type="file"
                        accept="image/*"
                        onChange={handleImageChange}
                    />
                    {tempImage && (
                        <img src={tempImage} alt="Selected" className="post-image" />
                     )}
                    {/*editedImageUrl && <img src={editedImageUrl} alt="Post Image" className="post-image" />*/}
                    <button onClick={handleSave}>Save</button>
                    <button onClick={handleCancel}>Cancel</button>
                </div>
            ) : (
                <div>
                     <div className="post-content">
                       {editedContent}
                    </div>
                    {editedImageUrl && ( <img src={editedImageUrl} alt="Post Image" className="post-image" />  )}
               </div>
            )}
            <div className="post-actions">
                <span className="share-button"onClick={deletePost1}><span className="icon"></span> ❎ Delete</span>
                <LikeButton />
                <span className="share-button"><span className="icon">↪️</span> Share</span>
                {isEditing ? (
                    <span className="share-button" onClick={handleSave}><span className="icon">✔️</span> Save</span>
                ) : (
                    <span className="share-button" onClick={handleEdit}><span className="icon">✎</span> Edit</span>
                )}
            </div>
            <div className="post-comments">
                <div className='comment-button'>
                    <input
                        type="text"
                        placeholder="Write a comment..."
                        className="input-field"
                        value={newCommentText}
                        onChange={(e) => setNewCommentText(e.target.value)}
                    />
                    <CommentButton onClick={addComment} />
                </div>
                {comments.map((comment, index) => (
                   /* <div key={index} className="comment">
                        <div className="comment-author">{comment.author}</div>
                        <div className="comment-text">{comment.text}</div>
                        <div className="comment-date">{comment.date}</div>
                    </div>*/
                     
                    <Comment key={index} comment={comment} index={index} onEdit={handleCommentEdit} />

                ))}
            </div>
        </div>
    );
}

export default Post;
