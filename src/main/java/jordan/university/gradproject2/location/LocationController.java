package jordan.university.gradproject2.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<LocationResponse> findAll() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> findById(@PathVariable Long id) {
        LocationResponse response = locationService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<LocationResponse> create(@RequestBody LocationRequest request) {
        LocationResponse response = locationService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> update(
            @PathVariable Long id,
            @RequestBody LocationRequest request
    ) {
        LocationResponse response = locationService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
