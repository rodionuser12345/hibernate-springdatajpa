package rodion.springproject.springdatajpa.dao;

import rodion.springproject.springdatajpa.domain.Book;

public interface BookDao {

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book findByISBN(String isbn);

    Book save(Book book);

    Book update(Book book);

    void deleteBookById(Long id);
}
