package org.atu.week5.service;

import org.atu.week5.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PassengerService {
    private final List<Passenger> store = new CopyOnWriteArrayList<>();

    public List<Passenger> findAll() {
        return Collections.unmodifiableList(store);
    }

    public Optional<Passenger> findById(long id) {
        return store.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Passenger create(Passenger passenger) {
        findById(passenger.getId()).ifPresent(p -> {
            throw new IllegalArgumentException("Passenger with id " + p.getId() + " already exists");
        });
        store.add(passenger);
        return passenger;
    }

    public Optional<Passenger> update(long id, String name, String email) {
        Optional<Passenger> existing = findById(id);
        if (existing.isEmpty()) return Optional.empty();
        Passenger updated = Passenger.builder()
                .id(id)
                .name(name != null ? name : existing.get().getName())
                .email(email != null ? email : existing.get().getEmail())
                .build();
        store.remove(existing.get());
        store.add(updated);
        return Optional.of(updated);
    }

    public boolean delete(long id) {
        return findById(id).map(store::remove).orElse(false);
    }

    public void clear() {
        store.clear();
    }
}