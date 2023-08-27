package ru.hogwarts.school.file.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudentRepository studentRepository;

    @MockBean
    FacultyRepository facultyRepository;

    @SpyBean
    StudentService studentService;

    @Test
    void getById() throws Exception {
        Student student = new Student(1L, "boris", 20);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("boris"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void create() throws Exception {
        Student student = new Student(1L, "boris", 20);
        when(studentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("boris"))
                .andExpect(jsonPath("$.age").value(20));


    }

    @Test
    void update() throws Exception {
        Student student = new Student(1L, "boris", 20);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.put("/student/1")
                        .content(objectMapper.writeValueAsString(student))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("boris"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void delete() throws Exception {
        Student student = new Student(1L, "boris", 20);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(studentRepository.delete(ArgumentMatchers.any(Student.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/1")
                        .content(objectMapper.writeValueAsString(student))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("boris"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void filteredByAge() throws Exception {
        Student student = new Student(1L, "boris", 20);
        when(studentRepository.findAllByAgeBetween(10, 21))
                .thenReturn(Arrays.asList(
                        new Student(1L, "boris", 20),
                        new Student(2L, "ivan", 18)
                ));


        mockMvc.perform(MockMvcRequestBuilders.get("/student/by-age?min=10&max=21")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("boris"))
                .andExpect(jsonPath("$[1].name").value("ivan"))
        ;
    }

    @Test
    void findByFaculty() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1L, "boris", 20),
                new Student(2L, "ivan", 18));
        Faculty faculty = new Faculty(1L, "math", "red");
        faculty.setStudent(students);

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/by-faculty?facultyId=1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("boris"))
                .andExpect(jsonPath("$[1].name").value("ivan"))
        ;
    }

}
