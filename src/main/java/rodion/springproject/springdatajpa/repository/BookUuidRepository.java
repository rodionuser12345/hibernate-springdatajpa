package rodion.springproject.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodion.springproject.springdatajpa.domain.uuid.BookUuid;

import java.util.UUID;

public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
