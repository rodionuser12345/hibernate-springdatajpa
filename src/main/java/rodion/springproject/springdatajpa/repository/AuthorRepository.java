package rodion.springproject.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodion.springproject.springdatajpa.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
