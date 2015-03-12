package cr.tec.lpsolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Leo
 */
public class TransportProblem {
    
    /**
     * The name of each consumer.
     */
    private String[] consumers;
    
    /**
     * The name of each producer.
     */
    private String[] producers;
    
    /**
     * The production quantity for each producers.
     */
    private double[] production;
    
    /**
     * The demand quantity for each consumer.
     */
    private double[] demand;
    
    /**
     * A cost information matrix of transporting products from one place to another.
     */
    private double[][] costTable;
    
    /**
     * Constructs a {@code TransportProblem}.
     */
    public TransportProblem() {
        this.producers = new String[0];
        this.consumers = new String[0];
        this.production = new double[0];
        this.demand = new double[0];
        this.costTable = new double[0][0];
    }
    
    /**
     * Add a producer.
     * 
     * @param producer The producer name.
     */
    public void addProducer(String producer) {
        // Allocate a new space in the producers array.
        String[] newProducers = new String[producers.length + 1];
        System.arraycopy(producers, 0, newProducers, 0, producers.length);

        // Allocate a new space in the production array.
        double[] newProduction = new double[producers.length + 1];
        System.arraycopy(production, 0, newProduction, 0, producers.length);

        // Allocate a new row in the cost matrix.
        double[][] newCostTable = new double[producers.length + 1][consumers.length];
        System.arraycopy(costTable, 0, newCostTable, 0, producers.length * consumers.length);
        
        // Override arrays.
        producers = newProducers;
        production = newProduction;
        costTable = newCostTable;
        
        // Add producer to the array.
        producers[producers.length - 1] = producer;
        
        // Set production to -1.
        production[producers.length - 1] = -1;
        
        // Set the cost to all consumers to -1.
        for (int j = 0; j< consumers.length; j++) {
            costTable[producers.length - 1][j] = -1;
        }
    }
    
    /**
     * Add a producer.
     * 
     * @param producer The producer name.
     * @param production The production quantity.
     */
    public void addProducer(String producer, double production) {
        this.addProducer(producer);
        this.setProduction(producer, production);
    }
    
    /**
     * Add a consumer
     * 
     * @param consumer The consumer name. 
     */
    public void addConsumer(String consumer) {
        // Allocate a new space in the consumers array.
        String[] newConsumers = new String[consumers.length + 1];
        System.arraycopy(consumers, 0, newConsumers, 0, consumers.length);

        // Allocate a new space in the demand array.
        double[] newDemand = new double[consumers.length + 1];
        System.arraycopy(demand, 0, newDemand, 0, consumers.length);
        
        // Allocate a new space in each row of the cost matrix.
        for (int i = 0; i < producers.length; i++) {
            double[] newRow = new double[consumers.length + 1];
            System.arraycopy(costTable[i], 0, newRow, 0, consumers.length);
            
            // Set cost from current producer to -1.
            newRow[consumers.length] = -1;
            
            // Override row.
            costTable[i] = newRow;
        }
        
        // Override arrays.
        consumers = newConsumers;
        demand = newDemand;
        
        // Add consumer to the array.
        consumers[consumers.length - 1] = consumer;
        
        // Set demand to -1.
        demand[consumers.length - 1] = -1;
    }
    
    /**
     * Add a consumer
     * 
     * @param consumer The consumer name. 
     * @param demand The demand quantity.
     */
    public void addConsumer(String consumer, double demand) {
        this.addConsumer(consumer);
        this.setDemand(consumer, demand);
    }
    
    /**
     * Sets the production quantity related to a specific producer.
     * 
     * @param producer producer's name.
     * @param value the value to be asigned to the producer.
     */
    public void setProduction(String producer, double value) {
        int producerIndex = this.getProducersIndex(producer);
        production[producerIndex] = value;
    }
    
    /**
     * Sets the demand quantity related to a specific consumer.
     * 
     * @param consumer consumer's name.
     * @param value the value to be asigned to the consumer.
     */
    public void setDemand(String consumer, double value) {
        int consumerIndex = this.getConsumersIndex(consumer);
        demand[consumerIndex] = value;
    }
    
    /**
     * Adds a cost to the cost's matrix.
     * 
     * @param producer producer's name.
     * @param consumer consumer's name. 
     * @param value the value to relate within the indexed space.
     */
    public void setCost(String producer, String consumer, double value)
    {
        int producerIndex = this.getProducersIndex(producer);
        int consumerIndex = this.getConsumersIndex(consumer);
        costTable[producerIndex][consumerIndex] = value;
    }

    /**
     * Get the consumers names.
     * 
     * @return The consumers names.
     */
    public String[] getConsumers() {
        return consumers;
    }

    /**
     * Gets the producers names.
     * 
     * @return The producers names.
     */
    public String[] getProducers() {
        return producers;
    }
    
    /**
     * Gets the production quantity related to a specific producer.
     * 
     * @param producer The producer name.
     * @return The production quantity or -1 if the producer doesn't have an assigned production.
     */
    public double getProduction(String producer) {
        int producerIndex = this.getProducersIndex(producer);
        return production[producerIndex];
    }
    
    /**
     * Get the sum of the production quantity of all producers.
     * 
     * @return The total production.
     */
    public double getTotalProduction() {
        double currentProduction, totalProduction = 0.0;
        
        for (String producer : this.producers) {
            currentProduction = this.getProduction(producer);
            if (currentProduction >= 0) {
                totalProduction += currentProduction;
            }
        }
        
        return totalProduction;
    }
    
    /**
     * Gets a map with the each producer name and their production quantity.
     * A quantity of -1 means that the producer doesn't have an assigned production.
     * 
     * @return The production quantity of each producer.
     */
    public Map<String, Double> getEachProduction() {
        Map<String, Double> allProduction = new HashMap<>();
        double currentProduction;
        
        for (String producer : this.producers) {
            currentProduction = this.getProduction(producer);
            allProduction.put(producer, currentProduction);
        }
        
        return allProduction;
    }
    
    /**
     * Gets the demand quantity related to a specific consumer.
     * 
     * @param consumer The consumer name.
     * @return The demand quantity or -1 if the consumer doesn't have an assigned demand.
     */
    public double getDemand(String consumer) {
        int consumerIndex = this.getConsumersIndex(consumer);
        return demand[consumerIndex];
    }
    
    /**
     * Get the sum of the demand quantity of all consumers.
     * 
     * @return The total demand.
     */
    public double getTotalDemand() {
        double currentDemand, totalDemand = 0.0;
        
        for (String consumer : this.consumers) {
            currentDemand = this.getDemand(consumer);
            if (currentDemand >= 0) {
                totalDemand += currentDemand;
            }
        }
        
        return totalDemand;
    }
    
    /**
     * Gets a map with the each consumer name and their demand quantity.
     * A quantity of -1 means that the consumer doesn't have an assigned demand.
     * 
     * @return The demand quantity of each consumer.
     */
    public Map<String, Double> getEachDemand() {
        Map<String, Double> allDemand = new HashMap<>();
        double currentDemand;
        
        for (String consumer : this.consumers) {
            currentDemand = this.getDemand(consumer);
            allDemand.put(consumer, currentDemand);
        }
        
        return allDemand;
    }

    /**
     * Get the transportation cost from a specific producer to a specific consumer.
     * 
     * @param producer The producer name.
     * @param consumer The consumer name.
     * @return The transportation cost.
     */
    public double getCost(String producer, String consumer) {
        int producerIndex = this.getProducersIndex(producer);
        int consumerIndex = this.getConsumersIndex(consumer);
        return costTable[producerIndex][consumerIndex];
    }
    
    /**
     * Get the number of producers.
     * 
     * @return The number of producers.
     */
    public int getProducersCount() {
        return this.producers.length;
    }
    
    /**
     * Get the number of consumers.
     * 
     * @return The number of consumers.
     */
    public int getConsumersCount() {
        return this.consumers.length;
    }
    
    /**
     * Obtains an index from the producers's vector.
     * 
     * @param producer the name of the producer to search for.
     * 
     * @return the index(or space) where the producer is stored. 
     */
    private int getProducersIndex(String producer) {
        return Arrays.binarySearch(this.producers, producer);
    }
    
    /**
     * Obtains an index from the consumers's vector.
     * 
     * @param consumer the name of the consumer to search for.
     * 
     * @return the index(or space) where the consumer is stored.
     */
    private int getConsumersIndex(String consumer) {
        return Arrays.binarySearch(this.consumers, consumer);
    }
    
    public Problem toProblem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
