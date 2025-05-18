package jordan.university.gradproject2.location;
import jordan.university.gradproject2.enums.Faculty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequest {
    private String locationName;
    private Faculty faculty;
}
