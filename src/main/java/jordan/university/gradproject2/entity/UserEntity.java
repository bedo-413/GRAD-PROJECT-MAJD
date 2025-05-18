package jordan.university.gradproject2.entity;

import jakarta.persistence.*;
import jordan.university.gradproject2.entity.base.BaseEntity;
import jordan.university.gradproject2.enums.Faculty;
import jordan.university.gradproject2.enums.Occupation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(name = "UNIVERSITY_ID", unique = true, nullable = false)
    private String universityId;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD", unique = true, nullable = false)
    private String password;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "FACULTY")
    private Faculty faculty;

    @Enumerated(EnumType.STRING)
    @Column(name = "OCCUPATION", nullable = false)
    private Occupation occupation;
}
