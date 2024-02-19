package edu.controllers;

import edu.DTOs.InputDTOs.InputApplicantDTO;
import edu.DTOs.OutputDTOs.OutputApplicantDTO;
import edu.exceptions.applicantExceptions.ApplicantCodeExistException;
import edu.exceptions.applicantExceptions.ApplicantDoesNotExistException;
import edu.exceptions.applicantExceptions.WrongApplicantCodeException;
import edu.exceptions.applicantExceptions.WrongApplicantRatingException;
import edu.exceptions.enumsExceptions.UnknownValueForGenderEnum;
import edu.services.ApplicantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/applicant")
@RestController
public class ApplicantController {
    private ApplicantService applicantService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody InputApplicantDTO inputApplicant) {
        try {
            OutputApplicantDTO outputApplicantDTO = applicantService.save(inputApplicant);
            return ResponseEntity.ok().body(outputApplicantDTO);
        }
        catch (
                ApplicantCodeExistException
                | WrongApplicantCodeException
                | UnknownValueForGenderEnum
                | WrongApplicantRatingException exception
        ) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/findById")
    public ResponseEntity findById(@RequestParam("id") Long id) {
        try {
            OutputApplicantDTO outputApplicantDTO = applicantService.findById(id);
            return ResponseEntity.ok().body(outputApplicantDTO);
        }
        catch (ApplicantDoesNotExistException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/findByCode")
    public ResponseEntity findByCode(@RequestParam("code") String code) {
        try {
            OutputApplicantDTO outputApplicantDTO = applicantService.findByCode(code);
            return ResponseEntity.ok().body(outputApplicantDTO);
        }
        catch (
                WrongApplicantCodeException
                | ApplicantDoesNotExistException exception
        ) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll() {
        try {
            List<OutputApplicantDTO> outputApplicantDTOList = applicantService.findAll();
            return ResponseEntity.badRequest().body(outputApplicantDTOList);
        }
        catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PatchMapping("/updateById")
    public ResponseEntity updateById(
            @RequestParam("id") Long id,
            @RequestBody InputApplicantDTO inputApplicant
    ) {
        try {
            OutputApplicantDTO outputApplicantDTO = applicantService.updateById(id, inputApplicant);
            return ResponseEntity.ok().body(outputApplicantDTO);
        }
        catch (
                ApplicantDoesNotExistException
                | ApplicantCodeExistException
                | WrongApplicantCodeException
                | UnknownValueForGenderEnum
                | WrongApplicantRatingException exception
        ) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity deleteById(@RequestParam("id") Long id) {
        try {
            OutputApplicantDTO outputApplicantDTO = applicantService.deleteById(id);
            return ResponseEntity.ok().body(outputApplicantDTO);
        }
        catch (ApplicantDoesNotExistException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
