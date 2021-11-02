package org.kao.loglines.controller;

import org.kao.loglines.service.GenericCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class GenericCRUDController<Entity, DtoFull, DtoUpdate> {

    public abstract GenericCRUDService<Entity, DtoFull, DtoUpdate> getService();

    @GetMapping
    public ResponseEntity<List<Entity>> getAllData() {
        return ResponseEntity.ok(getService().getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoFull> getById(@PathVariable(name = "id") @NotNull Long id) {
        return ResponseEntity.ok(getService().get(id));
    }

    @PostMapping
    public ResponseEntity<Entity> save(@Valid @RequestBody DtoUpdate dtoUpdate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(getService().create(dtoUpdate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entity> update(@PathVariable(name = "id") @NotNull Long id,
                                         @Valid @RequestBody DtoUpdate dtoUpdate) {
        return ResponseEntity.ok(getService().update(id, dtoUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") @NotNull Long id) {
        getService().deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
