'use strict';

//Import user model
const User = require('../models').User;
const NuageController = require('./nuage.controller');

//Import lib for hash password
const bcrypt = require('bcryptjs');


class UserController {

    async getAll() {
        const users = await User.find();
        if (users.length > 0 && users !== undefined) {
            return users;
        }
        return undefined;
    }

    //name, firstname, email, date, password)
    async add(name, firstname, email, date, password) {
        const hashedPassword = await bcrypt.hash(password, 8);

        if (await User.findOne({ email: email})) {
            return undefined;
        }

        const nuage = await NuageController.add('Default', null);

        if (nuage === undefined) {
            return undefined;
        }

        const user = new User();
        user.name = name;
        user.firstname = firstname;
        user.email = email;
        user.date = date;
        user.password = hashedPassword;
        user.nuages.push(nuage);

        try {
            return await user.save();
        } catch(err) {
            return undefined;
        }
    }

    async getById(id) {
        try {
            return await User.findById(id);
        }catch(err) {
            return undefined;
        }
    }

    async getByEmail(email) {
        const user = await User.findOne({ email: email });
        if (user === null) {
            return undefined;
        }
        return user;
    }

    async deleteUser(id) {
        const user = await User.findOneAndUpdate( { _id: id }, { is_deleted: true });

        if (user === null) {
            return undefined;
        }

        return user;
    }

    async updatePassword(id, password) {
        const hashedPassword = await bcrypt.hash(password, 8);

        const user = await User.findById(id);
        if (user === null) {
            return undefined;
        }
        try {
            return await user.updateOne({password:hashedPassword});
        } catch (e) {
            return undefined;
        }
    }

    async updateEmail(id, email) {
        const user = await User.findById(id);
        if (user === null) {
            return undefined;
        }
        try {
            return await user.updateOne({email:email});
        } catch (e) {
            return undefined;
        }
    }
}

module.exports = new UserController();
