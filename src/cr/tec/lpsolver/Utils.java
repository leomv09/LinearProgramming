package cr.tec.lpsolver;

import java.util.Collection;
import java.util.HashSet;

/**
 * Provides some general functions.
 * 
 * jose
 */
public class Utils {
    
    /**
     * Merge two collections.
     * 
     * @param <T> The type of the items in the collection.
     * @param c1 The first collection.
     * @param c2 The second collection.
     * 
     * @return The merged collection.
     */
    public static <T> Collection<T> merge(Collection<T> c1, Collection<T> c2) {
        Collection<T> c = new HashSet<>();
        c.addAll(c1);
        c.addAll(c2);
        return c;
    }
    
}
