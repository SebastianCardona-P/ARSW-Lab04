/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        String authorTrimmed = bp.getAuthor().trim();
        String nameTrimmed = bp.getName().trim();
        Tuple<String, String> key = new Tuple<>(authorTrimmed, nameTrimmed);

        if (blueprints.containsKey(key)) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(key, bp);
        }
    }


    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        String authorTrimmed = author.trim();
        String nameTrimmed = bprintname.trim();
        Blueprint bp = blueprints.get(new Tuple<>(authorTrimmed, nameTrimmed));
        if (bp == null) {
            throw new BlueprintNotFoundException("Blueprint not found: " + author + " - " + bprintname);
        }
        return bp;
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        String authorTrimmed = author.trim();
        Set<Blueprint> result = new HashSet<>();
        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            if (entry.getKey().getElem1().equals(authorTrimmed)) {
                result.add(entry.getValue());
            }
        }

        if (result.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }
        return result;
    }


    @Override
    public Set<Blueprint> getAllBluePrints(){
        Set<Blueprint> blueprintSet = new HashSet<>();

        for (Tuple<String, String> i : blueprints.keySet()){
            blueprintSet.add(blueprints.get(i));
        }

        return blueprintSet;
    }
    
}
