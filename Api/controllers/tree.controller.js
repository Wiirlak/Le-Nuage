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

    async getAllTree(parentId) {
        const entities = await this.getTree(parentId);
        for (const e of entities) {
            //console.log(e);
            if (e.type.name === "folder") {
                console.log('folder find');
            }
        }
    }

}

module.exports = new TreeController();
