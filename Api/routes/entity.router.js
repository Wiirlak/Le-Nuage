'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const multer = require('multer');
const upload = multer({ dest: process.env.UPLOAD_PATH });
const EntityController = require('../controllers').EntityController;
const AuthController = require('../controllers').AuthController;
const HistoryController = require('../controllers').HistoryController;
const strings = require('../res/strings');

const router = express.Router();
router.use(bodyParser.json());

router.get('/', async (req, res, next) => {
    const entities = await EntityController.getAll();

    if (entities === undefined) {
        return res.status(404).end();
    }
    const u = await AuthController.verify(req.headers['x-access-token']);
    await HistoryController.addToHistory(strings.read, u._id, null, null, 'All Entities');
    res.json(entities);
});

router.get('/search', async (req, res, next) => {
    if (!req.query.e) {
        return res.status(400).end();
    }

    const entity = await EntityController.getEntityById(req.query.e);
    if (entity === undefined) {
        return res.status(409).end();
    }
    return res.json(entity);
});

router.post('/', async (req, res, next) => {
   if (!req.body.name || !req.body.type || !req.body.parentId) {
       return res.status(400).end();
   }
   const e = await EntityController.add(req.body.parentId, req.body.name, req.body.type, undefined, undefined);
   if (e === undefined) {
       return res.status(409).end();
   }
   const u = await AuthController.verify(req.headers['x-access-token']);
   await HistoryController.addToHistory(strings.create, u._id, e._id, null, strings.entity);
   res.status(201).json(e);
});

router.post('/upload', upload.single('somefile'),async (req, res, next) => {
    //TODO finish to add parentId
    if (!req.file || !req.body.parentId) {
        return res.status(400).end();
    }
    let e;
    //console.log(req.file);
    const p = await EntityController.getNuageByEntityId(req.body.parentId);
    if (p === undefined || p.type.name !== 'nuage') {
        return res.status(409).end();
    }

    //const hash = EntityController.hash(req.file);
    const check = await EntityController.checkFile(req.file, req.body.parentId);
    if (check === undefined) {
        e = await EntityController.add(req.body.parentId, req.file.originalname, "file", req.file, undefined);
    } else if (check.version === -1) {
        if (await EntityController.removeFile(req.file.path)) {
            return res.status(201).json(check.entity).end();
        }
        return res.status(201).json(check.entity).end();
    } else {
        e = await EntityController.add(req.body.parentId, req.file.originalname, "file", req.file, check.version);
    }
    let extension = req.file.originalname.split('.');

    if (e === undefined) {
        return res.status(409).end();
    }

    const m = await EntityController.moveFile(req.file.path, `${process.env.NUAGE_PATH}${p._id}/${e._id}.${extension[extension.length - 1]}`);
    if (!m) {
        return res.status(409).end();
    }

    const u = await AuthController.verify(req.headers['x-access-token']);
    await HistoryController.addToHistory(strings.upload, u._id, e._id, null, strings.entity);
    res.status(201).json(e);

});

router.put('/', async (req, res, next) => {
    if (!req.query.name || !req.query.nuageId || !req.query.entityId) {
        return res.status(400).end();
    }
    const e = await EntityController.rename(req.query.nuageId, req.query.entityId, req.query.name);
    if (e === undefined) {
        return res.status(409).end();
    }
    res.json(e);
});

router.get('/download', async (req, res, next) => {
    if (!req.query.e) {
        return res.status(400).end();
    }

    const path = await EntityController.downloadEntity(req.query.e);
    console.log(path);

    const u = await AuthController.verify(req.headers['x-access-token']);
    await HistoryController.addToHistory(strings.download, u._id, req.query.e, null, strings.entity);
    res.sendFile(path);
});

router.get('/synchronize', async (req, res, next) => {
    if (!req.query.e || !req.query.p) {
        return res.status(400).end();
    }
    //TODO upload for synch
    const check = EntityController.checkFile()
});

// exemple http://localhost:3000/entity/last?parentid=5d245d22e0f1127c70f78b10&name=sdqfqsfd.txt
router.get('/last',async (req,res, next) => {
    if (!req.query.parentid || !req.query.name) {
        return res.status(400).end();
    }
    const retour = await EntityController.getLatestEntityByName(req.query.parentid,req.query.name);
    if(retour === undefined)
        return res.status(404).end();
    return res.status(200).json(retour);
});

module.exports = router;
