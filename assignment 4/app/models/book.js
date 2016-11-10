var currentId = 0;
var Book = function(title, price, author, genre, publishDate, description, id){
	var bookObj = new Object();
	// quick n dirty Object..
	
	bookObj.id = id;
	bookObj.title = title;
	bookObj.author = author;
	bookObj.price = price;
	bookObj.genre = genre;
	bookObj.publish_date = publishDate;
	bookObj.description = description;

	return bookObj;
}

exports.create = Book;