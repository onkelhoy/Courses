
// API documentation - https://github.com/visionmedia/supertest
var request = require('supertest');

var app = require("../app");


describe("AddBookResource", function () {

    describe("PUT /api/books/", function () {

        it("not implemented yet (aka no data sent)", function (done) {
            request(app)
                .put('/api/books')//correct input (later body data will be sent!)
                .set('Accept', 'application/json')
                .expect(200, done);
        });
    });
});
