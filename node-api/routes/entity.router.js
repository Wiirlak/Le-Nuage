'use strict';

const express = require('express');
const bodyParser = require('body-parser');
const EntityController = require('../controllers').EntityController;

const router = express.Router();
router.use(bodyParser.json());


router.route('/')
    .get(EntityController.index)
    .post(EntityController.new);

// router.route('/:id')
//     .get(UserController.view)
//     .put(UserController.update)
//     .delete(UserController.delete);

module.exports = router;