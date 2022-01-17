package rodion.springproject.springdatajpa.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import rodion.springproject.springdatajpa.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Book
                .builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .isbn(rs.getString("isbn"))
                .publisher(rs.getString("publisher"))
                .build();
    }
}
