package org.skypro.skyshop.service;

import org.skypro.skyshop.model.SearchResult;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public List<SearchResult> search(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }

        String lowerCaseQuery = query.toLowerCase();

        return storageService.getAllSearchable()
                .stream()
                .filter(searchable ->
                        searchable.getName().toLowerCase().contains(lowerCaseQuery)
                )
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}
