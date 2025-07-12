package jordan.university.gradproject2.repository.workingday;

import jordan.university.gradproject2.entity.WorkingDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface WorkingDayJpaRepository extends JpaRepository<WorkingDayEntity, Long>, JpaSpecificationExecutor<WorkingDayEntity> {

    Optional<WorkingDayEntity> findByDayOfWeek(DayOfWeek dayOfWeek);

    List<WorkingDayEntity> findByIsWorkingDay(Boolean isWorkingDay);

    @Query("SELECT w FROM WorkingDayEntity w WHERE w.isWorkingDay = false AND LOWER(w.holidayName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<WorkingDayEntity> searchHolidaysByName(@Param("keyword") String keyword);
}
