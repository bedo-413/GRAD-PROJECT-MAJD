package jordan.university.gradproject2.controller;

import jordan.university.gradproject2.model.WorkingDay;
import jordan.university.gradproject2.resource.WorkingDayResource;
import jordan.university.gradproject2.service.WorkingDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static jordan.university.gradproject2.controller.WorkingDayController.WORKING_DAYS_URL;

@RestController
@RequestMapping(WORKING_DAYS_URL)
@Slf4j
public class WorkingDayController {
    protected final static String WORKING_DAYS_URL = "/api/working-days";
    private final WorkingDayService workingDayService;

    public WorkingDayController(WorkingDayService workingDayService) {
        this.workingDayService = workingDayService;
    }

    @GetMapping
    public List<WorkingDayResource> getAllWorkingDays() {
        return workingDayService.getAllWorkingDays();
    }

    @GetMapping("/{id}")
    public WorkingDayResource getWorkingDayById(@PathVariable Long id) {
        return workingDayService.getWorkingDayById(id);
    }

    @GetMapping("/day/{dayOfWeek}")
    public WorkingDayResource getWorkingDayByDayOfWeek(@PathVariable DayOfWeek dayOfWeek) {
        return workingDayService.getWorkingDayByDayOfWeek(dayOfWeek);
    }

    @GetMapping("/working")
    public List<WorkingDayResource> getWorkingDays(@RequestParam(defaultValue = "true") Boolean isWorkingDay) {
        return workingDayService.getWorkingDays(isWorkingDay);
    }

    @GetMapping("/holidays")
    public List<WorkingDayResource> getHolidays(@RequestParam(defaultValue = "true") Boolean isHoliday) {
        return workingDayService.getHolidays(isHoliday);
    }

    @GetMapping("/holidays/search")
    public List<WorkingDayResource> searchHolidaysByName(@RequestParam String keyword) {
        return workingDayService.searchHolidaysByName(keyword);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkingDayResource createWorkingDay(@RequestBody WorkingDay workingDay) {
        return workingDayService.saveWorkingDay(workingDay);
    }

    @PutMapping("/{id}")
    public WorkingDayResource updateWorkingDay(@PathVariable Long id, @RequestBody WorkingDay workingDay) {
        workingDay.setId(id);
        return workingDayService.saveWorkingDay(workingDay);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkingDay(@PathVariable Long id) {
        workingDayService.deleteWorkingDay(id);
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        boolean isWorkingDay = workingDayService.isWorkingDay(date);
        boolean isHoliday = workingDayService.isHoliday(date);
        String holidayName = workingDayService.getHolidayName(date);
        
        return ResponseEntity.ok(Map.of(
                "date", date.toString(),
                "dayOfWeek", date.getDayOfWeek().toString(),
                "isWorkingDay", isWorkingDay,
                "isHoliday", isHoliday,
                "holidayName", holidayName != null ? holidayName : ""
        ));
    }
}