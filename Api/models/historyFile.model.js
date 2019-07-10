'use strict';

const mongoose = require('mongoose');

const historyfileSchema = mongoose.Schema({
    action: {
        type: String
    },
    date: {
        default: Date.now,
        type: Date
    },
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    },
    entity: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Entity'
    },
    parentId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Entity'
    },
    filename: {
        type: String
    }
});

module.exports = mongoose.model('HistoryFile', historyfileSchema);
