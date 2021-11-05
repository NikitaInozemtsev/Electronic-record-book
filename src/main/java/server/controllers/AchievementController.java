package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import server.filters.AchievementFilter;
import server.models.Achievement;
import server.services.AchievementService;

import java.util.List;

@Controller
@RequestMapping(value = "api/achievement")
public class AchievementController {
    @Autowired
    private AchievementService service;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Achievement>> list (AchievementFilter filter) {
        return new ResponseEntity<List<Achievement>>(service.findAll(filter), HttpStatus.OK);
    }
}
