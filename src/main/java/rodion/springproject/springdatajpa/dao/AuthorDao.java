package rodion.springproject.springdatajpa.dao;

import rodion.springproject.springdatajpa.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author getById(Long id);

    Author findAuthorByFistNameAndLastName(String firstName, String lastName);

    Author findAuthorByNameCriteria(String firstName, String lastName);

    List<Author> findAll();

    List<Author> listAuthorByLastNameLike(String name);

    Author save(Author author);

    Author update(Author author);

    void deleteAuthorById(Long id);
}
