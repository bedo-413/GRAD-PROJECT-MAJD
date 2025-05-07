package jordan.university.gradproject2.repository;

import jordan.university.gradproject2.model.ActivityForm;

import java.util.Optional;

public interface ActivityFormRepository {

    ActivityForm findById(String id);

    ActivityForm deleteById(String id);
}