package cr.tec.lpsolver.dynamic.equipment;

import cr.tec.lpsolver.dynamic.DynamicSolver;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class EquipmentReplacementSolver implements DynamicSolver {

    @Override
    public Object solve(Object obj) throws Exception {
        EquipmentProblem problem = (EquipmentProblem) obj;
        EquipmentAlgorithm algorithm = new EquipmentAlgorithm(problem);
        algorithm.execute();
        return new EquipmentResult(algorithm.getDecisions(), algorithm.getCost());
    }
    
}
