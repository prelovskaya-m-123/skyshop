package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.Item;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    private final String SEARCH_TERM = "laptop";

    // Сценарий 1: хранилище пустое
    @Test
    void search_whenStorageIsEmpty_shouldReturnEmpty() {
        when(storageService.findAll()).thenReturn(List.of());
        Optional<Item> result = searchService.searchByName(SEARCH_TERM);
        assertFalse(result.isPresent());
        verify(storageService, times(1)).findAll();
    }

    // Сценарий 2: объекты есть, но нет подходящего
    @Test
    void search_whenNoMatchingItem_shouldReturnEmpty() {
        List<Item> items = List.of(
                new Item("1", "phone"),
                new Item("2", "tablet")
        );
        when(storageService.findAll()).thenReturn(items);
        Optional<Item> result = searchService.searchByName(SEARCH_TERM);
        assertFalse(result.isPresent());
        verify(storageService, times(1)).findAll();
    }

    // Сценарий 3: есть подходящий объект
    @Test
    void search_whenMatchingItemExists_shouldReturnItem() {
        Item matchingItem = new Item("3", "gaming laptop");
        List<Item> items = List.of(matchingItem);
        when(storageService.findAll()).thenReturn(items);
        Optional<Item> result = searchService.searchByName(SEARCH_TERM);
        assertTrue(result.isPresent());
        assertEquals(matchingItem, result.get());
        verify(storageService, times(1)).findAll();
    }
}