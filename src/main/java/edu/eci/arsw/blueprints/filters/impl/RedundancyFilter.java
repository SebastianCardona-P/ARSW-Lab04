package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.filters.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

//@Service
public class RedundancyFilter implements Filter {

    @Override
    public Blueprint filterPlain(Blueprint blueprint){

        List<Point> originalPoints = blueprint.getPoints();
        // Si solo hay un punto, no hay nada que filtrar
        if (blueprint.getPoints().size() <= 1) {
            return blueprint;
        }

        List<Point> filteredPoints = new ArrayList<>();
        filteredPoints.add(originalPoints.get(0));

        for (int i = 1; i < originalPoints.size(); i++) {
            Point prev = originalPoints.get(i-1);
            Point current = originalPoints.get(i);

            // Solo agregamos el punto si no es igual al anterior
            if (!(prev.getX() == current.getX() && prev.getY() == current.getY())) {
                filteredPoints.add(current);
            }

        }

        // Convertimos la lista a un array de puntos
        Point[] resultPoints = filteredPoints.toArray(new Point[0]);
        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), resultPoints);
    }

    @Override
    public Set<Blueprint> filterBlueprints(Set<Blueprint> blueprints){
        Set<Blueprint> newBlueprintSet = new HashSet<>();

        for(Blueprint i: blueprints){
            newBlueprintSet.add(filterPlain(i));
        }

        return newBlueprintSet;
    }
}
