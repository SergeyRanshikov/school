package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
        return studentRepository.findAllByFaculty_Id(facultyId);

    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student update(Long id, Student student) {

        Student existingStudent = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        Optional.ofNullable(student.getName()).ifPresent(existingStudent::setName);
        Optional.ofNullable(student.getAge()).ifPresent(existingStudent::setAge);
        return studentRepository.save(existingStudent);
    }


    public Student remove(Long id) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        studentRepository.delete(existingStudent);
        return existingStudent;
    }
    public long count() {
        return studentRepository.countStudents();
    }

    public double average() {
        return studentRepository.averageAge();
    }

    public List<Student> getLastStudents(int quantity) {
        return studentRepository.findLastStudents(quantity);
    }
}
