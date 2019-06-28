'use strict';

const mongoose = require('mongoose');

const historySchema = mongoose.Schema({
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
    nuage: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Nuage'
    },
    details: {
        type: String
    }
});

module.exports = mongoose.model('History', historySchema);
