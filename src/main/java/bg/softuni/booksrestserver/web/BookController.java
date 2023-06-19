package bg.softuni.booksrestserver.web;

import bg.softuni.booksrestserver.model.dto.BookDto;
import bg.softuni.booksrestserver.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.
                ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long bookId) {
        Optional<BookDto> theBook = bookService.findBookById(bookId);

        //ResponseEntity.ok(theBook.get()); o
        //return ResponseEntity.notFound().build();

        return theBook.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> deleteBookById(@PathVariable("id") Long bookId) {
        bookService.deleteById(bookId);

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping()
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto newBook, UriComponentsBuilder uriComponentsBuilder) {
        long newBookId = bookService.createBook(newBook);

        return ResponseEntity.created(uriComponentsBuilder
                .path("/api/books/{id}")
                .build(newBookId))
                .build();
    }
}
