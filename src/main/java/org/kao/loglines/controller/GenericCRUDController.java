package org.kao.loglines.controller;

import org.kao.loglines.service.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public abstract class GenericCRUDController<T> {

    public abstract GenericService<T> getService();

    @GetMapping("/list")
    public ResponseEntity<List<T>> getAllData() {
        return ResponseEntity.ok(getService().getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(getService().get(id));
    }

}
