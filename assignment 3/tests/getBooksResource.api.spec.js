
// API documentation - https://github.com/visionmedia/supertest
var request = require('supertest');

var app = require("../app");


describe("GetBooksResource", function () {

    describe("GET /api/books", function () {

        it("respond with get booklist", function (done) {

            request(app)
                .get('/api/books')
                .set('Accept', 'application/json')
                .expect(200, done);//i would like to compare and stuff
        });
    });
});
