package cr.tec.lpsolver.objects;

import cr.tec.lpsolver.FeasibleRegion2D;
import cr.tec.lpsolver.FeasibleRegion2DFactory;

/**
 * Contains some useful predefined {@link FeasibleRegion2D}.
 * 
 * @author jose
 */
public class FeasibleRegionObjects {
    
    private static FeasibleRegionObjects instance; 
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#GQE_DEC_XY}.
     */
    public final FeasibleRegion2D GQE_DEC_XY;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#GQE_INC_XY}.
     */
    public final FeasibleRegion2D GQE_INC_XY;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#GQE_INC_X}.
     */
    public final FeasibleRegion2D GQE_INC_X;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#GQE_INC_Y}.
     */
    public final FeasibleRegion2D GQE_INC_Y;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#GQE_X}.
     */
    public final FeasibleRegion2D GQE_X;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#GQE_Y}.
     */
    public final FeasibleRegion2D GQE_Y;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#LQE_DEC_XY}.
     */
    public final FeasibleRegion2D LQE_DEC_XY;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#LQE_INC_XY}.
     */
    public final FeasibleRegion2D LQE_INC_XY;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#LQE_INC_X}.
     */
    public final FeasibleRegion2D LQE_INC_X;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#LQE_INC_Y}.
     */
    public final FeasibleRegion2D LQE_INC_Y;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#LQE_X}.
     */
    public final FeasibleRegion2D LQE_X;
    
    /**
     * Feasible region defined by the constraint {@link ConstraintObjects#LQE_Y}.
     */
    public final FeasibleRegion2D LQE_Y;
    
    /**
     * Construct a new {@code FeasibleRegionObjects}.
     */
    private FeasibleRegionObjects() {
        String[] variables = new String[] {"x", "y"};
        FeasibleRegion2DFactory factory = new FeasibleRegion2DFactory(variables);
        ConstraintObjects constraints = ConstraintObjects.getInstance();
        
        this.GQE_DEC_XY = factory.createFeasibleRegion(constraints.GQE_DEC_XY);
        this.GQE_INC_XY = factory.createFeasibleRegion(constraints.GQE_INC_XY);
        this.GQE_INC_X = factory.createFeasibleRegion(constraints.GQE_INC_X);
        this.GQE_INC_Y = factory.createFeasibleRegion(constraints.GQE_INC_Y);
        this.GQE_X = factory.createFeasibleRegion(constraints.GQE_X);
        this.GQE_Y = factory.createFeasibleRegion(constraints.GQE_Y);
        this.LQE_DEC_XY = factory.createFeasibleRegion(constraints.LQE_DEC_XY);
        this.LQE_INC_XY = factory.createFeasibleRegion(constraints.LQE_INC_XY);
        this.LQE_INC_X = factory.createFeasibleRegion(constraints.LQE_INC_X);
        this.LQE_INC_Y = factory.createFeasibleRegion(constraints.LQE_INC_Y);
        this.LQE_X = factory.createFeasibleRegion(constraints.LQE_X);
        this.LQE_Y = factory.createFeasibleRegion(constraints.LQE_Y);
    }
    
    /**
     * Get the unique instance of this class.
     * 
     * @return The instance
     */
    public static FeasibleRegionObjects getInstance() {
        if (instance == null) {
            instance = new FeasibleRegionObjects();
        }
        return instance;
    }
    
}
