'use strict';

const mongoose = require('mongoose');
const Type = require('../models').Type;

const entitySchema = mongoose.Schema({
    name: {
        type: String
    },
    type: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Type'
    },
    path: {
        type: String
    },
    is_deleted: {
        type: Boolean,
        default: false
    },
    created: {
        default: Date.now,
        type: Date
    }
});

module.exports = mongoose.model('Entity', entitySchema);