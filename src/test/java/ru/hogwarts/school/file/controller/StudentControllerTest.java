package ru.hogwarts.school.file.controller;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.SchoolApplication;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = SchoolApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @Autowired
    TestRestTemplate template;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;

    @AfterEach
    void clearDb() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }
    private ResponseEntity<Student> createStudent(String name, int age) {
        ResponseEntity<Student> response = template.postForEntity("/student",
                new Student(null, name, age),
                Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        return response;
    }
    @Test
    void getAll() {
        createStudent("ivan", 20);
        createStudent("mari", 21);

        ResponseEntity<Collection> response = template
                .getForEntity("/student" , Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
    }
    @Test
    void create() {
        String name = "mari";
        int age = 20;
        ResponseEntity<Student> response = createStudent(name, age);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("mari");
        assertThat(response.getBody().getAge()).isEqualTo(20);

    }
    @Test
    void getById() {
        ResponseEntity<Student> response = createStudent("mari", 20);
        Long studentId = response.getBody().getId();

        response = template.getForEntity("/student/" + studentId, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("mari");
        assertThat(response.getBody().getAge()).isEqualTo(20);
    }
    @Test
    void update() {
        ResponseEntity<Student> response = createStudent("mari", 21);
        Long studentId = response.getBody().getId();

        template.put("/student/"+studentId, new Student(null, "ivan", 20));

        response = template.getForEntity("/student/" + studentId, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAge()).isEqualTo(20);
        assertThat(response.getBody().getName()).isEqualTo("ivan");
    }
    @Test
    void delete() {
        ResponseEntity<Student> response = createStudent("mari", 20);
        Long studentId = response.getBody().getId();


        template.delete("/student/" + studentId);

        response = template.getForEntity("/student/" + studentId, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    void filteredByAge() {
        int age =20;
        createStudent("mari", 20);
        createStudent("boris", 22);


        ResponseEntity<Collection> response = template
                .getForEntity("/student/by-age?min=20&max=21", Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);

    }
    @Test
    void byFaculty() {
        createStudent("mari", 20);
        createStudent("boris", 22);

        ResponseEntity<Student> response = createStudent("Maria", 21 );
        Student student = response.getBody();

        Faculty faculty = new Faculty(null, "math", "red");
        faculty.setStudent(List.of(student));


        ResponseEntity<Faculty> facultyResponseEntity = template
                .postForEntity("/faculty/", faculty, Faculty.class);
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long facultyId = facultyResponseEntity.getBody().getId();

        ResponseEntity<Student[]> responses = template
                .getForEntity("/student/by-faculty?facultyId=" + facultyId, Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(student);
    }




}
