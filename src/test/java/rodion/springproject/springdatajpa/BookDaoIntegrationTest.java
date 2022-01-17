package rodion.springproject.springdatajpa;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import rodion.springproject.springdatajpa.dao.BookDao;
import rodion.springproject.springdatajpa.domain.Author;
import rodion.springproject.springdatajpa.domain.Book;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"rodion.springproject.springdatajpa.dao"})
class BookDaoIntegrationTest {

    @Qualifier("bookDaoImplJdbcTemplate")
    @Autowired
    BookDao bookDaoJdbcTemplate;

    @Qualifier("bookDaoImplJdbc")
    @Autowired
    BookDao bookDaoJdbc;

    @Qualifier("bookDaoImplHibernate")
    @Autowired
    BookDao bookDaoHibernate;

    static Author author;
    static Book book;

    @BeforeAll
    static void defineStaticMembers() {
        author = Author
                .builder()
                .id(5L)
                .firstName("A")
                .lastName("R")
                .build();

        book = Book
                .builder()
                .id(6L)
                .title("Book")
                .publisher("BP")
                .isbn("555-666-777-8000")
                .authorId(author.getId())
                .build();
    }


    @Test
    void testGetBookByIdJdbc() {
        Book book = bookDaoJdbc.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    void testFindBookByTitleJdbc() {
        Book book = bookDaoJdbc.findBookByTitle("Freedom");
        assertThat(book).isNotNull();
    }

    @Test
    void testSaveBookJdbc() {
        Book savedBook = bookDaoJdbc.save(book);
        assertThat(savedBook).isNotNull();
    }

    @Test
    void testUpdateBookJdbc() {
        Book savedBook = bookDaoJdbc.save(book);

        savedBook.setTitle("ABC");

        Book updatedBook = bookDaoJdbc.update(savedBook);

        assertThat(updatedBook.getTitle()).isEqualTo("ABC");
    }

    @Test
    void testDeleteBookByIdJdbc() {
        Book savedBook = bookDaoJdbc.save(book);

        bookDaoJdbc.deleteBookById(savedBook.getId());

        Book deleted = bookDaoJdbc.getById(savedBook.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void testGetBookByIdJdbcTemplate() {
        Book book = bookDaoJdbcTemplate.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    void testFindBookByTitleJdbcTemplate() {
        Book book = bookDaoJdbcTemplate.findBookByTitle("Freedom");
        assertThat(book).isNotNull();
    }

    @Test
    void testSaveBookJdbcTemplate() {
        Book savedBook = bookDaoJdbcTemplate.save(book);
        assertThat(savedBook).isNotNull();
    }

    @Test
    void testUpdateBookJdbcTemplate() {
        Book savedBook = bookDaoJdbcTemplate.save(book);
        savedBook.setTitle("ABC");

        Book updatedBook = bookDaoJdbcTemplate.update(savedBook);

        assertThat(updatedBook.getTitle()).isEqualTo("ABC");
    }

    @Test
    void testDeleteBookByIdJdbcTemplate() {
        Book savedBook = bookDaoJdbcTemplate.save(book);

        bookDaoJdbcTemplate.deleteBookById(savedBook.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> bookDaoJdbcTemplate.getById(savedBook.getId()));
    }

    @Test
    void testGetBookByIdHibernate() {
        Book book = bookDaoHibernate.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    void testFindBookByTitleHibernate() {
        Book book = bookDaoHibernate.findBookByTitle("Freedom");
        assertThat(book).isNotNull();
    }

    @Test
    void testSaveBookHibernate() {
        Book savedBook = bookDaoHibernate.save(book);
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    void testUpdateBookHibernate() {
        Book savedBook = bookDaoHibernate.save(book);

        savedBook.setTitle("GRAND");

        Book updatedBook = bookDaoHibernate.update(savedBook);

        assertThat(updatedBook.getTitle()).isEqualTo("GRAND");
    }

    @Test
    void testDeleteBookByIdHibernate() {
        Book saved = bookDaoHibernate.save(book);

        bookDaoHibernate.deleteBookById(saved.getId());

        Book deleted = bookDaoHibernate.getById(saved.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void testFindByISBNHibernate() {
        book.setIsbn("123" + RandomString.make());

        Book saved = bookDaoHibernate.save(book);
        Book fetched = bookDaoHibernate.findByISBN(saved.getIsbn());

        assertThat(fetched).isNotNull();
    }
}
















