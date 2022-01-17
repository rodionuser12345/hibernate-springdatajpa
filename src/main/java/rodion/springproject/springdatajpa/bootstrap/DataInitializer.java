package rodion.springproject.springdatajpa.bootstrap;

import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rodion.springproject.springdatajpa.domain.Author;
import rodion.springproject.springdatajpa.domain.uuid.AuthorUuid;
import rodion.springproject.springdatajpa.domain.Book;
import rodion.springproject.springdatajpa.domain.uuid.BookUuid;
import rodion.springproject.springdatajpa.repository.AuthorRepository;
import rodion.springproject.springdatajpa.repository.AuthorUuidRepository;
import rodion.springproject.springdatajpa.repository.BookRepository;
import rodion.springproject.springdatajpa.repository.BookUuidRepository;

@Component
@Data
@Profile({"local", "default"})
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;

    @Override
    public void run(String... args) throws Exception {
        bookRepository.deleteAll();
        authorUuidRepository.deleteAll();
        authorUuidRepository.deleteAll();
        bookUuidRepository.deleteAll();

        Author author = Author
                .builder()
                .firstName("Sam")
                .lastName("Fischer")
                .build();

        Author savedAuthor = authorRepository.save(author);
        System.out.println("Saved Author UUID: " + savedAuthor.getId());

        Book freedom = Book
                .builder()
                .title("Freedom")
                .publisher("Mark&Walton")
                .isbn("111-222-333-4000")
                .authorId(savedAuthor.getId())
                .build();
        Book world = Book
                .builder()
                .title("World")
                .publisher("Jane&Walton")
                .isbn("555-666-777-8000")
                .authorId(savedAuthor.getId())
                .build();

        bookRepository.save(freedom);
        bookRepository.save(world);

        bookRepository.findAll().forEach(book -> {
            System.out.printf("Book ID: %s%n", book.getId());
            System.out.printf("Book Title: %s%n", book.getTitle());
            System.out.printf("Book Publisher: %s%n", book.getPublisher());
        });


        AuthorUuid authorUuid = AuthorUuid
                .builder()
                .firstName("Sam")
                .lastName("Fischer")
                .build();

        AuthorUuid savedAuthorUuid = authorUuidRepository.save(authorUuid);
        System.out.println("Saved Author UUID: " + savedAuthorUuid.getId());

        BookUuid bookUuid = BookUuid
                .builder()
                .title("All About UUIDs")
                .build();

        BookUuid savedBookUuid = bookUuidRepository.save(bookUuid);
        System.out.println("Saved Book UUID: " + savedBookUuid.getId());

    }
}
