'use strict';
const mongoose = require('mongoose');

//Setup Schema
const userSchema = mongoose.Schema({
    name: {
        type: String
    },
    firstname: {
        type: String
    },
    email: {
        type: String
    },
    birthday: {
        type: Date
    },
    password: {
        type: String
    },
    date_access: {
        type: Date
    },
    is_deleted: {
        default: false,
        type: Boolean
    },
    capacity_max: {
        default: 10,
        type: Number
    },
    capacity_usage: {
        default: 0,
        type: Number
    },
    nuages: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Nuage',
    }],
});

//Export User Model


module.exports = mongoose.model('User', userSchema);

/*module.exports.get = function (callback, limit) {
    User.find(callback).limit(limit);
};*/
