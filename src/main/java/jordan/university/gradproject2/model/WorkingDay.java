package jordan.university.gradproject2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDay {
    private Long id;
    private DayOfWeek dayOfWeek;
    private String description;
    private Boolean isWorkingDay;
    private String holidayName;
}
