//Initialize express router
let router = require('express').Router();

//Set default API response
router.get('/', function (req, res) {
    res.json({
        status: 'API Its Working',
        message: 'Welcome to Node API'
    });
});

//Import user controller
var userController = require('./controllers/user.controller');

//Import file controller
var fileController = require('./controllers/fileController');

//User routes
router.route('/user')
    .get(userController.index)
    .post(userController.new);

router.route('/user/:user_id')
    .get(userController.view)
    .patch(userController.update)
    .put(userController.update)
    .delete(userController.delete);

//File route
// router.route('/file')
//     .get(fileController.index)
//     .post(fileController.new);

// route.route('/file/:file_id')
//     .get(fileController.view)
//     .patch(fileController.update)
//     .put(fileController.update)
//     .delete(fileController.delete);

//Export API routes
module.exports = router;