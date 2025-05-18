package jordan.university.gradproject2.location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location toLocation(LocationRequest locationRequest) {
        Location location = new Location();
        location.setLocationName(locationRequest.getLocationName());
        location.setFaculty(locationRequest.getFaculty());
        return location;
    }

    public LocationResponse toLocationResponse(Location location) {
        LocationResponse response = new LocationResponse();
        response.setLocationId(location.getLocationId());
        response.setLocationName(location.getLocationName());
        response.setFaculty(location.getFaculty());
        return response;
    }
}
