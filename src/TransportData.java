
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Leo
 */
public class TransportData {
    
    private final String [] consumers;//Name of each consumer.
    private final String [] producers;//Name of each producer.
    private double [] productionQuantity;//The total production quantity from all the producers.
    private double [] demandQuantity;//The total demand quantity from all the producers.
    private double [][] costTable;//A cost information matrix of transporting products from one place to another.
    
    
    /**
     * Creates a new instance of TransportData.
     * @param producers A vector of names for the producer variables.
     * @param consumers A vector of names for the consumer variables.
     */
    public TransportData(String [] producers, String [] consumers)
    {
        this.producers = producers;
        this.consumers = consumers;
    }
    
    /**
     * Adds a cost to the cost's matrix.
     * @param producer producer's name.
     * @param consumer consumer's name. 
     * @param value the value to relate within the indexed space.
     */
    public void addCost(String producer, String consumer, double value)
    {
        this.costTable[getProducersIndex(producer)][getConsumersIndex(consumer)] = value;
    }
    
    /**
     * Adds a quantity to the quantity's vector related to a specific producer.
     * @param producer producer's name.
     * @param value the value to be asigned to the producer.
     */
    public void addProductionQuantity(String producer, double value)
    {
        this.productionQuantity[getProducersIndex(producer)] = value;
    }
    
    /**
     * Adds a quantity to the quantity's vector related to a specific demandant.
     * @param consumer consumer's name.
     * @param value the value to be asigned to the consumer.
     */
    public void addDemandQuantity(String consumer, double value)
    {
        this.demandQuantity[getConsumersIndex(consumer)] = value;
    }
    
    /**
     * Obtains an index from the producers's vector.
     * @param producer the name of the producer to search for.
     * @return the index(or space) where the producer is stored. 
     */
    private int getProducersIndex(String producer)
    {
        return Arrays.binarySearch(this.producers, producer);
    }
    
    /**
     * Obtains an index from the consumers's vector.
     * @param consumer the name of the consumer to search for.
     * @return the index(or space) where the consumer is stored.
     */
    private int getConsumersIndex(String consumer)
    {
        return Arrays.binarySearch(this.consumers, consumer);
    }
    
    
    
}
