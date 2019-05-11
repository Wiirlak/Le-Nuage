'use strict';

const Entity = require('../models').Entity;
const Type = require('../models').Type;

class EntityController {

    async getAll() {
        const entities = await Entity.find();

        if (entities.length > 0 && entities !== undefined) {
            return entities;
        }
        return undefined;
    }

    async add(name, type, path) {
        const entity = new Entity();
        entity.name = name;
        entity.path = path;

        try {
            entity.type = await Type.findOne({ name: type });
        }catch (err) {
            return undefined;
        }

        try {
            await entity.save();
            return entity;
        } catch (err) {
            return undefined;
        }

    }
}
module.exports = new EntityController();
