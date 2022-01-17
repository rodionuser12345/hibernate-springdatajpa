package rodion.springproject.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodion.springproject.springdatajpa.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
