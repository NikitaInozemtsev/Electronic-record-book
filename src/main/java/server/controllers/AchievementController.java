package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.dto.AchievementDto;
import server.filters.AchievementFilter;
import server.models.Achievement;
import server.services.AchievementService;

import java.util.List;

@Controller
@RequestMapping(value = "api/achievements")
public class AchievementController {
    @Autowired
    private AchievementService service;

    @GetMapping(value = "/list")
    public ResponseEntity<List<Achievement>> list(AchievementFilter filter) {
        return new ResponseEntity<List<Achievement>>(service.findAll(filter), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<HttpStatus> create(@RequestBody AchievementDto achievementDto) {
        boolean result = service.createAchievement(achievementDto);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = "/delete-by-ids")
    public ResponseEntity<Integer> deleteByIds(@RequestBody List<Long> ids) {
        Integer result = service.deleteByIds(ids);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete-by-id/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) {
        boolean isRemoved = service.deleteById(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-by-filter")
    public ResponseEntity<Integer> deleteByFilter(AchievementFilter filter) {
        Integer result = service.deleteByFilter(filter);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
