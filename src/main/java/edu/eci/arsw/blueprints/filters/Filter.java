package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import org.springframework.stereotype.Service;
import java.util.Set;


public interface Filter {
    public abstract Blueprint filterPlain(Blueprint blueprint);

    public Set<Blueprint> filterBlueprints(Set<Blueprint> blueprints);

}
