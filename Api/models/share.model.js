'use strict';

const mongoose = require('mongoose');

const ShareSchema = mongoose.Schema({
    created: {
        default: Date.now,
        type: Date
    },
    entity: {
        type: mongoose.Types.ObjectId,
        ref: 'Entity'
    },
    link: {
        type: String
    }
});

module.exports = mongoose.model('Share', ShareSchema);
