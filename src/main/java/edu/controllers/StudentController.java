package edu.controllers;

import edu.DTO.InputStudentDTO;
import edu.DTO.OutputStudentDTO;
import edu.exceptions.applicantExceptions.ApplicantDoesNotExistException;
import edu.exceptions.studentExceptions.StudentDoesNotExistException;
import edu.exceptions.studentExceptions.StudentEmailExistException;
import edu.exceptions.studentExceptions.WrongStudentEmailException;
import edu.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/student")
@RestController
public class StudentController {
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody InputStudentDTO inputStudentDTO) {
        try {
            OutputStudentDTO outputStudentDTO = studentService.save(inputStudentDTO);
            return ResponseEntity.ok().body(outputStudentDTO);
        }
        catch (
                ApplicantDoesNotExistException
                | WrongStudentEmailException
                | StudentEmailExistException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/findById")
    public ResponseEntity findById(@RequestParam("id") Long id) {
        try {
            OutputStudentDTO outputStudentDTO = studentService.findById(id);
            return ResponseEntity.ok().body(outputStudentDTO);
        }
        catch (
                StudentDoesNotExistException
                | ApplicantDoesNotExistException exception
        ) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/findByApplicant_Id")
    public ResponseEntity findByApplicant_id(@RequestParam("applicant_id") Long applicant_id) {
        try {
            OutputStudentDTO outputStudentDTO = studentService.findByApplicant_Id(applicant_id);
            return ResponseEntity.ok().body(outputStudentDTO);
        }
        catch (
                ApplicantDoesNotExistException
                | StudentDoesNotExistException exception
        ) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/findByEmail")
    public ResponseEntity findByEmail(@RequestParam("email") String email) {
        try {
            OutputStudentDTO outputStudentDTO = studentService.findByEmail(email);
            return ResponseEntity.ok().body(outputStudentDTO);
        }
        catch (
                WrongStudentEmailException
                | StudentDoesNotExistException
                | ApplicantDoesNotExistException exception
        ) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll() {
        try {
            List<OutputStudentDTO> outputStudentDTOList = studentService.findAll();
            return ResponseEntity.ok().body(outputStudentDTOList);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PatchMapping("/updateById")
    public ResponseEntity updateById(
            @RequestParam("id") Long id,
            @RequestBody InputStudentDTO inputStudentDTO
    ) {
        try {
            OutputStudentDTO outputStudentDTO = studentService.updateById(id, inputStudentDTO);
            return ResponseEntity.ok().body(outputStudentDTO);
        }
         catch (
                 StudentDoesNotExistException
                 | ApplicantDoesNotExistException
                 | WrongStudentEmailException
                 | StudentEmailExistException exception
         ) {
            return ResponseEntity.badRequest().body(exception.getMessage());
         }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity deleteById(@RequestParam("id") Long id) {
        try {
            OutputStudentDTO outputStudentDTO = studentService.deleteById(id);
            return ResponseEntity.ok().body(outputStudentDTO);
        }
        catch (
                StudentDoesNotExistException
                | ApplicantDoesNotExistException exception
        ) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
