package bg.softuni.booksrestserver.service;

import bg.softuni.booksrestserver.model.dto.AuthorDto;
import bg.softuni.booksrestserver.model.dto.BookDto;
import bg.softuni.booksrestserver.model.entity.BookEntity;
import bg.softuni.booksrestserver.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
}
