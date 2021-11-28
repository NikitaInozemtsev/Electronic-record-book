package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.exception.NotFoundResourceException;
import server.models.Department;
import server.services.DepartmentService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/${version.api}/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService service;

    @GetMapping(value = "/")
    public ResponseEntity<List<Department>> list() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Department> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.findOrThrow(id), HttpStatus.OK);
        }
        catch (NotFoundResourceException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/")
    public ResponseEntity<String> create(@RequestBody Department department) {
        Optional<Exception> result = service.createDepartment(department);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (result.get().getClass() == NotFoundResourceException.class) {
            return new ResponseEntity<>(result.get().getMessage(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result.get().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        Optional<Exception> isRemoved = service.deleteById(id);
        if (isRemoved.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (isRemoved.get().getClass() == EmptyResultDataAccessException.class) {
            return new ResponseEntity<>(isRemoved.get().getMessage(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(isRemoved.get().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/")
    public ResponseEntity<String> update(@RequestBody Department department) {
        Optional<Exception> result = service.update(department);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else if (result.get().getClass() == NotFoundResourceException.class) {
            return new ResponseEntity<>(result.get().getMessage(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result.get().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
