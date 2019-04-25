'use strict';

const mongoose = require('mongoose');

const versionSchema = mongoose.Schema({
    date: {
        type: Date
    },
    name: {
        type: String
    }
});

module.exports = mongoose.model('Version', versionSchema);