package bg.softuni.booksrestserver.service;

import bg.softuni.booksrestserver.model.dto.AuthorDto;
import bg.softuni.booksrestserver.model.dto.BookDto;
import bg.softuni.booksrestserver.model.entity.AuthorEntity;
import bg.softuni.booksrestserver.model.entity.BookEntity;
import bg.softuni.booksrestserver.repository.AuthorRepository;
import bg.softuni.booksrestserver.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookDto> getAllBooks() {
        return this.bookRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    private BookDto map(BookEntity bookEntity) {

        AuthorDto author = new AuthorDto().setName(bookEntity.getAuthorEntity().getName());

        return new BookDto()
                .setId(bookEntity.getId())
                .setAuthor(author)
                .setIsbn(bookEntity.getIsbn())
                .setTitle(bookEntity.getTitle());
    }

    public Optional<BookDto> findBookById(Long bookId) {
        return this.bookRepository
                .findById(bookId)
                .map(this::map);
    }

    public void deleteById(Long bookId) {
        this.bookRepository.deleteById(bookId);
    }

    private AuthorEntity createNewAuthor(String authorName) {
        return authorRepository.save(new AuthorEntity().setName(authorName));
    }

    public long createBook(BookDto newBook) {
        String authorName = newBook.getAuthor().getName();
        Optional<AuthorEntity> authorOpt = this.authorRepository.findAuthorEntityByName(authorName);

        BookEntity newBookEntity = new BookEntity()
                .setTitle(newBook.getTitle())
                .setIsbn(newBook.getIsbn())
                .setAuthor(authorOpt.orElseGet(() -> createNewAuthor(authorName)));

        return bookRepository.save(newBookEntity).getId();
    }
}
