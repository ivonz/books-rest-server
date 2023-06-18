package bg.softuni.booksrestserver;

import bg.softuni.booksrestserver.model.entity.AuthorEntity;
import bg.softuni.booksrestserver.model.entity.BookEntity;
import bg.softuni.booksrestserver.repository.AuthorRepository;
import bg.softuni.booksrestserver.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class BooksApplicationInit implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    BooksApplicationInit(AuthorRepository authorRepository,
                         BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0 && authorRepository.count() == 0) {
            initJovkov();
            initNikolaiHaitov();
            initVazov();
            initElinPelin();
        }
    }

    private void initJovkov() {
        initAuthor("Iovkov", "Kniga 1");
    }

    private void initNikolaiHaitov() {
        initAuthor("Nikolai Haitov", "Kniga 2");
    }

    private void initVazov() {
        initAuthor("Ivan Vazov", "Kniga 3", "kniga 4");
    }

    private void initElinPelin() {
        initAuthor("Elin Pelin", "Kniga 5", "kniga 6", "kniga 7");
    }

    private void initAuthor(String authorName, String... books) {
        AuthorEntity author = new AuthorEntity();
        author.setName(authorName);

        author = authorRepository.save(author);

        List<BookEntity> allBooks = new ArrayList<>();

        for (String book: books) {
            BookEntity aBook = new BookEntity();
            aBook.setAuthor(author);
            aBook.setTitle(book);
            aBook.setIsbn(UUID.randomUUID().toString());
            allBooks.add(aBook);
            bookRepository.save(aBook);
        }

        author.setBooks(allBooks);
        authorRepository.save(author);
    }
}
