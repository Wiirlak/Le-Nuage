'use strict';

const HistoryFile = require('../models').HistoryFile;

class HistoryFileController {

	async getHistoryByParentIdAndName(parentId,name,limit){
        console.log(parentId,"  ",name)
        const histories = await HistoryFile.find({ parentId: parentId, filename:name}).sort({ date : 'desc'}).limit(parseInt(limit)).populate("user");
        if (histories.length > 0 && histories !== null) {
            return histories;
        }
        return undefined;
    }

    async add(action,user,entity,parentId,filename){
    	const historyFile = new HistoryFile();
    	historyFile.action = action;
    	if(user)
    		historyFile.user = user;
    	if(entity)
    		historyFile.entity = entity;
    	if(parentId)
    		historyFile.parentId = parentId;
    	if(filename)
    		historyFile.filename = filename;
    	try {
            return await historyFile.save();
        } catch (e) {
            return undefined;
        }
    }

}

module.exports = new HistoryFileController();