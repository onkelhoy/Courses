(function () {
    "use strict";

    var express = require('express');
    var router = express.Router();

    var AddBookResource = require('../../resources/AddBookResource');
    var EditBookResource = require('../../resources/EditBookResource');
    var GetBookResource = require('../../resources/GetBookResource');
    var GetBooksResource = require('../../resources/GetBooksResource');
    var RemoveBookResource = require('../../resources/RemoveBookResource');



    router.get('/', function (req, res) {
        res.type('json');

        GetBooksResource(req.query, function (data) {
            if(data != null) res.send(data);
            else res.status(500).end('error');
        });
    });


    router.put('/', function (req, res) {
        res.type('json');
        AddBookResource(req.body, function (response) {
            if(response == 'no data'){
                res.status(500); //server error
            }
            else if(response == 'book found'){
                res.status(406); //not accepteble
            }//else status -> 200 (ok)
            res.send("{}");
        });
    });


    router.route('/:bookId')
        .get(function (req, res) {
            res.type('json');

            GetBookResource(req.params.bookId, function (data) {
                if(data != null) {
                    res.send(data);}
                else res.status(404).send({});
            });
        })

        .post(function (req, res) {
            res.type('json');

            EditBookResource(req.params.bookId, req.body, function (success) {
                if(success) res.send("{}");
                else if(success == 500) res.status(500).send({});
                else res.status(404).send({});
            });
        })

        .delete(function (req, res) {
            res.type('json');

            RemoveBookResource(req.params.bookId, function (xml, found) {
                if(found) res.send(xml);
                else res.status(404).send('{}');
            });
        });

    module.exports = router;
}());
