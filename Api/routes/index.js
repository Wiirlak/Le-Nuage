'use strict';

class RouterBuilder {

    build(app) {
        app.use('/user', require('./user.router'));
        app.use('/entity', require('./entity.router'));
        app.use('/auth', require('./auth.router'));
        app.use('/nuage', require('./nuage.router'));
        app.use('/tree', require('./tree.router'));
        app.use('/history', require('./history.router'));
    }
}

module.exports = new RouterBuilder();
