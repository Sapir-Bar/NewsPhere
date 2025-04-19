import React, {useState} from "react";
import feeds from "./feeds.json";
import AddPost from "../AddPost/AddPost";
import FeedResults from "../FeedResults/FeedResults";
import img from "./img14.jpg"
function Feed(){
    const [feedsList,setFeedList]= useState(feeds);
    
    const handleAdd=(newPost)=>{
        setFeedList([newPost,...feedsList])
    }

    const handleDelete=function (key){
        const remainFeed=prevFeeds => prevFeeds.filter((feed) => feed.id !== key);
        setFeedList(remainFeed);
    }
    const handleEdit = (postId, newText) => {
        setFeedList(prevFeeds =>
            prevFeeds.map(feed => {
                if (feed.id === postId) {
                    return { ...feed, text: newText };
                }
                return feed;
            })
        );
    };

    return (
        <div>
            <div className="card  mb-3" style={{width: '35rem'}}>
                <div>
                    <AddPost addFeed={handleAdd} img={img}
                   />
                </div>

            </div>
            <div>
                <FeedResults feeds={feedsList} img={img} postDelete={handleDelete} editPost={handleEdit}/>
            </div>
        </div>

    );
}


export default Feed;