package jordan.university.gradproject2.entity;

import jakarta.persistence.*;
import jordan.university.gradproject2.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Entity
@Table(name = "WORKING_DAYS")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class WorkingDayEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "DAY_OF_WEEK", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IS_WORKING_DAY", nullable = false)
    private Boolean isWorkingDay;

    @Column(name = "HOLIDAY_NAME")
    private String holidayName;
}
