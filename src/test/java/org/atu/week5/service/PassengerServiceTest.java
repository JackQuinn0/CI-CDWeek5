package org.atu.week5.service;

import org.atu.week5.model.Passenger;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PassengerServiceTest {
    private PassengerService service;

    @BeforeEach
    void setUp() {
        service = new PassengerService();
        service.clear();
    }

    @Test
    void create_and_findById_work() {
        Passenger p = Passenger.builder().id(1).name("Alice").email("a@a.com").build();
        service.create(p);

        Optional<Passenger> found = service.findById(1);
        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getName());
    }

    @Test
    void findAll_returns_unmodifiable_snapshot() {
        service.create(Passenger.builder().id(1).name("A").email("a@a.com").build());
        assertEquals(1, service.findAll().size());
    }

    @Test
    void create_throws_on_duplicate_id() {
        service.create(Passenger.builder().id(1).name("A").email("a@a.com").build());
        assertThrows(IllegalArgumentException.class, () ->
                service.create(Passenger.builder().id(1).name("B").email("b@b.com").build()));
    }

    @Test
    void update_updates_existing() {
        service.create(Passenger.builder().id(1).name("A").email("a@a.com").build());
        var updated = service.update(1, "Ann", "ann@a.com");
        assertTrue(updated.isPresent());
        assertEquals("Ann", updated.get().getName());
        assertEquals("ann@a.com", updated.get().getEmail());
    }

    @Test
    void update_returns_empty_if_missing() {
        assertTrue(service.update(99, "X", "x@x.com").isEmpty());
    }

    @Test
    void delete_removes_existing() {
        service.create(Passenger.builder().id(1).name("A").email("a@a.com").build());
        assertTrue(service.delete(1));
        assertTrue(service.findById(1).isEmpty());
    }

    @Test
    void delete_returns_false_if_missing() {
        assertFalse(service.delete(42));
    }
}
