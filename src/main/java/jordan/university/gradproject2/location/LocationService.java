package jordan.university.gradproject2.location;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository,
                           LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public List<LocationResponse> findAll() {
        return locationRepository.findAll()
                .stream()
                .map(locationMapper::toLocationResponse)
                .collect(Collectors.toList());
    }

    public LocationResponse findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
        return locationMapper.toLocationResponse(location);
    }

    @Transactional
    public LocationResponse create(LocationRequest locationRequest) {
        Location location = locationMapper.toLocation(locationRequest);
        Location savedLocation = locationRepository.save(location);
        return locationMapper.toLocationResponse(savedLocation);
    }

    @Transactional
    public LocationResponse update(Long id, LocationRequest locationRequest) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));

        existingLocation.setLocationName(locationRequest.getLocationName());
        existingLocation.setFaculty(locationRequest.getFaculty());

        Location updatedLocation = locationRepository.save(existingLocation);
        return locationMapper.toLocationResponse(updatedLocation);
    }

    @Transactional
    public void delete(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
        locationRepository.delete(location);
    }
}
