var idCount = 0;
var Book = function(title, author, genre, publishDate, description){
	var bookObj = new Object();
	// quick n dirty Object..
	bookObj.id = idCount;
	idCount++;

	bookObj.title = title;
	bookObj.author = author;
	bookObj.genre = genre;
	bookObj.publishDate = publishDate;
	bookObj.description = description;

	return bookObj;
}

exports.create = Book;

exports.getBooks = function(){//ill keep the creation part here
	var books = [];
	books.push(new Book('The book of nothing', 'Mr. Owen', 'thriller', '1945', 'a book about nothing'));
	books.push(new Book('Harry Potter', 'JK rowling', 'fantasy', '1997', 'Harry Potter is dumbledore'));
	books.push(new Book('Ghost of Mary', 'Rodger Jenkins', 'horror', '1932', 'this book does not exist'));
	books.push(new Book('The Viper', 'Áttila Vérdes', 'sci fi', '3089', 'hah.. fooled'));
	books.push(new Book('Emils Hallonbuske', 'Rut Gunlöv', 'action', '1984', 'Emil står inför en evig kamp, mellan de goda och de onda. Men när emil finner en buske istället så tar boken slut.'));
	return books;
}