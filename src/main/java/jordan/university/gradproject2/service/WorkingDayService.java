package jordan.university.gradproject2.service;

import jakarta.transaction.Transactional;
import jordan.university.gradproject2.mapper.WorkingDayMapper;
import jordan.university.gradproject2.model.WorkingDay;
import jordan.university.gradproject2.repository.workingday.WorkingDayRepository;
import jordan.university.gradproject2.resource.WorkingDayResource;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkingDayService {

    private final WorkingDayRepository workingDayRepository;
    private final WorkingDayMapper workingDayMapper;

    public WorkingDayService(WorkingDayRepository workingDayRepository, WorkingDayMapper workingDayMapper) {
        this.workingDayRepository = workingDayRepository;
        this.workingDayMapper = workingDayMapper;
    }

    @Transactional
    public List<WorkingDayResource> getAllWorkingDays() {
        return workingDayMapper.toResources(workingDayRepository.findAll());
    }

    @Transactional
    public WorkingDayResource getWorkingDayById(Long id) {
        return workingDayRepository.findById(id)
                .map(workingDayMapper::toResource)
                .orElseThrow(() -> new RuntimeException("Working day not found with id: " + id));
    }

    @Transactional
    public WorkingDayResource getWorkingDayByDayOfWeek(DayOfWeek dayOfWeek) {
        return workingDayRepository.findByDayOfWeek(dayOfWeek)
                .map(workingDayMapper::toResource)
                .orElseThrow(() -> new RuntimeException("Working day not found for day: " + dayOfWeek));
    }

    @Transactional
    public List<WorkingDayResource> getWorkingDays(Boolean isWorkingDay) {
        return workingDayMapper.toResources(workingDayRepository.findByIsWorkingDay(isWorkingDay));
    }

    @Transactional
    public List<WorkingDayResource> getHolidays(Boolean isHoliday) {
        return workingDayMapper.toResources(workingDayRepository.findByIsWorkingDay(!isHoliday));
    }

    @Transactional
    public List<WorkingDayResource> searchHolidaysByName(String keyword) {
        return workingDayMapper.toResources(workingDayRepository.searchHolidaysByName(keyword));
    }

    @Transactional
    public WorkingDayResource saveWorkingDay(WorkingDay workingDay) {
        return workingDayMapper.toResource(workingDayRepository.save(workingDay));
    }

    @Transactional
    public void deleteWorkingDay(Long id) {
        workingDayRepository.deleteById(id);
    }

    /**
     * Check if a specific date is a working day
     * @param date The date to check
     * @return true if it's a working day, false otherwise
     */
    @Transactional
    public boolean isWorkingDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Optional<WorkingDay> workingDayOpt = workingDayRepository.findByDayOfWeek(dayOfWeek);

        return workingDayOpt.map(workingDay -> Boolean.TRUE.equals(workingDay.getIsWorkingDay()))
                .orElse(true);
    }

    /**
     * Check if a specific date is a holiday
     * @param date The date to check
     * @return true if it's a holiday, false otherwise
     */
    @Transactional
    public boolean isHoliday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return workingDayRepository.findByDayOfWeek(dayOfWeek)
                .map(workingDay -> !Boolean.TRUE.equals(workingDay.getIsWorkingDay()))
                .orElse(false);
    }

    /**
     * Get the holiday name for a specific date if it's a holiday
     * @param date The date to check
     * @return The holiday name or null if it's not a holiday
     */
    @Transactional
    public String getHolidayName(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return workingDayRepository.findByDayOfWeek(dayOfWeek)
                .filter(workingDay -> !Boolean.TRUE.equals(workingDay.getIsWorkingDay()))
                .map(WorkingDay::getHolidayName)
                .orElse(null);
    }
}
