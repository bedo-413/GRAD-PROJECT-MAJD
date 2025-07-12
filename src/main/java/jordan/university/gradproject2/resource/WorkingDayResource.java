package jordan.university.gradproject2.resource;

import lombok.Data;

import java.time.DayOfWeek;

@Data
public class WorkingDayResource {
    private Long id;
    private DayOfWeek dayOfWeek;
    private String description;
    private Boolean isWorkingDay;
    private String holidayName;
}
