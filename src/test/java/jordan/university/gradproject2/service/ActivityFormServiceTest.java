package jordan.university.gradproject2.service;

import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.entity.UserEntity;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.activitylog.ActivityFormLogRepository;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import jordan.university.gradproject2.resource.ActivityFormResource;
import jordan.university.gradproject2.service.logger.ActivityFormLoggerService;
import jordan.university.gradproject2.taskcatalog.TaskCatalog;
import jordan.university.gradproject2.validation.ValidationError;
import jordan.university.gradproject2.validation.ValidationModel;
import jordan.university.gradproject2.validation.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityFormServiceTest {

    @Mock
    private ActivityFormRepository activityFormRepository;

    @Mock
    private UserJpaRepository userRepository;

    @Mock
    private ActivityFormMapper activityFormMapper;

    @Mock
    private ActivityFormJpaRepository activityFormJpaRepository;

    @Mock
    private TaskCatalog taskCatalog;

    @Mock
    private ActivityFormLogRepository formLogRepository;

    @Mock
    private ActivityFormLoggerService loggerService;

    @Mock
    private EmailNotificationService emailNotificationService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ValidationService validationService;

    @Mock
    private ValidationModel activityFormCreationValidation;

    @InjectMocks
    private ActivityFormService activityFormService;

    private ActivityForm validActivityForm;
    private ActivityFormEntity validActivityFormEntity;
    private ActivityFormResource validActivityFormResource;
    private UserEntity studentEntity;
    private UserEntity supervisorEntity;
    private User student;
    private User supervisor;

    @BeforeEach
    void setUp() {
        // Setup test data
        studentEntity = new UserEntity();
        studentEntity.setId(1L);
        studentEntity.setEmail("student@example.com");

        supervisorEntity = new UserEntity();
        supervisorEntity.setId(2L);
        supervisorEntity.setEmail("supervisor@example.com");

        student = new User();
        student.setId(1L);
        student.setEmail("student@example.com");

        supervisor = new User();
        supervisor.setId(2L);
        supervisor.setEmail("supervisor@example.com");

        validActivityForm = new ActivityForm();
        validActivityForm.setStudent(student);
        validActivityForm.setSupervisor(supervisor);
        validActivityForm.setActivityType("Conference");
        validActivityForm.setActivityDate(LocalDate.now().plusWeeks(3)); // Valid date (3 weeks in future)
        validActivityForm.setOrganizingEntity("University");
        validActivityForm.setLocation("Main Hall");
        validActivityForm.setStartTime(LocalDateTime.now().plusWeeks(3));
        validActivityForm.setEndTime(LocalDateTime.now().plusWeeks(3).plusHours(2));
        validActivityForm.setPhoneNumber("1234567890");
        validActivityForm.setDescription("Annual conference");

        validActivityFormEntity = new ActivityFormEntity();
        validActivityFormEntity.setStudent(studentEntity);
        validActivityFormEntity.setSupervisor(supervisorEntity);
        validActivityFormEntity.setActivityType("Conference");
        validActivityFormEntity.setActivityDate(LocalDate.now().plusWeeks(3)); // Valid date (3 weeks in future)
        validActivityFormEntity.setOrganizingEntity("University");
        validActivityFormEntity.setLocation("Main Hall");
        validActivityFormEntity.setStartTime(LocalDateTime.now().plusWeeks(3));
        validActivityFormEntity.setEndTime(LocalDateTime.now().plusWeeks(3).plusHours(2));
        validActivityFormEntity.setPhoneNumber("1234567890");
        validActivityFormEntity.setDescription("Annual conference");

        validActivityFormResource = new ActivityFormResource();
        validActivityFormResource.setId(1L);
        validActivityFormResource.setActivityType("Conference");
        validActivityFormResource.setActivityDate(LocalDate.now().plusWeeks(3));
    }

    @Test
    void create_ValidForm_ReturnsResource() {
        // Arrange
        when(userRepository.getReferenceById(1L)).thenReturn(studentEntity);
        when(userRepository.getReferenceById(2L)).thenReturn(supervisorEntity);
        when(activityFormMapper.toEntity(any(ActivityForm.class))).thenReturn(validActivityFormEntity);
        when(activityFormMapper.toResource(any(ActivityFormEntity.class))).thenReturn(validActivityFormResource);
        when(userMapper.toModel(studentEntity)).thenReturn(student);
        when(validationService.validate(any(ActivityForm.class), any(ValidationModel.class))).thenReturn(new ArrayList<>());

        // Act
        ActivityFormResource result = activityFormService.create(validActivityForm);

        // Assert
        assertNotNull(result);
        assertEquals(validActivityFormResource, result);
        verify(validationService).validate(any(ActivityForm.class), eq(activityFormCreationValidation));
        verify(activityFormJpaRepository).save(any(ActivityFormEntity.class));
        verify(loggerService).log(any(ActivityFormEntity.class), isNull(), any(), eq(WorkflowAction.CREATE));
    }

    @Test
    void create_InvalidForm_ThrowsException() {
        // Arrange
        // Setup validation to fail
        List<ValidationError> errors = new ArrayList<>();
        errors.add(ValidationError.of("activityDate", "Activity date must be at least 2 weeks from now"));
        when(validationService.validate(any(ActivityForm.class), any(ValidationModel.class))).thenReturn(errors);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            activityFormService.create(validActivityForm);
        });

        assertTrue(exception.getMessage().contains("Validation failed"));
        assertTrue(exception.getMessage().contains("activityDate"));
        verify(validationService).validate(any(ActivityForm.class), eq(activityFormCreationValidation));
        verify(activityFormJpaRepository, never()).save(any(ActivityFormEntity.class));
    }
}
