(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');


    module.exports = function (data, callback) {
    	if(data.title[0] == ' ' || data.author[0] == ' '){
    		//must contain title and author
    		callback('no data');
    	}
    	else {
    		LibraryDAO.readXMLFile(function(xml){

	    		if(xml != null){
	    			var found = false;
    				var maxId = -1;
		    		var books = xml.catalog.book;
		    		for(var i = 0; i < books.length; i++){
		    			if(books[i].title[0] == data.title &&
	    					books[i].author[0] == data.author){ //exist only if title AND author is same!
	    					callback('book found');
	    					found = true;
	    				}
	    				else if(Number(books[i].$.id) >= maxId){
	    					maxId = Number(books[i].$.id);//must be converted into number
	    				}
		    		}

	    			if(!found){
	    				var id = (maxId != -1) ? (maxId + 1) : 0;
	    				var bookItem = {
	    					$: {id: id},
	    					author: [data.author],
	    					title: [data.title],
	    					price: [(data.price[0] != ' ' ? data.price : ' ')],
	    					genre: [(data.genre[0] != ' ' ? data.genre : ' ')],
	    					publish_date: [(data.publish_date[0] != ' ' ? data.publish_date : ' ')],
	    					description: [(data.description[0] != ' ' ? data.description : ' ')]
	    				};//for some dumb reason it just cant be a normal json... (no [''].. and $ <- for id.. :|)

	    				books.push(bookItem);
	    				LibraryDAO.writeXMLFile(xml);
	    				callback();
	    			}
		    	}
	    	});
    	}
    };

}());
