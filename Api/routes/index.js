'use strict';

class RouterBuilder {

    build(app) {
        app.use('/user', require('./user.router'));
        app.use('/entity', require('./entity.router'));
        app.use('/authenticate', require('./auth.router'));
    }
}

module.exports = new RouterBuilder();
