'use strict';

const mongoose = require('mongoose');

const nuageSchema = mongoose.Schema({
    name: {
        type: String
    },
    path: {
        type: String
    },
    image: {
        type: String
    }
});

module.exports = mongoose.model('Nuage', nuageSchema);