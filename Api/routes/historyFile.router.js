'use strict';

const express = require('express');
const HistoryFileController = require('../controllers').HistoryFileController;

const router = express.Router();

router.get('/:parentId/:name', async (req,res,next) => {
    const histories = await HistoryFileController.getHistoryByParentIdAndName(req.query.parentid,req.query.name);
    if (histories === undefined) {
        return res.status(400).end();
    }
    return res.json(histories).end();
});

module.exports = router;