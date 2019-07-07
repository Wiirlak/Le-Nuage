'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const AuthController = require('../controllers').AuthController;

const router = express.Router();
router.use(bodyParser.json());

router.post('/register', async (req, res, next) => {
    if (!req.body.name || !req.body.email || !req.body.password) {
        return res.status(400).end();
    }
    const auth = await AuthController.register(req.body.name, req.body.email, req.body.password);

    if (auth === undefined) {
        return res.status(409).end();
    }
    res.status(201).json(auth);
});

router.get('/verify', async (req, res, next) => {
    const token = req.headers['x-access-token'];
    if (!token) {
        return res.status(401).send({ auth: false, token: 'No token provided.' });
    }
    const user = await AuthController.verify(token);
    if (user === undefined) {
        return res.status(404).end();
    }
    res.json(user);
});

router.post('/login', async (req, res, next) => {
    //console.log(req.body);
    if (!req.body.email || !req.body.password) {
        return res.status(400).end();
    }

    const auth = await AuthController.login(req.body.email, req.body.password);
    if (auth === undefined) {
        return res.status(404).end();
    }
    res.json(auth);
});

router.get('/logout', (req, res, next) => {
   res.status(200).send({ auth: false, token: null });
});

module.exports = router;
