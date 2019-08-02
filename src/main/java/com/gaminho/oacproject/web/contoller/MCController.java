package com.gaminho.oacproject.web.contoller;

import com.gaminho.oacproject.exception.mc.InvalidMCException;
import com.gaminho.oacproject.exception.mc.MCNotFoundException;
import com.gaminho.oacproject.exception.mc.NoMCException;
import com.gaminho.oacproject.exception.project.TypeNotFoundException;
import com.gaminho.oacproject.exception.song.NoSongException;
import com.gaminho.oacproject.model.MC;
import com.gaminho.oacproject.web.service.MCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200")
public class MCController {

    @Autowired
    private MCService mcService;

    /**
     * GET  /mcs/:id : get the "id" mc.
     *
     * @param id the id of the mc to retrieve
     * @return the ResponseEntity with status 200 (OK) with mc as body or with status 404 (Not Found)
     */
    @GetMapping(value="/mcs/{id}")
    public ResponseEntity<?> getMCWithId(@PathVariable(value = "id") long id) {
        try {
            return ResponseEntity.ok(mcService.getMCWithId(id));
        } catch (MCNotFoundException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * GET /mcs : get all mcs
     * @return the ResponseEntity with status 200 (OK) and the list of {@link MC} or a message of empty data
     */
    @GetMapping(value = "/mcs")
    public ResponseEntity<?> getAllMCs(){
        try {
            List<MC> list = mcService.getAllMCs();
            return ResponseEntity.ok(list);
        } catch (NoMCException e){
//            return ResponseEntity.noContent().build();
            return ResponseEntity.ok(e.getMessage());
        }
    }

    /**
     * POST /mcs : create a new mc
     * @param mc mc to save
     * @return ResponseEntity with status 201 (created) with MC as body or a status 400 (Bad request)
     */
    @PostMapping(value = "/mcs")
    public ResponseEntity<?> createMC(@Valid @RequestBody MC mc){
        try {
            MC newMC = mcService.saveMC(mc);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newMC.getId())
                    .toUri();
            return ResponseEntity.created(location).body(newMC);
        } catch (InvalidMCException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PUT /mcs/:id : update the mc with given id
     * @param id id of the mc to update
     * @param mc mc with new data
     * @return ResponseEntity with status 201 (created) with mc as body or a status 400 (Bad request)
     */
    @PutMapping(value = "/mcs/{id}")
    public ResponseEntity<?> updateMC(@PathVariable(value = "id") long id, @Valid @RequestBody MC mc){
        try {
            MC newMC = mcService.updateMC(id, mc);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();
            return ResponseEntity.created(location).body(newMC);
        } catch (MCNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * DELETE /mcs/:id : delete the mc with given id
     * @param id id of the mc to delete
     * @return ResponseEntity with status 200 (OK) and a string as body indicating the action done
     */
    //TODO verify negative id or null id
    @DeleteMapping(value="/mcs/{id}")
    public ResponseEntity<?> deleteMC(@PathVariable(value = "id") long id) {
        try {
            mcService.deleteMC(id);
            return ResponseEntity.ok().build();
        } catch (MCNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * DELETE /mcs : delete all mcs
     * @return ResponseEntity with status 200 (OK) and a string as body indicating the action done
     */
    @DeleteMapping(value="/mcs")
    public ResponseEntity<?> deleteAllMCs() {
        try {
            mcService.deleteAllMCs();
            return ResponseEntity.ok().build();
        } catch (NoMCException e) {
            return ResponseEntity.noContent().build();
        }
    }

}
