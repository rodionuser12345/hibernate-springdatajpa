package rodion.springproject.springdatajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rodion.springproject.springdatajpa.domain.uuid.AuthorUuid;

import java.util.UUID;

public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, UUID> {
}
