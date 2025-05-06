package jordan.university.gradproject2.model;

import jordan.university.gradproject2.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityForm {
    private String id;
    private Status status;
    private Student student;
    private String supervisorName;
    private String activityType;
    private LocalDate activityDate;
    private String organizingEntity;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String phoneNumber;
    private String description;
}