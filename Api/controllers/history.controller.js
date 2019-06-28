'use strict';

const History = require('../models').History;

class HistoryController {

    async addToHistory(action, userId, entityId, nuageId, details) {
        const history = new History();
        history.action = action;
        history.user = userId;
        if (entityId) {
            history.entity = entityId;
        }
        if (nuageId) {
            history.nuage = nuageId;
        }
        if (details) {
            history.details = details;
        }
        try {
            return await history.save();
        } catch (e) {
            return undefined;
        }
    }

    async getAll() {
        const histories = await History.find().populate('user entity nuage');

        if (histories.length > 0 && histories !== null) {
            return histories;
        }

        return undefined;
    }

    async getHistoryByUserId(userId) {
        const histories = await History.find({ user: userId}).populate('user entity nuage');
        if (histories.length > 0 && histories !== null) {
            return histories;
        }
        return undefined;
    }

    async getHistoryByEntityId(entityId) {
        const histories = await History.find({ entity: entityId}).populate('user entity nuage');
        if (histories.length > 0 && histories !== null) {
            return histories;
        }
        return undefined;
    }

}

module.exports = new HistoryController();
