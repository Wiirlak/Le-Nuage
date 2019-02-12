var mongoose = require('mongoose');

//Setup Schema
var userSchema = mongoose.Schema({
    name: {
        type: String
    },
    email: {
        type: String
    },
    password: String
}, {
    collection: 'user'
});

//Export User Model
var User = module.exports = mongoose.model('user', userSchema);

module.exports.get = function (callback, limit) {
    User.find(callback).limit(limit);
};
