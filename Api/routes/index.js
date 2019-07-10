'use strict';

class RouterBuilder {

    build(app) {
        app.use('/api/auth', require('./auth.router'));
        app.use(require('../middleware').check());
        app.use('/api/user', require('./user.router'));
        app.use('/api/entity', require('./entity.router'));
        app.use('/api/nuage', require('./nuage.router'));
        app.use('/api/tree', require('./tree.router'));
        app.use('/api/history', require('./history.router'));
    }
}

module.exports = new RouterBuilder();
