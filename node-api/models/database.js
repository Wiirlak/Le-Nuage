'use strict';

const mongoose = require('mongoose');

class Database {
    constructor(){
        this.connection = undefined;
        mongoose.connect(process.env.MONGO_URI, {useNewUrlParser: true })
                .then((connection) => {
                    this.connection = connection;
                });
    }
}

module.exports = new Database();