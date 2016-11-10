(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');
    var book = require('../models/book');

    
    

    function loadBooks(xml){
        var booksJson = xml.catalog.book;
        var books = [];

        for(var i = 0; i < booksJson.length; i++){
                var b = booksJson[i],
                id = b.$.id,
                title = b.title,
                genre = b.genre,
                des = b.description,
                author = b.author,
                price = b.price,
                date = b.publish_date;
            books.push(book.create(title, price, author, genre, date, des, id));
        }
        return books;
    }

    module.exports = function (keyword, callback) { // The title is optional and is only present when searching. (You need yo modify the books.js file first)
    	// getBooks
        LibraryDAO.readXMLFile(function(data){
            if(data != null) {
                var books = loadBooks(data);

                if(keyword.title != undefined){
                    //searching
                    //xpath selections would be much better but as this approch gives it in json..
                    var matches = [];
                    for(var i = 0; i < books.length; i++){
                        var reg = keyword.title;
                        if(books[i].title[0].match(reg) != null
                             || books[i].author[0].match(reg) != null ){//can search for author too
                            matches.push(books[i]);// not the fastest way using match either.. 
                            //but it will do just fine!
                        }
                    }
                    callback(matches);
                }
                else {
                    //getting all books
                    callback(books);
                }
            }
            else callback(null);
        });
    };

}());
