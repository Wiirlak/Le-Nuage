'use strict';

const mongoose = require('mongoose');

const parentOfSchema = mongoose.Schema({
    folder: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Entity'
    },
    entity: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Entity'
    }
});

module.exports = mongoose.model('parent', parentOfSchema); 