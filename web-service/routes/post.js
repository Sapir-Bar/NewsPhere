const postController = require('../controllers/post');
const express = require('express');
const  authMiddleware  = require('../middleware/authmiddleware');
var router = express.Router();

// http://foo.com/api/posts

router.route('/')
    .get(authMiddleware.authMiddleware, postController.getFeedPosts); 

module.exports = router;


   
