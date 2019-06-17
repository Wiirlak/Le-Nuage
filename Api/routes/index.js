'use strict';

class RouterBuilder {

    build(app) {
        app.use('/auth', require('./auth.router'));
        app.use(require('../middleware').check());
        app.use('/user', require('./user.router'));
        app.use('/entity', require('./entity.router'));
        app.use('/nuage', require('./nuage.router'));
        app.use('/tree', require('./tree.router'));
    }
}

module.exports = new RouterBuilder();
