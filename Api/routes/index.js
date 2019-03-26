'use strict';

class RouterBuilder {
    build(app) {
        app.use('/sample', require('./sample.router'));
    }
}

module.exports = new RouterBuilder();
