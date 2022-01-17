package rodion.springproject.springdatajpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rodion.springproject.springdatajpa.repository.BookRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SpringDataJpaApplicationTests {

	@Autowired
	BookRepository bookRepository;

	@Test
	void testBookRepo(){
		long count = bookRepository.count();

		assertThat(count).isGreaterThan(0);
	}

	@Test
	void contextLoads() {
	}

}
