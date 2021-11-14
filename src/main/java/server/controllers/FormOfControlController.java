package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import server.exception.NotFoundResourceException;
import server.models.FormOfControl;
import server.services.FormOfControlService;

import java.util.List;

@Controller
@RequestMapping(value = "api/form-of-controls")
public class FormOfControlController {
    @Autowired
    private FormOfControlService service;

    @GetMapping(value = "/")
    public ResponseEntity<List<FormOfControl>> list() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FormOfControl> findById(@PathVariable Long id) {
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
}
