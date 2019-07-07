'use strict';

const Nuage = require('../models').Nuage;
const fs = require('fs-extra');
const EntityController = require('./entity.controller');

class NuageController {
    async getAll() {
        const nuage = await Nuage.find().populate('entities parentEntity');
        if (nuage.length > 0 && nuage !== null) {
            return nuage;
        }
    }

    async add(name, image) {
        //TODO set default image
        if (!image) {

        }

        const nuage = new Nuage();
        nuage.name = name;
        nuage.image = image;

        try {
            let n = await nuage.save();

            if ( await this.createDirectory(n._id) === null) {
                nuage.remove();
                return undefined;
            }

            const e = await EntityController.add(n._id, n._id, 'nuage');
            if (e === null) {
                return undefined;
            }

            n.parentEntity = e._id;

            n = await n.save();

            return n;
        } catch (e) {
            console.log(e);
            return undefined;
        }
    }

    async getById(id) {
        try {
            return await Nuage.findById(id).populate('entity');
        } catch (e) {
            return undefined;
        }
    }

    async createDirectory(id) {
        try {
            await fs.ensureDir(process.env.NUAGE_PATH + id);
            return true;
        } catch (e) {
            return undefined;
        }
    }

    async updateName(id, name) {
        const nuage = await Nuage.findById(id);
        if (nuage === null) {
            return undefined;
        }
        nuage.name = name;
        try {
            return await nuage.save();
        } catch (e) {
            return undefined;
        }
    }

    async deleteNuage(id) {
        const nuage = await Nuage.findOneAndUpdate( { _id: id }, { is_deleted: true });

        if (nuage === null) {
            return undefined;
        }

        return nuage;
    }
}

module.exports = new NuageController();
