package cr.tec.lpsolver.objects;

import cr.tec.lpsolver.Constraint;
import cr.tec.lpsolver.Linear;
import cr.tec.lpsolver.Relationship;

/**
 * Contains some useful predefined {@link Constraint}.
 * 
 * @author jose
 */
public class ConstraintObjects {
    
    public static ConstraintObjects instance;
        
    /**
     * A constraint that have relationship GQE, is decreasing and intersects both axis.
     * UNBOUNDED.
     * 
     * 3x + 2y >= 12
     * y = -3/2x + 6
     */
    public final Constraint GQE_DEC_XY;
    
    /**
     * A constraint that have relationship GQE, is increasing and intersects both axis.
     * UNBOUNDED.
     * 
     * y - x >= 0
     * y = x
     */
    public final Constraint GQE_INC_XY;
    
    /**
     * A constraint that have relationship GQE, is increasing and intersects the X axis.
     * UNBOUNDED.
     * 
     * 20x - 5y >= 15
     * y = 4x - 3
     */
    public final Constraint GQE_INC_X;
    
    /**
     * A constraint that have relationship GQE, is increasing and intersects the Y axis.
     * UNBOUNDED.
     * 
     * -2x + y >= 3
     * y = 2x + 3
     */
    public final Constraint GQE_INC_Y;

    /**
     * A constraint that have relationship GQE and intersects X axis (vertical).
     * UNBOUNDED.
     * 
     * x >= 6
     */
    public final Constraint GQE_X;
    
    /**
     * A constraint that have relationship GQE and intersects Y axis (horizontal).
     * UNBOUNDED.
     * 
     * y >= 9
     */
    public final Constraint GQE_Y;
    
    /**
     * A constraint that have relationship LQE, is decreasing and intersects both axis.
     * BOUNDED.
     * 
     * 3x + 2y <= 12
     * y = -3/2x + 6
     */
    public final Constraint LQE_DEC_XY;
    
    /**
     * A constraint that have relationship LQE, is increasing and intersects both axis.
     * UNBOUNDED.
     * 
     * y - x <= 0
     * y = x
     */
    public final Constraint LQE_INC_XY;
    
    /**
     * A constraint that have relationship LQE, is increasing and intersects the X axis.
     * UNBOUNDED.
     * 
     * 20x - 5y <= 15
     * y = 4x - 3
     */
    public final Constraint LQE_INC_X;
    
    /**
     * A constraint that have relationship LQE, is increasing and intersects the Y axis.
     * UNBOUNDED.
     * 
     * -2x + y <= 3
     * y = 2x + 3
     */
    public final Constraint LQE_INC_Y;

    /**
     * A constraint that have relationship LQE and intersects X axis (vertical).
     * UNBOUNDED.
     * 
     * x <= 6
     */
    public final Constraint LQE_X;
    
    /**
     * A constraint that have relationship LQE and intersects Y axis (horizontal).
     * UNBOUNDED.
     * 
     * y <= 9
     */
    public final Constraint LQE_Y;
    
    /**
     * Construct a new {@code ConstraintObjects}.
     */
    private ConstraintObjects() {
        Linear linear;
        
        linear = new Linear();
        linear.add(3, "x");
        linear.add(2, "y");
        this.GQE_DEC_XY = new Constraint(linear, Relationship.GEQ, 12);
        this.LQE_DEC_XY = new Constraint(linear, Relationship.LEQ, 12);
        
        linear = new Linear();
        linear.add(-1, "x");
        linear.add(1, "y");
        this.GQE_INC_XY = new Constraint(linear, Relationship.GEQ, 0);
        this.LQE_INC_XY = new Constraint(linear, Relationship.LEQ, 0);
        
        linear = new Linear();
        linear.add(20, "x");
        linear.add(-5, "y");
        this.GQE_INC_X = new Constraint(linear, Relationship.GEQ, 15);
        this.LQE_INC_X = new Constraint(linear, Relationship.LEQ, 15);
        
        linear = new Linear();
        linear.add(-2, "x");
        linear.add(1, "y");
        this.GQE_INC_Y = new Constraint(linear, Relationship.GEQ, 3);
        this.LQE_INC_Y = new Constraint(linear, Relationship.LEQ, 3);
        
        linear = new Linear();
        linear.add(1, "x");
        this.GQE_X = new Constraint(linear, Relationship.GEQ, 6);
        this.LQE_X = new Constraint(linear, Relationship.LEQ, 6);
        
        linear = new Linear();
        linear.add(1, "y");
        this.GQE_Y = new Constraint(linear, Relationship.GEQ, 9);
        this.LQE_Y = new Constraint(linear, Relationship.LEQ, 9);
    }
    
    /**
     * Get the unique instance of this class.
     * 
     * @return The instance
     */
    public static ConstraintObjects getInstance() {
        if (instance == null) {
            instance = new ConstraintObjects();
        }
        return instance;
    }
    
}