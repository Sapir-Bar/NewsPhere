const express = require('express');
const app = express();

// Body parsing middleware
app.use(express.urlencoded({ extended: true }));
app.use(express.json({ limit: '10000mb' }));

// Enable CORS
const cors = require('cors');
app.use(cors());

// Load environment variables
const customEnv = require('custom-env');
customEnv.env(process.env.NODE_ENV, './config');
//console.log(process.env.CONNECTION_STRING);
//console.log(process.env.PORT);

// Connect to MongoDB
const mongoose = require('mongoose');
mongoose.connect(process.env.CONNECTION_STRING, {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

// Listen for connection events
mongoose.connection.on('connected', () => {
    console.log('Connected to MongoDB');
});

mongoose.connection.on('error', (err) => {
    console.error('MongoDB connection error:', err);
});

const post = require('./controllers/post');
post.connectToServer();

// Serve static files from public directory
app.use(express.static('public'));

// Routes
const posts = require('./routes/post');
app.use('/api/posts', posts);

const users = require('./routes/user');
app.use('/api/users', users);

const tokens = require('./routes/token');
app.use('/api/tokens', tokens);

app.get('*', (req, res)=> {
  res.sendFile(path.resolve(__dirname, 'public', 'index.html'));
})

// Start the server
app.listen(process.env.PORT, '0.0.0.0', () => {
    console.log(`Server is running on http://0.0.0.0:${process.env.PORT}/`);
});
