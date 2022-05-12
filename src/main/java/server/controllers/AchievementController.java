package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.dto.AchievementDto;
import server.dto.IdDto;
import server.exception.NotFoundResourceException;
import server.filters.AchievementFilter;
import server.models.Achievement;
import server.services.AchievementService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "api/achievements")
@CrossOrigin(origins = {"https://electronic-record-book.herokuapp.com", "http://localhost:3000"})
public class AchievementController {
    @Autowired
    private AchievementService service;

    @GetMapping(value = "/")
    public ResponseEntity<Map<String, Object>> list(AchievementFilter filter,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size,
                                                    //pattern for sort FieldName,FieldName...FieldName:SortingType(asc, desc) example : id,name:asc
                                                    @RequestParam(required = false) String sort) {
        Pageable paging;
        if (sort != null) {
            String[] sorts = sort.split(":");
            String[] sortFields = sorts[0].split(",");
            String sortType = sorts[1];
            paging = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortType), sortFields));
        } else {
            paging = PageRequest.of(page, size);
        }
        Page<Achievement> pageAchievements = service.findAll(filter, paging);

        Map<String, Object> response = new HashMap<>();
        response.put("achievements", pageAchievements.getContent());
        response.put("totalItems", pageAchievements.getTotalElements());
        response.put("totalPages", pageAchievements.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> create(@RequestBody AchievementDto achievementDto) {
        Optional<Exception> result = service.createAchievement(achievementDto);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (result.get().getClass() == NotFoundResourceException.class) {
            return new ResponseEntity<>(result.get().getMessage(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result.get().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = "/delete-by-ids")
    public ResponseEntity<Integer> deleteByIds(@RequestBody List<IdDto> ids) {
        Integer result = service.deleteByIds(ids.stream().map(IdDto::getId).collect(Collectors.toList()));
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
