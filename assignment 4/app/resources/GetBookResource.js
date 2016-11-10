(function () {
    "use strict";

    var LibraryDAO = require('../dao/LibraryDAO');
    var gbs = require('./GetBooksResource');


    module.exports = function (id, callback) {
        LibraryDAO.readXMLFile(function(xml){
            if(xml != null){
                var books = xml.catalog.book;
                var book = null;
                var selectIndex = -1;
                for(var i = 0; i < books.length; i++){
                    if(id == books[i].$.id){
                       book = books[i];
                       selectIndex = i;
                    }
                }
                callback(book, xml, selectIndex);
            }
        });
    };

}());
