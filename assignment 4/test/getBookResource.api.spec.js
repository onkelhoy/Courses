
// API documentation - https://github.com/visionmedia/supertest
var request = require('supertest');

var app = require("../app");


describe("GetbookResource", function () {

    describe("GET /api/books/:bookId", function () {

        it("bookId = 1", function (done) {
            request(app)
                .get('/api/books/1')//correct input
                .set('Accept', 'application/json')
                .expect(200, done);
        });

        it("bookId = 3", function (done) {
            request(app)
                .get('/api/books/3')//incorrect input as book was deleted!
                .set('Accept', 'application/json')
                .expect(404, done);//this book was deleted!
        });

        it("bookId = 'henry'", function (done) {
            request(app)
                .get('/api/books/henry')//correct input
                .set('Accept', 'application/json')
                .expect(404, done);
        });
    });
});
