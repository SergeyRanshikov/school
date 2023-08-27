package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Collection<Student> getByAge(int age) {
        return studentRepository.findAllByAge(age);

    }
    public Collection<Student> getByAgeBetween(int min, int max) {
        return studentRepository.findAllByAgeBetween(min, max);

    }
    public Collection<Student> getByFacultyId(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .map(Faculty::getStudent)
                .orElseThrow(NotFoundException::new);

    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student update(Long id, Student student) {

        Student existingStudent = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        existingStudent.setAge(student.getAge());
        existingStudent.setName(student.getName());
        return studentRepository.save(existingStudent);
    }


    public Student remove(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        studentRepository.delete(student);
        return student;
    }

}
