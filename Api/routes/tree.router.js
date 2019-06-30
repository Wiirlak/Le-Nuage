'use strict';

const express = require('express');
const bodyParse = require('body-parser');
const TreeController = require('../controllers').TreeController;


const router = express.Router();
router.use(bodyParse.json());

router.get('/:parentId', async (req, res, next) => {
    if (!req.params.parentId) {
        return res.status(400).end();
    }

    const t = await TreeController.getTree(req.params.parentId);

    if (t === undefined) {
        return res.status(409).end();
    }

    return res.status(200).json(t);

});

router.get('/all/:parentId', async (req, res, next) => {
    if (!req.params.parentId) {
        return res.status(400).end();
    }

    await TreeController.getAllTree(req.params.parentId);

    res.send();
});

router.post('/', async (req, res, next) => {

});

module.exports = router;
