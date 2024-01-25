package com.nl.Nutso.web;

import com.nl.Nutso.model.dto.AddBookDTO;
import com.nl.Nutso.model.dto.BookDetailDTO;
import com.nl.Nutso.model.dto.SearchBookDTO;
import com.nl.Nutso.model.dto.BookSummaryDTO;
import com.nl.Nutso.model.enums.CategoryEnum;
import com.nl.Nutso.service.BookService;
import com.nl.Nutso.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("categories")
    public CategoryEnum[] categories() {
        return CategoryEnum.values();
    }

    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("addBookDTO")) {
            model.addAttribute("addBookDTO", AddBookDTO.empty());
        }

        return "book/book-add";
    }

    @PostMapping("/add")
    public String addNewBook(
            @Valid AddBookDTO addBookDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        //TODO check when trying to add with error
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addBookDTO", addBookDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addBookDTO", bindingResult);
            return "redirect:/books/add";
        }

        UUID newBookUUID = bookService.addBook(addBookDTO);

        return "redirect:/books/" + newBookUUID;
    }

    @GetMapping("/{uuid}")
    public String getBookDetails(@PathVariable("uuid") UUID uuid,
                          Model model) {

        BookDetailDTO bookDetailDTO = bookService.getBookDetail(uuid)
                .orElseThrow(() -> new ObjectNotFoundException("Book with id " + uuid + " not found!"));

        List<BookDetailDTO> relatedBooks = bookService.getRelatedBooksByCategory(bookDetailDTO);
        model.addAttribute("book", bookDetailDTO);
        model.addAttribute("relatedBooks", relatedBooks);

        return "book/book-details";
    }

    @GetMapping("{uuid}/edit")
    public String editBook(@PathVariable("uuid") UUID uuid,
                           Model model) {
        BookDetailDTO bookDetailDTO = bookService.getBookDetail(uuid).
                orElseThrow(() -> new ObjectNotFoundException("Book with id "+ uuid + "not found"));

        System.out.println(bookDetailDTO.price());

        model.addAttribute("book", bookDetailDTO);

        return "book/book-edit";
    }

    @PatchMapping("/{uuid}/edit")
    public String updateBook(@PathVariable("uuid") UUID uuid,
                             @ModelAttribute("book") BookDetailDTO bookDetailDTO,
                             BindingResult bindingResult) {


        // Update the book using the service method
        bookService.updateBook(uuid, bookDetailDTO);
        return "redirect:/books/" + uuid;
    }

    @PostMapping("/{uuid}")
    public String handleBookAction(@PathVariable("uuid") UUID uuid, @RequestParam("action") String action) {
        if ("Деактивирай".equals(action)) {
            bookService.deactivateBook(uuid);
        } else if ("Активирай".equals(action)) {
            bookService.activateBook(uuid);
        }
        return "redirect:/books/all";
    }


    @GetMapping("/all")
    public String showAllBooks(Model model,
                               @PageableDefault(
                              size = 10,
                              sort = "price",
                              direction = Sort.Direction.DESC
                               ) Pageable pageable,
                               @ModelAttribute("searchBookModel") SearchBookDTO searchBookDTO,
                               @RequestParam(name = "category", required = false) CategoryEnum category) {

        if (searchBookDTO != null && !searchBookDTO.isEmpty()) {
            Page<BookSummaryDTO> searchResults = bookService.searchBooks(searchBookDTO, pageable);
            model.addAttribute("books", searchResults);

        } else if (category != null) {
            Page<BookSummaryDTO> booksPage = bookService.getBooksByCategory(category, pageable);
            model.addAttribute("books", booksPage);
            model.addAttribute("categories", CategoryEnum.values());
        } else {
            Page<BookSummaryDTO> allAvailableBooks = bookService.getAvailableBooks(pageable);
            model.addAttribute("books", allAvailableBooks);
        }

        return "book/books";
    }



    @GetMapping("/search")
    public String searchQuery(@Valid SearchBookDTO searchBookDTO,
                              BindingResult bindingResult,
                              Model model,
                              @PageableDefault(
                                      size = 10,
                                      sort = "title"
                              ) Pageable pageable) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("searchBookModel", searchBookDTO);
            model.addAttribute(
                    "org.springframework.validation.BindingResult.searchBookModel",
                    bindingResult);
            return "books-search";
        }

        if (!model.containsAttribute("searchBookModel")) {
            model.addAttribute("searchBookModel", searchBookDTO);
        }

        if (!searchBookDTO.isEmpty()) {
            model.addAttribute("books", bookService.searchBooks(searchBookDTO, pageable));
        }
        return "book/books";
    }


}
