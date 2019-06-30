'use strict';

const Entity = require('../models').Entity;

class TreeController {

    async getTree(parentId) {
        try {
            return await Entity.find( { parent: parentId }).populate('type');
        } catch (e) {
            return undefined;
        }
    }

}

module.exports = new TreeController();
