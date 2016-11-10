
// API documentation - https://github.com/visionmedia/supertest
var request = require('supertest');

var app = require("../app");


describe("RemoveBookResource", function () {

    describe("DELETE /api/books/:bookId", function () {

        it("bookId = 3", function (done) {
            request(app)
                .delete('/api/books/3')//correct input
                .set('Accept', 'application/json')
                .expect(200, done);
        });

        it("bookId = undefined", function (done) {

            request(app)
                .delete('/api/books')//wrong input
                .set('Accept', 'application/json')
                .expect(404, done);

        });

        it("bookId = 'henry'", function (done) {

            request(app)
                .delete('/api/books/henry')//wrong input
                .set('Accept', 'application/json')
                .expect(404, done);

        });
    });
});