package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty getById(Long id) {
        return facultyRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getByColor(String color) {
        return facultyRepository.findAllByColor(color);
    }

    public Collection<Faculty> getByColorOrName(String color, String name) {
        return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty update(Long id, Faculty faculty) {

        Faculty existingFaculty = facultyRepository.findById(id).orElseThrow(NotFoundException::new);
        if (faculty.getColor() != null) {
            existingFaculty.setColor(faculty.getColor());
        }
        if (faculty.getName() != null) {
            existingFaculty.setName(faculty.getName());
        }
            return facultyRepository.save(existingFaculty);
    }
    public Faculty remove(Long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        facultyRepository.delete(faculty);
        return faculty;
    }

    public Faculty getByStudentId(Long studentId) {
        return facultyRepository.findByStudent_Id(studentId).orElseThrow(NotFoundException::new);

    }


}


