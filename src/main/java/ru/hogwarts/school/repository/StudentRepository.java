package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAge(Integer Age);

    List<Student> findAllByAgeBetween(int win, int max);
    List<Student> findAllByFaculty_Id(Long facultyId);

}
