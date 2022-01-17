package rodion.springproject.springdatajpa.dao.jdbctemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rodion.springproject.springdatajpa.dao.BookDao;
import rodion.springproject.springdatajpa.dao.mappers.BookMapper;
import rodion.springproject.springdatajpa.domain.Book;

@Component
public class BookDaoImplJdbcTemplate implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImplJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?", getRowMapper(), id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return jdbcTemplate.queryForObject("SELECT * FROM book where title = ?",
                getRowMapper(),
                title);
    }

    @Override
    public Book findByISBN(String isbn) {
        return null;
    }

    @Override
    public Book save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)",
                book.getTitle(),
                book.getIsbn(),
                book.getPublisher(),
                book.getAuthorId());

        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        //        toReturn.setAuthor(Author
//                .builder()
//                .id(book.getAuthor().getId())
//                .firstName(book.getAuthor().getFirstName())
//                .lastName(book.getAuthor().getLastName())
//                .build());
        return this.getById(createdId);
    }

    @Override
    public Book update(Book book) {
        jdbcTemplate.update("UPDATE book SET title  = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?",
                book.getTitle(),
                book.getIsbn(),
                book.getPublisher(),
                book.getAuthorId(),
                book.getId());

        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    private RowMapper<Book> getRowMapper() {
        return new BookMapper();
    }

}















