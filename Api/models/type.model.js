'use strict';

const mongoose = require('mongoose');

const typeSchema = mongoose.Schema({
    name: {
        require: true,
        type: String
    }
});

module.exports = mongoose.model('Type', typeSchema);