package com.example.bookstore.order.client;

import com.example.bookstore.order.model.BookSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "catalogClient",
        url = "${clients.catalog.url}"
)
public interface CatalogClient {

    @GetMapping("/api/catalog/books/{bookId}")
    BookSummary getBook(@PathVariable("bookId") Long bookId);
}
