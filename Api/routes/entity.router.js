'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const EntityController = require('../controllers').EntityController;

const router = express.Router();
router.use(bodyParser.json());

router.get('/', async (req, res, next) => {
    const entities = await EntityController.getAll();

    if (entities === undefined) {
        return res.status(404).end();
    }
    res.json(entities);
});

router.post('/', async (req, res, next) => {
   if (!req.body.name || !req.body.type || !req.body.path) {
       return res.status(400).end();
   }
   const e = await EntityController.add(req.body.name, req.body.type, req.body.path);
   if (e === undefined) {
       return res.status(409).end();
   }
   res.status(201).json(e);
});

// router.route('/')
//     .get(EntityController.index)
//     .post(EntityController.new);

// router.route('/:id')
//     .get(UserController.view)
//     .put(UserController.update)
//     .delete(UserController.delete);

module.exports = router;
