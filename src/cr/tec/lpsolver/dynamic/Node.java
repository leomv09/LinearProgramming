/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.tec.lpsolver.dynamic;

/**
 *
 * @author Leo
 */
public class Node {
    private final String name;
    
    public Node(String Name)
    {
        this.name = Name;
    }
    
    public String getName()
    {
        return this.name;
    }
}
