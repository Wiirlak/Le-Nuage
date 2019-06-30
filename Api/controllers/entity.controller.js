'use strict';

const Entity = require('../models').Entity;
const Type = require('../models').Type;
const Nuage = require('../models').Nuage;
const fs = require('fs-extra');
const path = require('path');

class EntityController {

    async getAll() {
        const entities = await Entity.find().populate('type');

        if (entities.length > 0 && entities !== null) {
            return entities;
        }
        return undefined;
    }

    async add(parentId, name, type) {
        //let nuage;
        const entity = new Entity();
        entity.name = name;
        if (type === 'nuage') {
            entity.parent = parentId;
        } else {
            entity.parent = await Entity.findOne({ _id: parentId });
        }

        if (entity.parent === null) {
            return undefined;
        }

        //console.log(nuage);

        //if (nuage === null) {
        //    return undefined;
        //}

        entity.type = await Type.findOne({ name: type });

        if (entity.type === null) {
            return undefined;
        }

        try {
            return await entity.save();
            //nuage.entities.push(entity);
            //await nuage.save();
            //console.log(nuage);
            //return entity;
        } catch (err) {
            return undefined;
        }

    }

    //moves the $file to $dir2
    async moveFile(src, dest) {
        try {
            await fs.move(src, dest);
            return true;
        } catch (e) {
            return false;
        }
    }

    async downloadEntity(entityId) {
        const entity = await Entity.findOne({ _id : entityId });

        if (entity === null) {
            return undefined;
        }

        return `${process.env.NUAGE_PATH}${entity.parent}/${entity.name}`;
    }

}
module.exports = new EntityController();
