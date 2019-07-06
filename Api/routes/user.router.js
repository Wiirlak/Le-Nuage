'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const UserController = require('../controllers').UserController;

const router = express.Router();
router.use(bodyParser.json());

router.get('/', async (req, res, next) => {
    const users = await UserController.getAll();
    if (users === undefined) {
        return res.status(404).end();
    }
    res.json(users);
});

router.post('/', async (req, res, next) => {
    if (!req.body.name || !req.body.email || !req.body.password) {
        return res.status(400).end();
    }
    const u = await UserController.add(req.body.name, req.body.email, req.body.password);
    if (u === undefined) {
        return res.status(409).end();
    }
    res.status(201).json(u);
});

router.get('/:id', async (req, res, next) => {
    if (!req.params.id) {
        return res.status(400).end();
    }
    const u = await UserController.getById(req.params.id);
    if (u === undefined) {
        return res.status(404).end();
    }
    res.json(u);
});

router.put('/', async(req, res, next) => {
    if (!req.body.id) {
        return res.status(400).end();
    }
    if (req.body.email) {
        const user = await UserController.updateEmail(req.body.id, req.body.email);
        if (user === undefined) {
            return res.status(409).end();
        }
        return res.json(user);
    }
    if (req.body.password) {
        const user = await UserController.updatePassword(req.body.id, req.body.password);
        if (user === undefined) {
            return res.status(409).end();
        }
        return res.json(user);
    }
    res.status(400).end();
});

router.delete('/:id', async(req, res, next) => {
    if (!req.params.id) {
        return res.status(400).end();
    }

    const u = await UserController.deleteUser(req.params.id);

    if (u === undefined) {
        return res.status(409).end();
    }
    res.send();
});



/*router.route('/:id')
    .get(UserController.view)
    .put(UserController.update)
    .delete(UserController.delete);*/

module.exports = router;
