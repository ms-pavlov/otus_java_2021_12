package ru.otus.controllers;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import ru.otus.controllers.rest.ContactsController;
import ru.otus.dto.requests.ContactsRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class ContactsControllerTest {
    private static final Logger log = LoggerFactory.getLogger(ContactsControllerTest.class);

    private final static int COUNT = 20;
    private final static String NAME = "name";
    private final static String PHONE = "phone";

    private final static ContactsRequest REQUEST = ContactsRequest.builder()
            .name(NAME)
            .phone(PHONE)
            .placementId(1L)
            .active(true)
            .build();
    private final static ContactsRequest EMPTY_REQUEST = ContactsRequest.builder().name("test").placementId(1L).build();

    @Autowired
    private ContactsController controller;

    @Test
    void findAll() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        var size = Optional.ofNullable(controller.findAll().block()).map(List::size).orElse(0);

        for (var i = 0; i < COUNT; i++) {
            var result = controller.create(REQUEST).block();
            assertNotNull(result);
            assertNotNull(result.getId());
        }

        var list = controller.findAll().block();

        assertNotNull(list);
        assertEquals(COUNT + size, list.size());
    }

    @Test
    void findPageable() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        var size = Optional.ofNullable(controller.findAll().block()).map(List::size).orElse(0);

        for (var i = 0; i < COUNT; i++) {
            var result = controller.create(REQUEST).block();
            assertNotNull(result);
            assertNotNull(result.getId());
        }

        var page = controller.findPageable(0, 5).block();

        assertNotNull(page);
        assertEquals(COUNT + size, page.getTotalElements());
        assertEquals(5, page.getContent().size());

        page.getContent().forEach(buildingsResponse -> {
            assertNotNull(buildingsResponse);
            assertNotNull(buildingsResponse.getId());
        });

    }

    @Test
    void createAndFind() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        var result = controller.create(REQUEST).block();

        assertNotNull(result);
        assertNotNull(result.getId());

        var found = controller.findOne(result.getId()).block();
        assertNotNull(found);
        assertEquals(REQUEST.getName(), found.getName());
        assertEquals(REQUEST.getPhone(), found.getPhone());
        assertTrue(found.isActive());
    }

    @Test
    void update() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        var create = controller.create(EMPTY_REQUEST).block();

        assertNotNull(create);
        assertNotNull(create.getId());

        var update = controller.update(create.getId(), REQUEST).block();

        assertNotNull(update);
        var found = controller.findOne(update.getId()).block();
        assertNotNull(found);
        assertEquals(REQUEST.getName(), found.getName());
        assertEquals(REQUEST.getPhone(), found.getPhone());
        assertTrue(found.isActive());
    }

    @Test
    void delete() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        var result = controller.create(REQUEST).block();

        assertNotNull(result);
        assertNotNull(result.getId());
        assertTrue(result.isActive());

        var delete = controller.delete(result.getId()).block();

        log.info("delete {}", delete);

        var found = controller.findOne(result.getId()).block();
        assertNotNull(found);
        assertEquals(REQUEST.getName(), found.getName());
        assertEquals(REQUEST.getPhone(), found.getPhone());
        assertFalse(found.isActive());
    }
}