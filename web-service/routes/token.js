const userController = require('../controllers/user');
const express = require('express');
const { authMiddleware } = require('../middleware/authmiddleware');

var router = express.Router(); 

router.route('/')
    .post(userController.login) 
 
module.exports = router;