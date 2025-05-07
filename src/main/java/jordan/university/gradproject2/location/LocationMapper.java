package jordan.university.gradproject2.location;
import com.example.Graduation.Project.college.College;
import com.example.Graduation.Project.college.CollegeRepository;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    private final CollegeRepository collegeRepository;

    public LocationMapper(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    public Location toLocation(LocationRequest locationRequest) {
        Location location = new Location();
        location.setLocationName(locationRequest.getLocationName());

        College college = collegeRepository.findById(locationRequest.getCollegeId())
                .orElseThrow(() -> new RuntimeException("College not found with id: " + locationRequest.getCollegeId()));
        location.setCollege(college);

        return location;
    }

    public LocationResponse toLocationResponse(Location location) {
        LocationResponse response = new LocationResponse();
        response.setLocationId(location.getLocationId());
        response.setLocationName(location.getLocationName());
        response.setCollege(location.getCollege());
        return response;
    }
}