'use strict';

const Entity = require('../models').Entity;
const Type = require('../models').Type;
const fs = require('fs-extra');
const crypto = require('crypto');
const moment = require('moment');
const lodash = require('lodash');
const mongoose = require('mongoose');

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

    async getEntityByIdNotPopulated(id) {
        try {
            return await Entity.findById(id);
        } catch (e) {
            return undefined;
        }
    }

    hash(path) {
        const fd = fs.createReadStream(path);
        const hash = crypto.createHash('sha256').setEncoding('hex');
        fd.pipe(hash);
        return new Promise((resolve, reject) => {
            fd.on('end',  () => {
                hash.end();
                
                 resolve(hash.read());
            });
        });

    }

    async add(parentId, name, type, file, version) {
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
        let extension = entity.name.split('.');

        if (type === 'file') {
            if (file.size !== undefined) {
                entity.size = file.size;
            } else {
                entity.size = 0;
            }
            if (version !== undefined) {
                entity.version = version;
            }
            entity.extension = extension[extension.length - 1];
            const sha256 = await this.hash(file.path);
            
            entity.hash = sha256;
        } else {
            entity.extension = '';
            entity.size = 0;
        }

        

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
    async removeFile(src) {
        try {
            await fs.unlink(src);
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

    async getEntityByNameAndParent(entityName, parentId) {
        const entity = await Entity.find({ name: entityName, parent: parentId}).sort({ version: -1 });
        if (entity === undefined) {
            return undefined;
        }
        return entity;
    }

    async checkFile(file, parentId) {
        const entity = await this.getEntityByNameAndParent(file.originalname, parentId);
        if (entity[0] === undefined) {
            return undefined;
        }
        
        //const parent = await this.getNuageByEntityId(entity[0]._id);
        //const entityBuffer = await fs.readFile( `${process.env.NUAGE_PATH}${parent._id}/${entity[0]._id}.${entity[0].extension}`);
        
        const newBuffer = await this.hash(file.path);
        if (entity[0].hash === newBuffer) {
            return {
                entity: entity[0],
                version: -1
            };
        }

        return {
            entity: entity[0],
            version: entity[0].version + 1
        };
    }

    async getLatestEntityByName(parentId, name){
        let entity =  await Entity.findOne( {parent: parentId , name: name, is_deleted : false}) .sort({created: 'desc'}).lean();
        if(entity === null)
            return undefined;
        let date = moment(entity.created, 'DD-MM-YYYY hh:mm');
        entity.created = date.format('DD-MM-YYYY hh:mm');  
        
        return entity;
    }

    async removeEntityByName(parentId, name){
        let res = await Entity.updateMany(
            {'parent': parentId , 'name': name, 'is_deleted' : false},
            {$set : {is_deleted: true}});
        if(res === null)	
            return undefined;
        return res;
    }

    async getEntityByNameAndParentId(parentId, name, limit){
        let entity =  await Entity.find( {parent: parentId , name: name, is_deleted : false}) .sort({created: 'desc'}).lean().limit(parseInt(limit));
        if(entity === null)
            return undefined;  
        return entity;
    }

    async getAllLatestEntity(parentId){
        const entity =  await Entity.find(
            {is_deleted : false, parent : mongoose.Types.ObjectId(parentId)}
        ).sort({created: "desc"}).populate("type")
        if(entity === null)
            return undefined;  
        return lodash.uniqWith(entity,(entityA,entityB) => {
            return entityA.name === entityB.name 
        });
    }

}
module.exports = new EntityController();
