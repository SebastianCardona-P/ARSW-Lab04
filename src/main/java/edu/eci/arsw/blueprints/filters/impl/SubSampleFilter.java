package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.filters.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Service
public class SubSampleFilter implements Filter {


    @Override
    public Blueprint filterPlain(Blueprint blueprint) {

        List<Point> originalPoints = blueprint.getPoints();
        // Si solo hay un punto, no hay nada que filtrar
        if (blueprint.getPoints().size() <= 1) {
            return blueprint;
        }

        // Crear un nuevo array con la mitad de los puntos (eliminando 1 de cada 2)
        Point[] filteredPoints = new Point[(originalPoints.size() + 1) / 2];
        for (int i = 0, j = 0; i < originalPoints.size(); i += 2, j++) {
            filteredPoints[j] = originalPoints.get(i);
        }

        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), filteredPoints);
    }
    @Override
    public Set<Blueprint> filterBlueprints(Set<Blueprint> blueprints) {
        Set<Blueprint> newBlueprintSet = new HashSet<>();

        for (Blueprint i : blueprints) {
            newBlueprintSet.add(filterPlain(i));
        }

        return newBlueprintSet;
    }
}
