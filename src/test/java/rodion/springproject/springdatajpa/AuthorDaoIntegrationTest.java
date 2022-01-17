package rodion.springproject.springdatajpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import rodion.springproject.springdatajpa.dao.AuthorDao;
import rodion.springproject.springdatajpa.domain.Author;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"rodion.springproject.springdatajpa.dao"})
class AuthorDaoIntegrationTest {

    @Qualifier("authorDaoImplJdbcTemplate")
    @Autowired
    AuthorDao authorDaoJdbcTemplate;

    @Qualifier("authorDaoImplJdbc")
    @Autowired
    AuthorDao authorDaoJdbc;

    @Qualifier("authorDaoImplHibernate")
    @Autowired
    AuthorDao authorDaoHibernate;

    @Test
    void testGetAuthorByIdJdbc() {
        Author author = authorDaoJdbc.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void testFindAuthorByNameJdbc() {
        Author author = authorDaoJdbc.findAuthorByFistNameAndLastName("Sam", "Fischer");
        assertThat(author).isNotNull();
    }

    @Test
    void testSaveAuthorJdbc() {
        Author author = Author.builder().firstName("Jack").lastName("Nickelson").build();
        Author savedAuthor = authorDaoJdbc.save(author);
        assertThat(savedAuthor).isNotNull();
    }

    @Test
    void testUpdateAuthorJdbc() {
        Author author = Author.builder().firstName("Mike").lastName("Spenser").build();
        Author savedAuthor = authorDaoJdbc.save(author);

        savedAuthor.setFirstName("Michael");

        Author updatedAuthor = authorDaoJdbc.update(savedAuthor);

        assertThat(updatedAuthor.getFirstName()).isEqualTo("Michael");
    }

    @Test
    void testDeleteAuthorByIdJdbc() {
        Author author = Author.builder().firstName("Steven").lastName("Segal").build();
        Author savedAuthor = authorDaoJdbc.save(author);

        authorDaoJdbc.deleteAuthorById(savedAuthor.getId());

        Author deleted = authorDaoJdbc.getById(savedAuthor.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void testGetAuthorByIdJdbcTemplate() {
        Author author = authorDaoJdbcTemplate.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void testFindAuthorByNameJdbcTemplate() {
        Author author = authorDaoJdbcTemplate.findAuthorByFistNameAndLastName("Sam", "Fischer");
        assertThat(author).isNotNull();
    }

    @Test
    void testSaveAuthorJdbcTemplate() {
        Author author = Author.builder().firstName("Jack").lastName("Nickelson").build();
        Author savedAuthor = authorDaoJdbcTemplate.save(author);
        assertThat(savedAuthor).isNotNull();
    }

    @Test
    void testUpdateAuthorJdbcTemplate() {
        Author author = Author.builder().firstName("Mike").lastName("Spenser").build();
        Author savedAuthor = authorDaoJdbcTemplate.save(author);

        savedAuthor.setFirstName("Michael");

        Author updatedAuthor = authorDaoJdbcTemplate.update(savedAuthor);

        assertThat(updatedAuthor.getFirstName()).isEqualTo("Michael");
    }

    @Test
    void testDeleteAuthorByIdJdbcTemplate() {
        Author author = Author.builder().firstName("Steven").lastName("Segal").build();
        Author savedAuthor = authorDaoJdbcTemplate.save(author);

        authorDaoJdbcTemplate.deleteAuthorById(savedAuthor.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> authorDaoJdbcTemplate.getById(savedAuthor.getId()));
    }

    @Test
    void testGetAuthorByIdHibernate() {
        Author author = authorDaoHibernate.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void testFindAuthorByNameHibernate() {
        Author author = authorDaoHibernate.findAuthorByFistNameAndLastName("Sam", "Fischer");
        assertThat(author).isNotNull();
    }

    @Test
    void testFindAuthorByNameCriteriaHibernate() {
        Author author = authorDaoHibernate.findAuthorByNameCriteria("Sam", "Fischer");
        assertThat(author).isNotNull();
    }

    @Test
    void testFindAll(){
        List<Author> authors = authorDaoHibernate.findAll();

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testSaveAuthorHibernate() {
        Author author = Author.builder().firstName("Jack").lastName("Nickelson").build();
        Author savedAuthor = authorDaoHibernate.save(author);
        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }

    @Test
    void testUpdateAuthorHibernate() {
        Author author = Author.builder().firstName("Mike").lastName("Spenser").build();
        Author savedAuthor = authorDaoHibernate.save(author);

        savedAuthor.setFirstName("Michael");

        Author updatedAuthor = authorDaoHibernate.update(savedAuthor);

        assertThat(updatedAuthor.getFirstName()).isEqualTo("Michael");
    }

    @Test
    void testDeleteAuthorByIdHibernate() {
        Author author = Author.builder().firstName("Steven").lastName("Segal").build();
        Author savedAuthor = authorDaoHibernate.save(author);

        authorDaoHibernate.deleteAuthorById(savedAuthor.getId());

        Author deleted = authorDaoHibernate.getById(savedAuthor.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void testListAuthorByLastNameLikeHibernate() {
        List<Author> authors = authorDaoHibernate.listAuthorByLastNameLike("Spen");

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }
}
















