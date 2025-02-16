package edu.eci.arsw.blueprints.controller;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping( "/blueprints")
public class BluePrintController {

    @Autowired
    private final BlueprintsServices bps;



    public BluePrintController(BlueprintsServices bps) {
        this.bps = bps;
    }

    @GetMapping("/{author}/{name}")
    public ResponseEntity<?> getBlueprint(@PathVariable("author") String author, @PathVariable("name") String name) {
        try {
            return ResponseEntity.ok(bps.getBlueprint(author, name));
        } catch (BlueprintNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blueprint not found: " + author + "/" + name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintByAuthor(@PathVariable("author") String author) {
        try {
            Set<Blueprint> blueprints = bps.getBlueprintsByAuthor(author);
            return ResponseEntity.ok(blueprints);
        } catch (BlueprintNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blueprints not found for author: " + author);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PostMapping
    public ResponseEntity<?> registerBlueprint(@RequestBody Blueprint bp){
        HashMap<String, Object> response = new HashMap<>();
        try {
            bps.addNewBlueprint(bp);
            response.put("status", "success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public Set<Blueprint> getAllBlueprints(){
        return bps.getAllBlueprints();
    }
}
