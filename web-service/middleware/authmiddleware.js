const jwt = require('jsonwebtoken')
const customEnv = require('custom-env');
customEnv.env(process.env.NODE_ENV, './config');
const authMiddleware = (req, res, next) => {

    const token_header = req.headers['Authorization'] || req.headers['authorization']
    console.log(token_header)
    if(!token_header) {
        return res.status(401).json({
            message:"Unauthorized, no token provided",
            status: 401,
            data: null
        })
    }

    try {
        const token = token_header.split("Bearer")[1].trim()
        if(!token) {
            return res.status(401).json({
                message:"Unauthorized, bad token format",
                status: 401,
                data: null
            })
        }

        // { _id: string }
        const decoded = jwt.verify(token , process.env.JWT_SECRET)
        req.user = decoded 
        //req.user = { _id: decoded._id };

        next()
    } catch(e) {
        return res.status(401).json({
            message:"Unauthorized blabla",
            status: 401,
            data: null
        })
    }
}

//module.exports = authMiddleware
module.exports = {authMiddleware};