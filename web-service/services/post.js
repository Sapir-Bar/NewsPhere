const Post = require('../models/post');
const UserModel = require('../models/user');

const findById = async (id) => {
  try {
    const post = await Post.findById(id);
    return post;
  } catch (error) {
    throw new Error('An error occurred while finding post by ID');
  }
};
/*
const createUserPost = async (post) => {
  const authorId = post.author._id
  const newPost = await Post.create(post);
  await UserModel.findByIdAndUpdate(authorId, { $push: { posts: newPost._id } })
 const user = await UserModel.findByIdAndUpdate(authorId, { $push: { posts: { $each: [newPost._id], $position: 0 } } }, { new: true });
 user.posts.add(newPost._id, 1)
  return newPost
};*/

const createUserPost = async (post) => {
  const authorId = post.author;
  const author = await UserModel.findById(authorId)
  const newPost = await Post.create(post);
  console.log(newPost._id)
 /* await UserModel.findByIdAndUpdate(authorId, { $push: { posts:  newPost._id, $position: 0  } });*/
  author.posts.push(newPost._id);
  await author.save();
  return newPost.populate({
    path:"author",
    model: "User"
  });
};


const deleteUserPost = async (userId, postId) => {
  const user = await UserModel.findById(userId)
  const postIndex = user.posts.findIndex(post => post._id.toString() === postId)
  if (postIndex !== -1) {
    user.posts.splice(postIndex, 1)
    await UserModel.findByIdAndUpdate(userId, { $pull: { posts: postId } })
    await Post.findByIdAndDelete(postId);
    return await user.save();
  }
}


const updateUserPost = async (userId, postId, content, image) => {
 /* const user = await UserModel.findById(userId)*/
  const post = await Post.findById(postId);
  console.log(content)
  /*const postIndex = user.posts.findIndex(post => post._id.toString() === postId);*/
 /* if (postIndex !== -1) {*/
    if (content) { post.content = content; }
    if (image) { post.image = image; }
 /* }*/
  return (await post.save()).populate({
    path:"author",
    model: "User"
  });
};

const getFeedPosts = async (userId) => {

  const allPosts = await Post.find().sort({ date: -1 }).populate({
    path:"author",
    model: "User"
    /*populate: {
      path: "friends",
      model: "User",
    }*/
  });

  const set = new Set() // O(1)
  const user = await UserModel.findById(userId).populate({
    path: "friends",
    model: "User",
    populate: {
      path: "posts",
      model: "Post",
      populate: {
        path: "author", // Assuming the field name is 'user' in the Post schema
        model: "User"
      }
    }
  })

  const friends = user.friends
  const posts = []


  for (var friend of friends) {
    posts.push(...friend.posts)
  }
  const sorted = posts.sort((a, b) => b.date.getTime() - a.date.getTime())
  const postIdSet = new Set();

  var allTakenPosts = []
  if (sorted.length <= 20) {
    for (var post of sorted) postIdSet.add(String(post._id))
    allTakenPosts.push(...sorted)
  } else {
    const only20 = sorted.slice(0, 20)
    for (var post of only20) postIdSet.add(String(post._id))
    allTakenPosts.push(...only20)
  }

  while (allTakenPosts.length < 25 && allPosts.length) {
    const somePost = allPosts.pop()
    if (!postIdSet.has(String(somePost._id))) {
      allTakenPosts.push(somePost)
    }
  }
  
  allTakenPosts.sort((a, b) => b.date.getTime() - a.date.getTime());
  for (var post of allTakenPosts) console.log(post._id)
  return allTakenPosts;
};

const getFriendPosts = async (userId, friendId) => {
  try {
    const user = await UserModel.findById(friendId)
     .populate({
      path: "posts",
      model: "Post",
      options: { sort: { date: -1 } }, // Sort posts by date in descending order
      populate: {
        path: "author",
        model: "User"
      }
    });

    console.log("user", user)
    console.log("userposts", user.posts)
    const friendIndex = user.friends.findIndex(friendIdOther => friendIdOther.toString() === userId);
    
    if (friendIndex !== -1) { // Legal friends
      return user.posts;// Return the populated posts array
    } else {
      return null; // Friend not found
    }
  } catch (error) {
    console.error("Error getting friend posts:", error);
    return null;
  }
};

module.exports = { createUserPost, updateUserPost, deleteUserPost, getFeedPosts, getFriendPosts, findById }