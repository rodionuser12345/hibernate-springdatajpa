package rodion.springproject.springdatajpa.dao.jdbctemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import rodion.springproject.springdatajpa.dao.AuthorDao;
import rodion.springproject.springdatajpa.dao.mappers.AuthorMapper;
import rodion.springproject.springdatajpa.domain.Author;

import java.util.List;

@Component
public class AuthorDaoImplJdbcTemplate implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImplJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?", getRowMapper(), id);
    }

    @Override
    public Author findAuthorByFistNameAndLastName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject("SELECT * FROM author where first_name = ? and last_name = ?",
                getRowMapper(),
                firstName, lastName);
    }

    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return null;
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String name) {
        return null;
    }

    @Override
    public Author save(Author author) {
        jdbcTemplate.update("INSERT INTO author (first_name, last_name) VALUES (?, ?)",
                author.getFirstName(),
                author.getLastName());

        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return this.getById(createdId);
    }

    @Override
    public Author update(Author author) {
        jdbcTemplate.update("UPDATE author SET first_name  = ?, last_name = ? WHERE id = ?",
                author.getFirstName(),
                author.getLastName(),
                author.getId());

        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        jdbcTemplate.update("DELETE FROM author WHERE id = ?", id);
    }

    private RowMapper<Author> getRowMapper() {
        return new AuthorMapper();
    }

}















