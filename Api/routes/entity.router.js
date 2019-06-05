'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const multer = require('multer');
const upload = multer({ dest: process.env.UPLOAD_PATH });
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
   if (!req.body.name || !req.body.type || !req.body.parentId) {
       return res.status(400).end();
   }
   const e = await EntityController.add(req.body.parentId, req.body.name, req.body.type);
   if (e === undefined) {
       return res.status(409).end();
   }

   res.status(201).json(e);
});

router.post('/upload', upload.single('somefile'),async (req, res, next) => {
    //TODO finish to add parentId
    if (!req.file || !req.body.parentId) {
        return res.status(400).end();
    }
    //console.log(req.file);
    const m = await EntityController.moveFile(req.file.path, `${process.env.NUAGE_PATH}${req.body.parentId}/${req.file.originalname}`);

    if (!m) {
        return res.status(409).end();
    }
    const e = await EntityController.add(req.body.parentId, req.file.originalname, "file");
    if (e === undefined) {
        return res.status(409).end();
    }

    res.status(201).json(e);
});

module.exports = router;
