var express = require('express');
var router = express.Router();
var model = require('../models/index');

/* GET todo listing. */
router.get('/', function(req, res, next) {
    model.User.findAll({})
        .then(user => res.json({
            error: false,
            data: user
        }))
        .catch(error => res.json({
            error: true,
            data: [],
            error: error
        }));
});

router.get('/:id', function (req, res, next) {
   const user_id = req.params.id;
   model.User.findById(user_id)
        .then(user => res.json({
            error: false,
            data: user
        }))
       .catch(error => res.json({
           error: true,
           data: [],
           error: error
       }));
});


/* POST todo. */
router.post('/', function(req, res, next) {
    const {
        name,
        email
    } = req.body;
    model.User.create({
            name: name,
            email: email
        })
        .then(user => res.status(201).json({
            error: false,
            data: user,
            message: 'New user has been created.'
        }))
        .catch(error => res.json({
            error: true,
            data: [],
            error: error
        }));

});


/* update todo. */
router.put('/:id', function(req, res, next) {
    const  user_id = req.params.id;
    const { name, email } = req.body;
    model.User.update({
            name: name,
            email: email
        }, {
            where: {
                id: user_id
            }
        })
        .then(user => res.json({
            error: false,
            message : 'user has been updated.'
        }))
        .catch(error => res.json({
            error: true,
            error: error
        }));
});


/* GET todo listing. */
router.delete('/:id', function(req, res, next) {
    const user_id = req.params.id;
    model.User.destroy({ where: {
        id: user_id
        }})
            .then(status => res.json({
                error: false,
                message: 'user has been delete.'
            }))
            .catch(error => res.json({
                error: true,
                error: error
            }));
});

module.exports = router;