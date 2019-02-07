var mongoose = require('mongoose');

//Setup Schema
var userSchema = mongoose.Schema({
    name: {
        type: String,
        require: true
    },
    email: {
        type: String,
        require: true
    },
    password: String,
    create_date: {
        type: Date,
        default: Date.now
    }
}, {
    collection: 'user',
    versionKey: false
});

//Export User Model
var User = module.exports = mongoose.model('user', userSchema);

module.exports.get = function (callback, limit) {
    User.find(callback).limit(limit);
};