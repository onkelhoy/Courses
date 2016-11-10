(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');


    module.exports = function (id, data, callback) {
    	LibraryDAO.readXMLFile(function(xml){
    		if(xml != null){
	    		var books = xml.catalog.book;
	    		var success = false;
	    		for(var i = 0; i < books.length; i++){
	    			if(id == books[i].$.id){
	    				success = true;
	    			}
	    		}
	    		callback(success);
	    	}
    	});
    };

}());
