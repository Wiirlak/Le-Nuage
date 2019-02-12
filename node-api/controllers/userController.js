//Import user model
User = require('../models/userModel');

//Handle index actions
exports.index = function (req, res) {
    User.get(function (err, user) {
        if (err) {
            res.json({
                status: "error",
                message: err,
                dta: []
            });
        }
        res.json({
            status: "success",
            message: "Contacts retrives successfully",
            data: user
        });
    });
};

//Handle create user actions
exports.new = function (req, res) {
    var user = new User();
    user.name = req.body.name ? req.body.name : user.name;
    user.email = req.body.email;
    user.password = req.body.password;

    //save the user and check for errors
    user.save(function (err) {
        if (err) {
            res.json({
                status: "error",
                message: err,
                data: []
            });
        }
        res.json({
            status: "success",
            message: 'New contact created',
            data: user
        });
    });
};

//Handle view user info
exports.view = function (req, res) {
    User.findById(req.params.user_id, function (err, user) {
        if (err) {
            res.json({
                status: "error",
                message: err,
                data: []
            });
        }
        res.json({
            message: 'User details loading...',
            data: user
        });
    });
};

//Handle update user info
exports.update = function (req, res) {
    User.findById(req.params.user_id, function (err, user) {
        if (err) {
            res.json({
                status: "error",
                message: err,
                data: []
            });
        }
        user.name = req.body.name ? req.body.name : user.name;
        user.email = req.body.email;
        user.password = req.body.password;

        //save the contact and check for erros
        user.save(function (err) {
            if (err) {
                res.json({
                    status: "error",
                    message: err,
                    data: []
                });
            }
            res.json({
                message: 'User Info updated',
                data: user
            });
        });
    });
};

//Handle delete contact
exports.delete = function (req, res) {
    User.deleteOne({
        _id: req.params.user_id
    }, function (err) {
        if (err) {
            res.json({
                status: "error",
                message: err,
                data: []
            });
        }
        res.json({
            status: "success",
            message: 'User deleted'
        });
    });
};
