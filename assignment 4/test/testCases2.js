var expect    = require("chai").expect;
var addBook = require("../app/resources/AddBookResource");
var editBook = require("../app/resources/EditBookResource");
var getbooks = require("../app/resources/GetBooksResource");//search
var getBook = require("../app/resources/GetBookResource");

describe("Test add, edit and search", function () {

    describe("addBook", function () {

        var empty = {
            title: [ ' ' ],
            author: [ ' ' ],
            publish_date: [ ' ' ],
            price: [ ' ' ],
            description: [ ' ' ],
            genre: [ ' ' ]
        };

        it("scenario 1", function (done) {
        	//add book with all info filled
            var data = {
                title: 'Lord of the flies',
                author: 'William Golding',
                genre: 'fiction',
                price: 69,
                description: 'Stranded on an island, a group of schoolboys degenerate into savagery.',
                publish_date: '1954'
            };
            getbooks('', function(books){
                addBook(data, function(){
                    //check if list is one grater!
                    checkList(books, -1, done);//one book added, so.. oldlist - newlsit = -1
                });  
            });
        });
        it("scenario 2", function (done) {
            //all info but title is set
            var data = {
                title: [ ' ' ],//title not set
                author: 'William Golding',
                genre: 'fiction',
                price: 69,
                description: 'Stranded on an island, a group of schoolboys degenerate into savagery.',
                publish_date: '1954'
            };
            getbooks('', function(books){
                addBook(data, function(){
                    checkList(books, 0, done);//not added, so.. oldlist - newlsit = 0 (they should be the same)
                });  
            });
        });
        it("scenario 3", function (done) {
            //only title and author are set
            var data = {
                title: 'my own book',
                author: 'Henry Pap',
                publish_date: [ ' ' ],
                price: [ ' ' ],
                description: [ ' ' ],
                genre: [ ' ' ]
            };

            getbooks('', function(books){
                addBook(data, function(){
                    checkList(books, -1, done);//book added (same as scenario 1)
                });  
            });
        });
        it("scenario 4", function (done) {
            //no info is set
            getbooks('', function(books){
                addBook(empty, function(){
                    checkList(books, 0, done);//no book added (same list)
                });  
            });
        });
        it("scenario 5", function (done) {
            //book exist
            var data = {
                title: 'Lord of the flies',//title not set
                author: 'William Golding',
                genre: 'fiction',
                price: 69,
                description: 'Stranded on an island, a group of schoolboys degenerate into savagery.',
                publish_date: '1954'
            };
            getbooks('', function(books){
                addBook(data, function(){
                    checkList(books, 0, done);//not added
                });  
            });
        });
    });

    describe("editBook", function () {//part for the edit iteration (4 scenarios.. pwuuh this documents starts to get long heh)
        it("scenario 1", function (done) {
            //change every info
            getbooks({title: 'Lord of the flies'}, function(book){
                var id = book[0].id;
                var des = book[0].description;//in case javascript changes every by referenve values
                 var data = {
                    title: 'Lord of the falcon',
                    author: 'Edwin Gothard',
                    genre: 'horror',
                    price: 1000,
                    description: 'A group of birds attack babys in their swings while the mothers watch',
                    publish_date: '1966'
                };
                editBook(id, data, function(){
                    getBook(id, function(changedbook){
                        expect(checkSimilarity(book[0], changedbook, des)).to.equal(false);//book has been updated
                        done();
                    });
                });
            });
        });
        it("scenario 2", function (done) {
            //no set values
            getbooks({title: 'Lord of the falcon'}, function(book){
                var id = book[0].id;
                var des = book[0].description;//in case javascript changes every by referenve values
                
                var data = {
                    title: [ ' ' ],
                    author: [ ' ' ],
                    publish_date: [ ' ' ],
                    price: [ ' ' ],
                    description: [ ' ' ],
                    genre: [ ' ' ]
                };
                editBook(id, data, function(){
                    getBook(id, function(changedbook){
                        expect(checkSimilarity(book[0], changedbook, des)).to.equal(true);//book has been updated
                        done();
                    });
                });
            });
        });
        it("scenario 3", function (done) {
            //at least one field is set
            getbooks({title: 'Lord of the falcon'}, function(book){
                var id = book[0].id;
                var data = {
                    title: [ ' ' ],
                    author: [ ' ' ],
                    publish_date: [ ' ' ],
                    price: 10000,
                    description: [ ' ' ],
                    genre: [ ' ' ]
                };//one changed value
                editBook(id, data, function(status){
                    getBook(id, function(changed){
                        expect(Number(changed.price[0])).to.equal(10000);
                        done();
                    });
                });
            });
        });
        it("scenario 4", function (done) {
            //change book to an already existing one
            getbooks({title: 'Lord of the falcon'}, function(book){
                var id = book[0].id;
                var des = book[0].description;//in case javascript changes every by referenve values
                
                var data = {
                    title: 'my own book',
                    author: 'Henry Pap',
                    publish_date: [ ' ' ],
                    price: [ ' ' ],
                    description: [ ' ' ],
                    genre: [ ' ' ]
                };//book already exist!
                editBook(id, data, function(status){
                    expect(status).to.equal(500);
                    done();
                });
            });
        });
    });

    describe("search book", function () {
        //scenario 1 has been tested alot by previous code!
        it("scenario 2", function (done) {
            //blank search
            getbooks({title: ''}, function(books){
                getbooks('', function(allBooks){
                    expect(books.length).to.equal(allBooks.length);
                    done();
                });
            });
        });
        it("scenario 3", function (done) {
            //no match
            getbooks({title: 'ääääääääääääää'}, function(books){
                expect(books.length).to.equal(0);
                done();
            });
        });
    });
});

function checkList(books, eq, done){
	getbooks('', function(newbooks){
		expect(books.length - newbooks.length).to.equal(eq);
		done();
	});
}

function checkSimilarity(oBook, cBook, des){
    return  oBook.title[0] == cBook.title[0] &&
            oBook.author[0] == cBook.author[0] &&
            oBook.description[0] == cBook.description[0] && 
            cBook.description[0] == des &&
            oBook.publish_date[0] == cBook.publish_date[0] &&
            oBook.price[0] == cBook.price[0] &&
            oBook.genre[0] == cBook.genre[0];
}