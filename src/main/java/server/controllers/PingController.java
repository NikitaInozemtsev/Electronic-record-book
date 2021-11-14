package server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PingController {
    public final static String OK_MESSAGE = "0;OK";

    @GetMapping(value = "/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>(OK_MESSAGE, HttpStatus.OK);
    }
}
