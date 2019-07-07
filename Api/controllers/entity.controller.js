'use strict';

const Entity = require('../models').Entity;
const Type = require('../models').Type;
const fs = require('fs-extra');

class EntityController {

    async getAll() {
        const entities = await Entity.find().populate('type');

        if (entities.length > 0 && entities !== null) {
            return entities;
        }
        return undefined;
    }

    async getEntityById(id) {
        try {
            return await Entity.findById(id).populate('type parent');
        } catch (e) {
            return undefined;
        }
    }

    async  add(parentId, name, type, size) {
        //let nuage;
        const entity = new Entity();
        entity.name = name;
        if (size != undefined) {
            entity.size = size;
        } else {
            entity.size = 0;
        }
        if (type === 'nuage') {
            entity.parent = parentId;
        } else {
            entity.parent = await Entity.findOne({ _id: parentId });
        }

        if (entity.parent === null) {
            return undefined;
        }
        let extension = entity.name.split('.');

        if (type === 'file') {
            entity.extension = extension[extension.length - 1];
        } else {
            entity.extension = '';
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
            const e = await entity.save();
            return e;
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
        const entity = await Entity.findById(entityId);

        if (entity === null) {
            return undefined;
        }
        //TODO find problème
        const parent = await this.getNuageByEntityId(entity._id);
        if (parent === undefined || parent.type.name !== 'nuage') {
            return undefined;
        }
        let path = null;
        if (entity.extension === '' || undefined) {
            path = `${process.env.NUAGE_PATH}${parent._id}/${entity._id}`;
        } else {
            path = `${process.env.NUAGE_PATH}${parent._id}/${entity._id}.${entity.extension}`;
        }
        return path;
    }

    async rename(entityId, name) {
        const entity = await Entity.findById(entityId);
        if (entity === null) {
            return undefined;
        }
        const parent = await this.getNuageByEntityId(entity._id);
        if (parent === undefined || parent.type.name !== 'nuage') {
            return undefined;
        }
        const path = `${process.env.NUAGE_PATH}${parent._id}/${entity._id}`;
        //TODO rename realfile
        return undefined;
    }

    async getNuageByEntityId(id) {
        const entity = await this.getEntityById(id);

        if (entity !== undefined && entity.type.name === 'nuage') {
            return entity;
        }
        const parent = await this.getEntityById(entity.parent._id);
        if (parent !== undefined && parent.type.name === 'nuage') {
            return parent;
        } else if (parent !== undefined && parent.type.name === 'folder') {
            return await this.getNuageByEntityId(parent.id);
        }
        return undefined;
    }

}
module.exports = new EntityController();
