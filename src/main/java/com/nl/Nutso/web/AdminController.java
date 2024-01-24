package com.nl.Nutso.web;

import com.nl.Nutso.model.dto.BookSummaryDTO;
import com.nl.Nutso.model.dto.SearchBookDTO;
import com.nl.Nutso.model.enums.CategoryEnum;
import com.nl.Nutso.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("categories")
    public CategoryEnum[] categories() {
        return CategoryEnum.values();
    }

    @GetMapping("/books-unavailable")
    public String all(Model model,
                      @PageableDefault(
                              size = 10,
                              sort = "title"
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
            Page<BookSummaryDTO> allUnavailableBooks = bookService.getUnavailableBooks(pageable);
            model.addAttribute("books", allUnavailableBooks);
        }
        return "book/books-unavailable";
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
            return "book/books-search";
        }

        if (!model.containsAttribute("searchBookModel")) {
            model.addAttribute("searchBookModel", searchBookDTO);
        }

        if (!searchBookDTO.isEmpty()) {
            model.addAttribute("books", bookService.searchBooks(searchBookDTO, pageable));
        }
        return "book/books-unavailable";
    }

}
