package com.gaminho.oacproject.web.contoller;

import com.gaminho.oacproject.error.exception.project.InvalidTypeException;
import com.gaminho.oacproject.error.exception.project.NoTypeException;
import com.gaminho.oacproject.error.exception.project.TypeNotFoundException;
import com.gaminho.oacproject.model.ProjectType;
import com.gaminho.oacproject.web.OACExceptionHandler;
import com.gaminho.oacproject.web.service.ProjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
public class ProjectTypeController {

    @Autowired
    private ProjectTypeService typeService;

    /**
     * GET /project-types : get all project types
     * @return the ResponseEntity with status 200 (OK) and the list of {@link ProjectType} or a message of empty data
     */
    @GetMapping(value = "/project-types")
    public ResponseEntity<?> getAllProjectTypes(){
        try {
            List<ProjectType> list = typeService.getAllProjectTypes();
            return ResponseEntity.ok(list);
        } catch (NoTypeException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * GET  /project-types/:id : get the "id" project type.
     *
     * @param id the id of the project type to retrieve
     * @return the ResponseEntity with status 200 (OK) with project type as body or with status 404 (Not Found)
     */
    @GetMapping(value="/project-types/{id}")
    public ResponseEntity<?> getProjectTypeWithId(@PathVariable(value = "id") long id) {
        try {
            return ResponseEntity.ok(typeService.getProjectTypeWithId(id));
        } catch (TypeNotFoundException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * POST /project-types : create a new project type
     * @param projectType song to save
     * @return ResponseEntity with status 201 (created) with type as body or a status 400 (Bad request)
     */
    @PostMapping(value = "/project-types")
    public ResponseEntity<?> createProjectType(@RequestBody ProjectType projectType){
        try {
            ProjectType newType = typeService.saveProjectType(projectType);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newType.getId())
                    .toUri();
            return ResponseEntity.created(location).body(newType);
        } catch (InvalidTypeException e){
            return OACExceptionHandler.handleInvalidTypeException(e);
        }
    }

    /**
     * PUT /project-types/:id : update the project type with given id
     * @param id id of the song to update
     * @param projectType project-type with new data
     * @return ResponseEntity with status 201 (created) with project type as body or a status 400 (Bad request)
     */
    @PutMapping(value = "/project-types/{id}")
    public ResponseEntity<?> updateProjectType(@PathVariable(value = "id") long id, @Valid @RequestBody ProjectType projectType){
        try {
            ProjectType newType = typeService.updateProjectType(id, projectType);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();
            return ResponseEntity.created(location).body(newType);
        } catch (TypeNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * DELETE /project-types/:id : delete the project type with given id
     * @param id id of the project type to delete
     * @return ResponseEntity with status 200 (OK) and a string as body indicating the action done
     */
    @DeleteMapping(value="/project-types/{id}")
    public ResponseEntity<?> deleteProjectType(@PathVariable(value = "id") long id) {
        try {
            typeService.deleteProjectType(id);
            return ResponseEntity.ok().build();
        } catch (TypeNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * DELETE /project-types : delete all project types
     * @return ResponseEntity with status 200 (OK) and a string as body indicating the action done
     */
    @DeleteMapping(value="/project-types")
    public ResponseEntity<?> deleteAllProjectTypes() {
        try {
            typeService.deleteAllTypes();
            return ResponseEntity.ok().build();
        } catch (NoTypeException e) {
            return ResponseEntity.noContent().build();
        }
    }

}
