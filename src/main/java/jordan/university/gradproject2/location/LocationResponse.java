package jordan.university.gradproject2.location;
import jordan.university.gradproject2.enums.Faculty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse {
    private Long locationId;
    private String locationName;
    private Faculty faculty;
}
