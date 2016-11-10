(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');
    var gbs = require('./GetBooksResource');


    module.exports = function (id, callback) {
    	LibraryDAO.readXMLFile(function(xml){
    		if(xml != null){
	    		var books = xml.catalog.book;
	    		for(var i = 0; i < books.length; i++){
	    			if(id == books[i].$.id){
	    				xml.catalog.book.splice(i, 1);
	    			}
	    		}
	    		LibraryDAO.writeXMLFile(xml);
	    		callback(xml);
	    	}
    	});
    };

}());
