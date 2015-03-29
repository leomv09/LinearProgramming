package cr.tec.lpsolver;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * jose
 */
public class Utils {
    
    public static <T> Collection<T> merge(Collection<T> c1, Collection<T> c2) {
        Collection<T> c = new HashSet<>();
        c.addAll(c1);
        c.addAll(c2);
        return c;
    }
    
}
