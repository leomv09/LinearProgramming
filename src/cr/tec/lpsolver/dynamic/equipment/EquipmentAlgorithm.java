package cr.tec.lpsolver.dynamic.equipment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class EquipmentAlgorithm {
    
    private final int years;
    private final int actualYear;
    private final int lowerBound;
    private final int upperBound;
    private final Table table;
    private final int newCost;
    private final Phase[] phases;
    private Node[][] nodes;

    public EquipmentAlgorithm(EquipmentProblem problem) {
        this.years = problem.getYears();
        this.actualYear = problem.getActualYear();
        this.lowerBound = problem.getLowerBound();
        this.upperBound = problem.getUpperBound();
        this.table = problem.getTable();
        this.newCost = problem.getNewCost();
        this.phases = new Phase[years];
        calculateNodes();
    }
    
    public void execute() {
        for (int i = years - 1; i >= 0; i--) {
            if (i == years - 1) {
                phases[i] = new Phase(nodes[i], table, newCost);
            }
            else {
                phases[i] = new Phase(nodes[i], phases[i+1], table, newCost);
            }
        }
    }
    
    public double getCost() {
        return phases[0].row(actualYear).f();
    }
    
    public String[][] getDecisions() {
        List<List<String>> total = new ArrayList<>();
        List<String> curr = new ArrayList<>();
        total.add(curr);
        getDecisions(total, curr, 0, actualYear);
        
        String[][] result = new String[total.size()][];
        List<String> list;
        for (int i = 0; i < total.size(); i++) {
            list = total.get(i);
            result[i] = list.toArray(new String[list.size()]);
        }
        
        return result;
    }
    
    private void getDecisions(List<List<String>> total, List<String> prev, int phase, int year) {        
        if (phase == years) {
            return;
        }
        
        PhaseRow row = phases[phase].row(year);
        
        if (row.k() > row.r()) {
            prev.add("K");
            getDecisions(total, prev, phase + 1, year + 1);
        }
        else if (row.r() > row.k()) {
            prev.add("R");
            getDecisions(total, prev, phase + 1, 1);
        }
        else {
            List<String> keep = new ArrayList<>(prev);
            keep.add("K");
            List<String> replace = new ArrayList<>(prev);
            replace.add("R");
            total.add(keep);
            total.add(replace);
            total.remove(prev);
            getDecisions(total, keep, phase + 1, year + 1);
            getDecisions(total, replace, phase + 1, 1);
        }
    }
     
    private void calculateNodes() {
        nodes = new Node[years][];
        Node[] curr;
        
        for (int i = 0; i < years; i++) {
            if (i == 0) {
                curr = new Node[1];
                curr[0] = createNode(actualYear);
            }
            else {
                curr = createNodes(nodes[i-1]);
            }
            nodes[i] = curr;
        }
    }
    
    private Node createNode(int year) {
        boolean canKeep = year < upperBound;
        boolean canReplace = year >= lowerBound;
        return new Node(year, canKeep, canReplace);
    }
    
    private Node[] createNodes(Node[] prev) {
        Set<Node> lnodes = new HashSet<>();
        for (Node node : prev) {
            if (node.canKeep()) {
                lnodes.add(createNode(node.t() + 1));
            }
            if (node.canReplace()) {
                lnodes.add(createNode(1));
            }
        }
        return lnodes.toArray(new Node[lnodes.size()]);
    }
    
}
