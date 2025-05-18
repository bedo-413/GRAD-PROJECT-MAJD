package jordan.university.gradproject2.location;

import jordan.university.gradproject2.enums.Faculty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Enumerated(EnumType.STRING)
    @Column(name = "faculty", nullable = false)
    private Faculty faculty;
}
