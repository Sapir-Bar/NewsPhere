const User = require('../models/user');
const Post = require('../models/post');

const toggleFriend = async (userId, friendId)  => {
  const user = await User.findById(userId)

  const friendIndex = user.friends.findIndex(friendIdOther => friendIdOther.toString() === friendId)
  if(friendIndex !== -1) { // already friends
      user.friends.splice(friendIndex, 1) // remove
      await User.findByIdAndUpdate(userId, { $pull: { friends: friendId }})
  } else { // new friend
      user.friends.push(friendId)
      await User.findByIdAndUpdate(userId, { $push: { friends: friendId  }})
  }

  return await user.save()
}
const me = async (userId) => {
  return  await User.findById(userId)
} 

const createUser = async (userInfo) => {
    const username = userInfo.username
    const password = userInfo.password
    const nickname = userInfo.nickname
    const image = userInfo.image
    const posts = userInfo.posts
    const friends = userInfo.friends
    const user = new User({ username, password ,nickname, image, posts, friends });
    return await user.save()
  };

  const getUsers = async () => {
    try {
      const users = await User.find({});
      return users;
  } catch (error) {
      console.error('Error while fetching users:', error);
      throw error; // Throw the error to handle it in the controller
  }
};

const checkUserByName = async (userName) => {
  return await User.exists({ username: userName });
};


// Function to fetch users from the database
const login = async () => {
  try {
      // Fetch all users from the database
      const users = await User.find();
      return users;
  } catch (error) {
      throw new Error('Error fetching users from the database');
  }
};
const addFriend = async (userId, friendId) => {
 // const userId = userId;
 // const friendId = friendId;
  const user = await User.findById(userId)
  const friend = await User.findById(friendId)
  //console.log(author.posts)
  //console.log(newPost._id)
 /* await UserModel.findByIdAndUpdate(authorId, { $push: { posts:  newPost._id, $position: 0  } });*/
  user.friends.push(friendId);
  friend.friends.push(userId);
  await user.save();
  await friend.save();
  
  return user;
}
const updateUser = async (userId, name, image) => {
  /* const user = await UserModel.findById(userId)*/
   const user = await User.findById(userId);
   
   /*const postIndex = user.posts.findIndex(post => post._id.toString() === postId);*/
  /* if (postIndex !== -1) {*/
     if (name) { user.nickname = name; }
     if (image) { user.image = image; }
    // console.log("hello",post.content)
  /* }*/
   return (await user.save())/*.populate({
     path:"author",
     model: "User"
   });*/
 };

module.exports = { createUser, getUsers, checkUserByName, login, toggleFriend, me,updateUser, addFriend};