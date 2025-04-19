const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const Post = new Schema({
    author: {
        type: Schema.Types.ObjectId,
        ref: "User"
    },
    content: {
        type: String,
        required: false
    },
    image: {
        type: String,
        required: false
    },
    date: {
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('Post', Post);