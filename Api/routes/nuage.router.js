'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const NuageController = require('../controllers').NuageController;

const router = express.Router();
router.use(bodyParser.json());

router.get('/', async(req, res, next) => {
    const nuages = await NuageController.getAll();

    if (nuages === undefined) {
        return res.status(404).end();
    }
    res.json(nuages);
});

router.get('/:id', async (req, res, next) => {
    if (!req.params.id) {
        return res.status(400).end();
    }
    const u = await NuageController.getById(req.params.id);
    if (u === undefined) {
        return res.status(404).end();
    }
    res.json(u);
});

router.post('/', async(req, res, next) => {
    if (!req.body.name) {
        return res.status(400).end();
    }
    //TODO set image with right value
    const image = null;
    const n = await NuageController.add(req.body.name, image);
    if (n === undefined) {
        return res.status(409).end();
    }
    res.status(201).json(n);
});



module.exports = router;
