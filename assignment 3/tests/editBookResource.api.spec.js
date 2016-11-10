
// API documentation - https://github.com/visionmedia/supertest
var request = require('supertest');

var app = require("../app");


describe("EditbookResource", function () {

    describe("POST /api/books/:bookId", function () {

        it("bookId = 5 (200)", function (done) {
            request(app)
                .post('/api/books/5')//correct input (book exist)
                .set('Accept', 'application/json')
                .expect(200, done);
        });
        it("bookId = 3 (404)", function (done) {
            request(app)
                .post('/api/books/3')//incorrect input (book does not exist)
                .set('Accept', 'application/json')
                .expect(404, done);
        });
    });
});
