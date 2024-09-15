package com.example.personnel_backend.controller;

import com.example.personnel_backend.service.PersonnelService;
import com.example.personnel_backend.entity.Personnel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/personnel")
@Validated
@Tag(name = "Personnel", description = "Personnel management API")
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @Operation(summary = "Get All Personnel", description = "Returns a list of all personnel")
    @GetMapping
    public List<Personnel> getAllPersonnel() {
        return personnelService.getAllPersonnel();
    }

    @Operation(summary = "Get Personnel by ID", description = "Returns a personnel by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Personnel> getPersonnelById(@PathVariable Long id) {
        Optional<Personnel> personnel = personnelService.getPersonnelById(id);
        if (personnel.isPresent()) {
            return ResponseEntity.ok(personnel.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create New Personnel", description = "Creates a new personnel record")
    @PostMapping
    public ResponseEntity<Personnel> createPersonnel(@Valid @RequestBody Personnel personnel) {

        // Save new personnel record
        Personnel savedPersonnel = personnelService.savePersonnel(personnel);

        // Return saved personnel record
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonnel);
    }

    @Operation(summary = "Update Existing Personnel", description = "Updates an existing personnel record")
    @PutMapping("/{id}")
    public ResponseEntity<Personnel> updatePersonnel(@PathVariable Long id, @Valid @RequestBody Personnel personnel) {
        Optional<Personnel> existingPersonnel = personnelService.getPersonnelById(id);
        if (!existingPersonnel.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Get ID from existing personnel record
        personnel.setId(id);
        Personnel updatedPersonnel = personnelService.savePersonnel(personnel);
        return ResponseEntity.ok(updatedPersonnel);
    }


    @Operation(summary = "Delete Personnel", description = "Deletes a personnel record by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonnel(@PathVariable Long id) {
        if (!personnelService.getPersonnelById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        personnelService.deletePersonnel(id);
        return ResponseEntity.noContent().build();
    }
}
