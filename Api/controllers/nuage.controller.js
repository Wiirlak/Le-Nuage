'use strict';

const Nuage = require('../models').Nuage;
const User = require('../models').User;
const fs = require('fs-extra');
const EntityController = require('./entity.controller');

class NuageController {
    async getAll() {
        const nuage = await Nuage.find().populate('parentEntity');
        if (nuage.length > 0 && nuage !== null) {
            return nuage;
        }
    }

    async getUserNuage(id){
        const nuage = await User.findById(id).select("nuages -_id").lean();
        /*await Nuage.find({ "id name": { "$in": nuage } },function(err,items) {
           // matching results are here

        })*/

        var tmp = JSON.stringify(nuage);
        var tmp2 = JSON.parse(tmp);
        const results = [];
        for(let i = 0 ; i< tmp2.nuages.length;i++)
            results.push(tmp2.nuages[i]);

        return await Nuage.find({ _id: { $in : results }, is_deleted: {$exists : true , $eq : false} });
    }

    async getPageNuage(id, page){
        var perPage = 25;
        const nuage = await User.findById(id).select("nuages -_id").lean();
        var tmp = JSON.stringify(nuage);
        var tmp2 = JSON.parse(tmp);
        const results = [];

        for(let i = 0 ; i< tmp2.nuages.length;i++)
            results.push(tmp2.nuages[i]);

        return await Nuage.find({ _id: { $in : results }, is_deleted: {$exists : true , $eq : false} }).skip(perPage*(page-1)).limit(perPage);
    }

    async add(name, image, id) {
        //TODO set default image
        if (!image) {

        }

        const nuage = new Nuage();
        nuage.name = name;
        nuage.image = image;
        nuage.owner = id;

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



            const user = await User.findByIdAndUpdate(
               { _id: id }, 
               { $push:
                    {
                        nuages : n._id
                    }
               });

            n.parentEntity = e._id;
            n.access.push(user._id);

            n = await n.save();

            return n;
        } catch (e) {

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

    async addUser(id, email) {
        const user = await User.findOne({email: email});
        if (!user)
            return undefined;
        const nuage = await Nuage.findByIdAndUpdate(id, {$push: {access: user._id}});
        if (!nuage)
            return undefined;
        await User.findOneAndUpdate({_id: user.id}, {$push: {nuages: nuage}});
        return nuage;
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
