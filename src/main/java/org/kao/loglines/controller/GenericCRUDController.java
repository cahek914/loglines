package org.kao.loglines.controller;

import org.kao.loglines.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class GenericCRUDController<T> {

    public abstract GenericService<T> getService();

    @GetMapping
    public ResponseEntity<List<T>> getAllData() {
        return ResponseEntity.ok(getService().getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable(name = "id") @NotNull Long id) {
        return ResponseEntity.ok(getService().get(id));
    }

    @PostMapping
    public ResponseEntity<T> save(@Valid @RequestBody T entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(getService().create(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable(name = "id") @NotNull Long id,
                                    @Valid @RequestBody T entity) {
        return ResponseEntity.ok(getService().update(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") @NotNull Long id) {
        getService().deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
