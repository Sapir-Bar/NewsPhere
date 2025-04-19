const userController = require('../controllers/user');
const postController = require('../controllers/post');

const express = require('express');
const authMiddleware = require('../middleware/authmiddleware');
var router = express.Router(); 

router.delete('/posts/:pid', authMiddleware.authMiddleware, postController.deleteUserPost);
router.put('/posts/:pid', authMiddleware.authMiddleware, postController.updateUserPost);

router.post('/:id/posts', authMiddleware.authMiddleware, postController.createUserPost);
router.get('/:id/posts', authMiddleware.authMiddleware, postController.getFriendPosts);

router.put('/toggle-friend/:friendId', authMiddleware.authMiddleware, userController.toggleFriend);

router.get('/:id/friends', authMiddleware.authMiddleware, userController.toggleFriend);
router.post('/:id/friends', authMiddleware.authMiddleware, userController.addFriend);

router.post('/exist', userController.checkUserExistence);

router.get('/:id', authMiddleware.authMiddleware, userController.me);
router.put('/:id', authMiddleware.authMiddleware, userController.updateUser);

router.post('/', userController.createUser);
router.get('/', userController.getUsers);

module.exports = router;       
