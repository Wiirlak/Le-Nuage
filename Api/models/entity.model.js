'use strict';

const mongoose = require('mongoose');

const entitySchema = mongoose.Schema({
    name: {
        type: String
    },
    type: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Type'
    },
    parent: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Entity'
    },
    is_deleted: {
        type: Boolean,
        default: false
    },
    created: {
        default: Date.now,
        type: Date
    },
    owner: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    },
    extension: {
        type: String
    },
    size: {
        type: Number
    },
    version: {
        default: 0,
        type: Number
    }
});

module.exports = mongoose.model('Entity', entitySchema);
