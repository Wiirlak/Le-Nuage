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
    const u = await AuthController.verify(req.headers['x-access-token']);
    var nuages;
    var url = require('url');
    var url_parts = url.parse(req.url, true);
    if (url_parts.query.page)
        nuages = await NuageController.getPageNuage(u._id, url_parts.query.page);
    else
        nuages = await NuageController.getUserNuage(u._id);

    // const nuages = await NuageController.getAll();

    if (nuages === undefined) {
        return res.status(404).end();
    }

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
    console.log(req.body.name)
    //TODO set image with right value
    const image = "https://zupimages.net/up/19/26/afo4.png";
    const u = await AuthController.verify(req.headers['x-access-token']);
    const n = await NuageController.add(req.body.name, image,u._id);
    if (n === undefined) {
        return res.status(409).end();
    }
    await HistoryController.addToHistory(strings.create, u._id, image, n._id, strings.nuage);
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

router.put('/addUser', async (req, res, next) => {
    if (!req.body.id) {
        return res.status(400).end();
    }
    if (req.body.email && req.body.id) {
        const nuage = await NuageController.addUser(req.body.id, req.body.email);
        if (nuage === undefined) {
            return res.status(409).end();
        }
        const u = await AuthController.verify(req.headers['x-access-token']);
        await HistoryController.addToHistory(strings.update, u._id, null, nuage._id, strings.nuage);
        return res.json(nuage).end();
    }
    res.status(400).end();
});

router.delete('/:id', async(req, res, next) => {
    if (!req.params.id) {
        return res.status(400).end();
    }

    const n = await NuageController.deleteNuage(req.params.id);

    if (n === undefined) {
        return res.status(409).end();
    }
    res.send();
});



module.exports = router;
