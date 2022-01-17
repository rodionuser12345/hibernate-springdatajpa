package rodion.springproject.springdatajpa.dao.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import rodion.springproject.springdatajpa.dao.AuthorDao;
import rodion.springproject.springdatajpa.dao.BookDao;
import rodion.springproject.springdatajpa.domain.Book;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class BookDaoImplJdbc implements BookDao {

    private final DataSource dataSource;
    private final AuthorDao authorDao;

    public BookDaoImplJdbc(DataSource dataSource, @Qualifier("authorDaoImplJdbc") AuthorDao authorDao) {
        this.dataSource = dataSource;
        this.authorDao = authorDao;
    }

    @Override
    public Book getById(Long id) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book where id = ?");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getBookFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, ps, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Book findBookByTitle(String title) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book where title = ?");
            ps.setString(1, title);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getBookFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, ps, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Book findByISBN(String isbn) {
        return null;
    }

    @Override
    public Book save(Book book) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)");
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getPublisher());

            if (book.getAuthorId() != null) {
                ps.setLong(4, book.getAuthorId());
            }
            else {
                ps.setNull(4, -5);
            }

            ps.execute();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

            if (resultSet.next()) {
                Long savedId = resultSet.getLong(1);
                return this.getById(savedId);
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, ps, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Book update(Book book) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("UPDATE book SET title  = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?");
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getPublisher());
            if (book.getAuthorId() != null) {
                ps.setLong(4, book.getAuthorId());
            }
            else {
                ps.setNull(4, -5);
            }
            ps.setLong(5, book.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(resultSet, ps, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("DELETE FROM book WHERE id = ?");
            ps.setLong(1, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeAll(null, ps, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeAll(ResultSet resultSet, PreparedStatement ps, Connection connection) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        return Book
                .builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .isbn(resultSet.getString("isbn"))
                .publisher(resultSet.getString("publisher"))
                .authorId(resultSet.getLong("author_id"))
                .build();
    }
}















