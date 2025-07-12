package jordan.university.gradproject2.service;

import jordan.university.gradproject2.mapper.WorkingDayMapper;
import jordan.university.gradproject2.model.WorkingDay;
import jordan.university.gradproject2.repository.workingday.WorkingDayRepository;
import jordan.university.gradproject2.resource.WorkingDayResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkingDayServiceTest {

    @Mock
    private WorkingDayRepository workingDayRepository;

    @Mock
    private WorkingDayMapper workingDayMapper;

    @InjectMocks
    private WorkingDayService workingDayService;

    private WorkingDay mondayWorkingDay;
    private WorkingDay sundayHoliday;
    private WorkingDayResource mondayWorkingDayResource;
    private WorkingDayResource sundayHolidayResource;

    @BeforeEach
    void setUp() {
        // Setup test data
        mondayWorkingDay = new WorkingDay();
        mondayWorkingDay.setId(1L);
        mondayWorkingDay.setDayOfWeek(DayOfWeek.MONDAY);
        mondayWorkingDay.setIsWorkingDay(true);
        mondayWorkingDay.setDescription("Regular working day");

        sundayHoliday = new WorkingDay();
        sundayHoliday.setId(2L);
        sundayHoliday.setDayOfWeek(DayOfWeek.SUNDAY);
        sundayHoliday.setIsWorkingDay(false);
        sundayHoliday.setDescription("Weekend");
        sundayHoliday.setHolidayName("Weekend");

        mondayWorkingDayResource = new WorkingDayResource();
        mondayWorkingDayResource.setId(1L);
        mondayWorkingDayResource.setDayOfWeek(DayOfWeek.MONDAY);
        mondayWorkingDayResource.setIsWorkingDay(true);
        mondayWorkingDayResource.setDescription("Regular working day");

        sundayHolidayResource = new WorkingDayResource();
        sundayHolidayResource.setId(2L);
        sundayHolidayResource.setDayOfWeek(DayOfWeek.SUNDAY);
        sundayHolidayResource.setIsWorkingDay(false);
        sundayHolidayResource.setDescription("Weekend");
        sundayHolidayResource.setHolidayName("Weekend");
    }

    @Test
    void getAllWorkingDays() {
        // Arrange
        List<WorkingDay> workingDays = Arrays.asList(mondayWorkingDay, sundayHoliday);
        List<WorkingDayResource> expectedResources = Arrays.asList(mondayWorkingDayResource, sundayHolidayResource);

        when(workingDayRepository.findAll()).thenReturn(workingDays);
        when(workingDayMapper.toResources(workingDays)).thenReturn(expectedResources);

        // Act
        List<WorkingDayResource> result = workingDayService.getAllWorkingDays();

        // Assert
        assertEquals(expectedResources, result);
        verify(workingDayRepository).findAll();
        verify(workingDayMapper).toResources(workingDays);
    }

    @Test
    void getWorkingDayById() {
        // Arrange
        when(workingDayRepository.findById(1L)).thenReturn(Optional.of(mondayWorkingDay));
        when(workingDayMapper.toResource(mondayWorkingDay)).thenReturn(mondayWorkingDayResource);

        // Act
        WorkingDayResource result = workingDayService.getWorkingDayById(1L);

        // Assert
        assertEquals(mondayWorkingDayResource, result);
        verify(workingDayRepository).findById(1L);
        verify(workingDayMapper).toResource(mondayWorkingDay);
    }

    @Test
    void getWorkingDayByDayOfWeek() {
        // Arrange
        when(workingDayRepository.findByDayOfWeek(DayOfWeek.MONDAY)).thenReturn(Optional.of(mondayWorkingDay));
        when(workingDayMapper.toResource(mondayWorkingDay)).thenReturn(mondayWorkingDayResource);

        // Act
        WorkingDayResource result = workingDayService.getWorkingDayByDayOfWeek(DayOfWeek.MONDAY);

        // Assert
        assertEquals(mondayWorkingDayResource, result);
        verify(workingDayRepository).findByDayOfWeek(DayOfWeek.MONDAY);
        verify(workingDayMapper).toResource(mondayWorkingDay);
    }

    @Test
    void isWorkingDay_returnsTrueForWorkingDay() {
        // Arrange
        LocalDate monday = LocalDate.of(2023, 7, 17); // A Monday
        when(workingDayRepository.findByDayOfWeek(DayOfWeek.MONDAY)).thenReturn(Optional.of(mondayWorkingDay));

        // Act
        boolean result = workingDayService.isWorkingDay(monday);

        // Assert
        assertTrue(result);
        verify(workingDayRepository).findByDayOfWeek(DayOfWeek.MONDAY);
    }

    @Test
    void isWorkingDay_returnsFalseForHoliday() {
        // Arrange
        LocalDate sunday = LocalDate.of(2023, 7, 16); // A Sunday
        when(workingDayRepository.findByDayOfWeek(DayOfWeek.SUNDAY)).thenReturn(Optional.of(sundayHoliday));

        // Act
        boolean result = workingDayService.isWorkingDay(sunday);

        // Assert
        assertFalse(result);
        verify(workingDayRepository).findByDayOfWeek(DayOfWeek.SUNDAY);
    }

    @Test
    void isHoliday_returnsTrueForHoliday() {
        // Arrange
        LocalDate sunday = LocalDate.of(2023, 7, 16); // A Sunday
        when(workingDayRepository.findByDayOfWeek(DayOfWeek.SUNDAY)).thenReturn(Optional.of(sundayHoliday));

        // Act
        boolean result = workingDayService.isHoliday(sunday);

        // Assert
        assertTrue(result);
        verify(workingDayRepository).findByDayOfWeek(DayOfWeek.SUNDAY);
    }

    @Test
    void isHoliday_returnsFalseForWorkingDay() {
        // Arrange
        LocalDate monday = LocalDate.of(2023, 7, 17); // A Monday
        when(workingDayRepository.findByDayOfWeek(DayOfWeek.MONDAY)).thenReturn(Optional.of(mondayWorkingDay));

        // Act
        boolean result = workingDayService.isHoliday(monday);

        // Assert
        assertFalse(result);
        verify(workingDayRepository).findByDayOfWeek(DayOfWeek.MONDAY);
    }

    @Test
    void getHolidayName_returnsNameForHoliday() {
        // Arrange
        LocalDate sunday = LocalDate.of(2023, 7, 16); // A Sunday
        when(workingDayRepository.findByDayOfWeek(DayOfWeek.SUNDAY)).thenReturn(Optional.of(sundayHoliday));

        // Act
        String result = workingDayService.getHolidayName(sunday);

        // Assert
        assertEquals("Weekend", result);
        verify(workingDayRepository).findByDayOfWeek(DayOfWeek.SUNDAY);
    }

    @Test
    void getHolidayName_returnsNullForWorkingDay() {
        // Arrange
        LocalDate monday = LocalDate.of(2023, 7, 17); // A Monday
        when(workingDayRepository.findByDayOfWeek(DayOfWeek.MONDAY)).thenReturn(Optional.of(mondayWorkingDay));

        // Act
        String result = workingDayService.getHolidayName(monday);

        // Assert
        assertNull(result);
        verify(workingDayRepository).findByDayOfWeek(DayOfWeek.MONDAY);
    }
}
