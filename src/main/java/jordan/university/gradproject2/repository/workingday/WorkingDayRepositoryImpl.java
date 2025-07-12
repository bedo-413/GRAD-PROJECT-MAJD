package jordan.university.gradproject2.repository.workingday;

import jordan.university.gradproject2.mapper.WorkingDayMapper;
import jordan.university.gradproject2.model.WorkingDay;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public class WorkingDayRepositoryImpl implements WorkingDayRepository {

    private final WorkingDayJpaRepository workingDayJpaRepository;
    private final WorkingDayMapper workingDayMapper;

    public WorkingDayRepositoryImpl(WorkingDayJpaRepository workingDayJpaRepository, WorkingDayMapper workingDayMapper) {
        this.workingDayJpaRepository = workingDayJpaRepository;
        this.workingDayMapper = workingDayMapper;
    }

    @Override
    public Optional<WorkingDay> findByDayOfWeek(DayOfWeek dayOfWeek) {
        return workingDayJpaRepository.findByDayOfWeek(dayOfWeek)
                .map(workingDayMapper::toModel);
    }


    @Override
    public List<WorkingDay> findByIsWorkingDay(Boolean isWorkingDay) {
        return workingDayMapper.toModels(workingDayJpaRepository.findByIsWorkingDay(isWorkingDay));
    }

    @Override
    public List<WorkingDay> searchHolidaysByName(String keyword) {
        return workingDayMapper.toModels(workingDayJpaRepository.searchHolidaysByName(keyword));
    }

    @Override
    public WorkingDay save(WorkingDay workingDay) {
        return workingDayMapper.toModel(
                workingDayJpaRepository.save(
                        workingDayMapper.toEntity(workingDay)
                )
        );
    }

    @Override
    public List<WorkingDay> findAll() {
        return workingDayMapper.toModels(workingDayJpaRepository.findAll());
    }

    @Override
    public Optional<WorkingDay> findById(Long id) {
        return workingDayJpaRepository.findById(id)
                .map(workingDayMapper::toModel);
    }

    @Override
    public void deleteById(Long id) {
        workingDayJpaRepository.deleteById(id);
    }
}
