'use strict';

const express = require('express');
const HistoryContoller = require('../controllers').HistoryController;

const router = express.Router();

router.get('/', async (req, res, next) => {
    if (req.query.u) {
        const histories = await HistoryContoller.getHistoryByUserId(req.query.u);
        if (histories === undefined) {
            return res.status(400).end();
        }
        return res.json(histories).end();
    }
    if (req.query.e) {
        const histories = await HistoryContoller.getHistoryByEntityId(req.query.e);
        if (histories === undefined) {
            return res.status(400).end();
        }
        return res.json(histories).end();
    }
    if (Object.keys(req.query).length === 0) {
        const histories = await HistoryContoller.getAll();
        if (histories === undefined) {
            return res.status(404).end();
        }
        return res.json(histories).end();
    }
    res.status(400).end();

});


module.exports = router;
