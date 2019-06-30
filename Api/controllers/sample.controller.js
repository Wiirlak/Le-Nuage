'use script';

const models = require('../models');
const Sample = models.Sample;

class SampleController {

    async addSample(data) {
        return Sample.create({
           data
        });
    }

    async getSample(id) {
        return Sample.findOne({
            where: {
                id: id
            }
        });
    }

}

module.exports = new SampleController();
