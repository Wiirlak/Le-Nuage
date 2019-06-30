'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const NuageController = require('../controllers').NuageController;
const HistoryController = require('../controllers').HistoryController;
const AuthController = require('../controllers').AuthController;
const strings = require('../res/strings');

const router = express.Router();
router.use(bodyParser.json());

router.get('/', async(req, res, next) => {
    const nuages = await NuageController.getAll();

    if (nuages === undefined) {
        return res.status(404).end();
    }
    const u = await AuthController.verify(req.headers['x-access-token']);
    await HistoryController.addToHistory(strings.read, u._id, null, null, 'All Nuages');
    res.json(nuages);
});

router.get('/:id', async (req, res, next) => {
    if (!req.params.id) {
        return res.status(400).end();
    }
    const n = await NuageController.getById(req.params.id);
    if (n === undefined) {
        return res.status(404).end();
    }
    const u = await AuthController.verify(req.headers['x-access-token']);
    await HistoryController.addToHistory(strings.read, u._id, null, n._id, strings.nuage);
    res.json(n);
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
    const u = await AuthController.verify(req.headers['x-access-token']);
    await HistoryController.addToHistory(strings.create, u._id, null, n._id, strings.nuage);
    res.status(201).json(n);
});

router.put('/', async(req, res, next) => {
    if (!req.body.id) {
        return res.status(400).end();
    }
    if (req.body.name) {
        const nuage = await NuageController.updateName(req.body.id, req.body.name);
        if (nuage === undefined) {
            return res.status(409).end();
        }
        const u = await AuthController.verify(req.headers['x-access-token']);
        await HistoryController.addToHistory(strings.update, u._id, null, nuage._id, strings.nuage);
        return res.json(nuage).end();
    }
    res.status(400).end();
});



module.exports = router;
