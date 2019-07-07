'use strict';

const mongoose = require('mongoose');

const nuageSchema = mongoose.Schema({
    name: {
        type: String
    },
    entities: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Entity',
    }],
    image: {
        type: String
    },
    parentEntity: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Entity'
    },
    is_deleted: {
        default: false,
        type: Boolean
    },
});

module.exports = mongoose.model('Nuage', nuageSchema);
