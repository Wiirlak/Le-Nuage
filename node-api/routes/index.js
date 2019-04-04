'use strict';

class RouterBuilder {

    build(app) {
        app.use('/user', require('./user.router'));
        app.use('/entity', require('./entity.router'));
    }
}

module.exports = new RouterBuilder();