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

    module.exports = function (callback, title) { // The title is optional and is only present when searching. (You need yo modify the books.js file first)
    	// getBooks
        LibraryDAO.readXMLFile(function(data){
            if(data != null) callback(loadBooks(data));
        });
    };

}());
