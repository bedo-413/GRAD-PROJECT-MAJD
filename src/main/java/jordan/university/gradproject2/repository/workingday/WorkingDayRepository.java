package jordan.university.gradproject2.repository.workingday;

import jordan.university.gradproject2.model.WorkingDay;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface WorkingDayRepository {

    Optional<WorkingDay> findByDayOfWeek(DayOfWeek dayOfWeek);

    List<WorkingDay> findByIsWorkingDay(Boolean isWorkingDay);

    List<WorkingDay> searchHolidaysByName(String keyword);

    WorkingDay save(WorkingDay workingDay);

    List<WorkingDay> findAll();

    Optional<WorkingDay> findById(Long id);

    void deleteById(Long id);
}
