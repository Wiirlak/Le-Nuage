'use strict';

//import controller user
const UserController = require('./user.controller');

//import lib for token
const jwt = require('jsonwebtoken');
//import lib for hash password
const bcrypt = require('bcryptjs');

class AuthController {
    //req.body.name, req.body.firstname, req.body.email, req.body.date, req.body.password
    async register(name, firstname, email, date, password) {
        const user = await UserController.add(name, firstname, email, date, password);
        if (user === undefined) {

            return undefined;
        }


        const token = this.generateToken(user._id);

        return {auth: true, token: token};
    }

    async verify(token) {
        try {
            const decoded = await jwt.verify(token, process.env.SECRET);
            const user = await UserController.getById(decoded.id);
            if (user === undefined) {
                return undefined;
            }

            return user;
        } catch (err) {
            return undefined;
        }
    }

    async login(email, password) {
        const user = await UserController.getByEmail(email);
        if (user === undefined) {
            return undefined;
        }

        const passwordIsValide = await bcrypt.compare(password, user.password);
        if (!passwordIsValide) {
            return undefined;
        }

        const token = this.generateToken(user._id);

        return { auth: true, token: token };
    }

    generateToken(id) {
        return jwt.sign({ id: id }, process.env.SECRET, {
            expiresIn: 20 * 60
        });
    }
}

module.exports = new AuthController();


