var expect    = require("chai").expect;
var remove = require("../app/resources/RemoveBookResource");
var getbooks = require("../app/resources/GetBooksResource");

describe("Test RemoveBookResource and GetBooksResource", function () {

    describe("delete book with id: ", function () {

        it("incorrect id value", function (done) {//this needs to be async but i do not know how if i cant add module..
        	getbooks(function(books){
        		var orginalLength = books.length;//got the orginal length
        		if(orginalLength > 0){
                    console.log('\tgot bookList');
        			var deleteId = 'id';//incorrect id value
        			console.log('\tdeleting bookid: '+deleteId+', title: ' + books[0].title[0]);
        			deleteBook(books, deleteId, 0, done);//orignal - changed length should therefor be the same! (0)
        		}
        		else console.log('\tadd some books to delete!');
        	});
        });
        it("correct id value", function (done) {
        	getbooks(function(books){
        		var orginalLength = books.length;//got the orginal length
        		if(orginalLength > 0){
                    console.log('\tgot bookList');
        			var deleteId = books[0].id;
        			console.log('\tdeleting bookid: '+deleteId+', title: ' + books[0].title[0]);
        			deleteBook(books, deleteId, 1, done);// one book should be removed therfore 1
        		}
        		else console.log('\tadd some books to delete!');
        	});
        });
    });
});

function deleteBook(books, id, eq, done){
	remove(id, function(xml){
		expect(books.length - xml.catalog.book.length).to.equal(eq);
		done();
	});
}