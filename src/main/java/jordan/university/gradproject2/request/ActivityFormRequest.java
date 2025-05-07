package jordan.university.gradproject2.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ActivityFormRequest {
    private Long requesterId;
    private Long typeId;
    private Long supervisorId;
    private Long locationId;
    private String description;
    private String objectives;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}