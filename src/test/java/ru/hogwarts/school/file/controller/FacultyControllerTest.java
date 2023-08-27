package ru.hogwarts.school.file.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import ru.hogwarts.school.service.FacultyService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudentRepository studentRepository;

    @MockBean
    FacultyRepository facultyRepository;

    @SpyBean
    FacultyService facultyService;

    @Test
    void getById() throws Exception {
        Faculty faculty = new Faculty(1L, "math", "red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("math"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void create() throws Exception {
        Faculty faculty = new Faculty(1L, "math", "red");
        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("math"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void update() throws Exception {
        Faculty faculty = new Faculty(1L, "math", "red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/1")
                        .content(objectMapper.writeValueAsString(faculty))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("math"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void delete() throws Exception {
        Faculty faculty = new Faculty(1L, "math", "red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
//        when(studentRepository.delete(ArgumentMatchers.any(Student.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/1")
                        .content(objectMapper.writeValueAsString(faculty))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("math"))
                .andExpect(jsonPath("$.color").value("red"))
        ;
    }

    //    @Test
//    void filteredByColor() throws Exception {
//        Faculty faculty = new Faculty(1L, "math", "red");
//        when(facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase("math", "math")
//                .thenReturn(Arrays.asList(
//                        new Faculty(1L, "math", "red"),
//                        new Faculty(1L, "meh", "blue")
//                )));
//
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/by-color-or-name?color=red")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].name").value("math"))
//                .andExpect(jsonPath("$[1].name").value("meh"))
//        ;
//    }
//    @Test
//    void findByStudent() throws Exception {
//        List<Faculty> faculties = Arrays.asList(
//                new Faculty(1L, "math", "red"),
//                new Faculty(1L, "meh", "blue"));
//        Student student = new Student(1L, "boris", 20);
//        student.setFaculty(faculties);
//
//        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/by-student?studentId=1")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray());
//    }

}
