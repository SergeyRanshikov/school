package ru.hogwarts.school.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByColor(String Color);

    List<Faculty> findAllByColorIgnoreCaseOrNameIgnoreCase(String color, String name);


    Optional<Faculty> findByStudent_Id(Long studentId);
}
