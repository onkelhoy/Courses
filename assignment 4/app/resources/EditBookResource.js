(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');
    var getBook = require('./GetBookResource');
    var getBooks = require('./GetBooksResource');


    module.exports = function (id, data, callback) {
    	getBook(id, function(book, xml, index){	// for me this isnt even reachable as api cant even get the id 
    		var books = xml.catalog.book;		// same problem you guys said you FIXED!!!
    		if(books[index].$.id == id){		// but im going blind on this and hopefully it should work
    			//make changes!						(well later on test i will see if it works or not..)

    			//im asuming data comes with the whole book (just like addbook)
    			var bookItem = {
	    			$: {id: id},
	    			author: [(data.author[0] != ' ' ? data.author : books[index].author[0])],
	    			title: [(data.title[0] != ' ' ? data.title : books[index].title[0])],
	    			genre: [(data.genre[0] != ' ' ? data.genre : books[index].genre[0])],
                    price: [(data.price[0] != ' ' ? data.price : books[index].price[0])],
	    			publish_date: [(data.publish_date[0] != ' ' ? data.publish_date : books[index].publish_date[0])],
	    			description: [(data.description[0] != ' ' ? data.description : books[index].description[0])]
	    		};

                var t = bookItem.title[0],
                    ot = books[index].title[0],
                    a = bookItem.author[0],
                    oa = books[index].author[0];


                if(t != ot || a != oa){
                    //there is a chance that book has same title and author as other book now
                    var found = false;
                    for(var i = 0; i < books.length; i++){
                        if(books[i].title[0] == t && books[i].author[0] == a){
                            callback(500);//Aha! there already exist such a book! cant edit this book then!
                            found = true;
                            break;
                        }
                    }
                    if(!found){//no same book (everything is oke!)
                        returnEdit(books, index, xml, bookItem, callback);       
                    }
                }
                else returnEdit(books, index, xml, bookItem, callback);//no title or author changes (ok)
    		}
    		else {
    			callback(false);
    		}
    	});
    };

    function returnEdit(books, index, xml, bookItem, callback){
        books[index] = bookItem;
        LibraryDAO.writeXMLFile(xml);
        callback(true);
    }

}());
