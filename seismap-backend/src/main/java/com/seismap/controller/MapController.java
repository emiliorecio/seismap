package com.seismap.controller;

import com.seismap.model.entity.SeismapMap;
import com.seismap.service.MapService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/maps")
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/default")
    public SeismapMap getDefault(@RequestParam(defaultValue = "1") Long userId) {
        return mapService.getDefault(userId);
    }

    @GetMapping("/{id}")
    public SeismapMap get(@PathVariable Long id) {
        return mapService.getById(id);
    }

    @GetMapping
    public List<SeismapMap> listByUser(@RequestParam(defaultValue = "1") Long userId) {
        return mapService.listByUser(userId);
    }

    @PostMapping
    public SeismapMap create(@RequestBody SeismapMap map) {
        return mapService.create(map);
    }

    @PatchMapping("/{id}/name")
    public SeismapMap rename(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return mapService.rename(id, body.get("name"));
    }

    @PutMapping("/{id}")
    public SeismapMap update(@PathVariable Long id, @RequestBody SeismapMap map) {
        return mapService.update(id, map);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        mapService.delete(id);
    }
}
