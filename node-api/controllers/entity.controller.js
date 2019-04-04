'use strict';

const Entity = require('../models').Entity;
const Type = require('../models').Type;

exports.index = async (req, res) => {
    const entities = await Entity.find();

    if (entities.length > 0 && entities !== undefined) {
        return res.json(entities);
    }

    res.status(404).end();
};

exports.new = async (req, res) => {

    if (!req.body.name || !req.body.type || !req.body.path) {
        return res.status(400).end();
    }

    const entity = new Entity();
    entity.name = req.body.name;
    entity.path = req.body.path;

    try {
        entity.type = await Type.findOne({ name: req.body.type });
    } catch(err) {
        return res.status(404).end();
    }

    try{
        await entity.save();
        return res.status(201).json(entity);
    } catch(err) {
        return res.status(409).end();
    }
};