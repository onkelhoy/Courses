(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');
    var books = [];
    var book = require('../models/book');

	books = book.getBooks();//this to fill the book list
    books.push(book.create('The book of nothing','henry pap','adventure','1998-03-05','this book does not exist')); // this to show that a book can be created


    module.exports = function (callback, title) { // The title is optional and is only present when searching. (You need yo modify the books.js file first)
    	// getBooks
        console.log(books);
        callback(books);
    };

}());
