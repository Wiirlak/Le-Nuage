'use strict';

//Import user model
const User = require('../models').User;

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

    async add(name, email, password) {
        const hashedPassword = await bcrypt.hash(password, 8);

        const user = new User();
        user.name = name;
        user.email = email;
        user.password = hashedPassword;

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
}

module.exports = new UserController();

//Handle create user actions
/*exports.new = async (req, res) => {
    if (!req.body.name || !req.body.email || !req.body.password) {
        return res.status(400).end();
    }

    const hashedPaswword = bcrypt.hashSync(req.body.password);

    const user = new User();
    user.name = req.body.name;
    user.email = req.body.email;
    user.password = hashedPaswword;


    //save the user and check for errors
    try {
        const u = await user.save();
        res.status(201).json(u);
    } catch(err) {
        res.status(409).end();
    }
};

//Handle view user info
exports.view = async (req, res) => {
    console.log(req.params.id);
    if (!req.params.id) {
        return res.status(400).end();
    }
    try {
        const u = await User.findById(req.params.id);
        res.json(u);
    }catch(err) {
        res.status(404).end();
    }

};

//Handle update user info
exports.update =  async (req, res) => {
    if (!req.params.id) {
        return res.status(400).end();
    }

    let user;

    try {
        user = await User.findById(req.params.id);
        user.name = req.body.name ? req.body.name : user.name;
        user.email = req.body.email ? req.body.email : user.email;
        user.password = req.body.password ? req.body.password : user.password;

    }catch(err) {
        console.log(err);
        res.status(404).end();
    }

    try {
        await user.save();
        return res.status(202).json(user);
    } catch(err) {
        res.status(409).end();
    }
};

//Handle delete contact
exports.delete = async (req, res) => {
    if (!req.params.id) {
        return res.status(400).end();
    }
    try {
        const u = await User.findByIdAndUpdate(req.params.id, { is_deleted: true} );
        return res.json(u);
    } catch(err) {
        res.status(409).end();
    }
};
*/
