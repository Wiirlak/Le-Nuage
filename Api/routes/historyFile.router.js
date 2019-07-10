'use strict';

const express = require('express');
const HistoryFileController = require('../controllers').HistoryFileController;

const router = express.Router();

router.get('/', async (req,res,next) => {
	if(!req.query.parentid || !req.query.name || !req.query.limit)
		return res.status(400).end();
    const histories = await HistoryFileController.getHistoryByParentIdAndName(req.query.parentid,req.query.name,req.query.limit);
    if (histories === undefined) {
        return res.status(404).end();
    }
    return res.json(histories).end();
});

module.exports = router;