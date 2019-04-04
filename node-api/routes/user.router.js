'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const UserController = require('../controllers').UserController;

const router = express.Router();
router.use(bodyParser.json());


router.route('/')
    .get(UserController.index)
    .post(UserController.new);

router.route('/:id')
    .get(UserController.view)
    .put(UserController.update)
    .delete(UserController.delete);

module.exports = router;