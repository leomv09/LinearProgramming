package cr.tec.lpsolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Leo
 */
public class TransportProblem {
    
    /**
     * The name of each consumer.
     */
    private final List<String> consumers;
    
    /**
     * The name of each producer.
     */
    private final List<String> producers;
    
    /**
     * The production quantity for each producers.
     */
    private final List<Double> production;
    
    /**
     * The demand quantity for each consumer.
     */
    private final List<Double> demand;
    
    /**
     * A cost information matrix of transporting products from one place to another.
     */
    private final List<List<Double>> costTable;
    
    /**
     * Constructs a {@code TransportProblem}.
     */
    public TransportProblem()
    {
        this.producers = new ArrayList<>();
        this.consumers = new ArrayList<>();
        this.production = new ArrayList<>();
        this.demand = new ArrayList<>();
        this.costTable = new ArrayList<>();
    }
    
    /**
     * Add a producer.
     * 
     * @param producer The producer name.
     */
    public void addProducer(String producer) {
        this.producers.add(producer);
        
        // Fill the cell in the production quantity list with -1.
        this.production.add(-1.0);
        
        // Fill the new row in the matrix with -1.
        List<Double> row = new ArrayList<>();
        consumers.stream().forEach((_item) -> {
            row.add(-1.0);
        });
        
        this.costTable.add(row);
    }
    
    /**
     * Add a producer.
     * 
     * @param producer The producer name.
     * @param production The production quantity.
     */
    public void addProducer(String producer, Double production) {
        this.addProducer(producer);
        this.setProduction(producer, production);
    }
    
    /**
     * Add a consumer
     * 
     * @param consumer The consumer name. 
     */
    public void addConsumer(String consumer) {
        this.consumers.add(consumer);
        
        // Fill the cell in the demand quantity list with -1.
        this.demand.add(-1.0);
        
        // Fill the new column in the matrix with -1. 
        costTable.stream().forEach((row) -> {
            row.add(-1.0);
        });
    }
    
    /**
     * Add a consumer
     * 
     * @param consumer The consumer name. 
     * @param demand The demand quantity.
     */
    public void addConsumer(String consumer, Double demand) {
        this.addConsumer(consumer);
        this.setDemand(consumer, demand);
    }
    
    /**
     * Sets the production quantity related to a specific producer.
     * 
     * @param producer producer's name.
     * @param value the value to be asigned to the producer.
     */
    public void setProduction(String producer, Double value)
    {
        int producerIndex = producers.indexOf(producer);
        production.set(producerIndex, value);
    }
    
    /**
     * Sets the demand quantity related to a specific consumer.
     * 
     * @param consumer consumer's name.
     * @param value the value to be asigned to the consumer.
     */
    public void setDemand(String consumer, double value)
    {
        int consumerIndex = consumers.indexOf(consumer);
        demand.set(consumerIndex, value);
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
        int producerIndex = producers.indexOf(producer);
        int consumerIndex = consumers.indexOf(consumer);
        List<Double> row = costTable.get(producerIndex);
        row.set(consumerIndex, value);
    }

    /**
     * Get the consumers names.
     * 
     * @return The consumers names.
     */
    public List<String> getConsumers() {
        return consumers;
    }

    /**
     * Gets the producers names.
     * 
     * @return The producers names.
     */
    public List<String> getProducers() {
        return producers;
    }
    
    /**
     * Gets the production quantity related to a specific producer.
     * 
     * @param producer The producer name.
     * @return The production quantity or -1 if the producer doesn't have an assigned production.
     */
    public Double getProduction(String producer) {
        int producerIndex = producers.indexOf(producer);
        return production.get(producerIndex);
    }
    
    /**
     * Get the sum of the production quantity of all producers.
     * 
     * @return The total production.
     */
    public Double getTotalProduction() {
        Double currentProduction, totalProduction = 0.0;
        
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
    public Map<String, Double> getAllProduction() {
        Map<String, Double> allProduction = new HashMap<>();
        Double currentProduction;
        
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
    public Double getDemand(String consumer) {
        int consumerIndex = consumers.indexOf(consumer);
        return demand.get(consumerIndex);
    }
    
    /**
     * Get the sum of the demand quantity of all consumers.
     * 
     * @return The total demand.
     */
    public Double getTotalDemand() {
        Double currentDemand, totalDemand = 0.0;
        
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
    public Map<String, Double> getAllDemand() {
        Map<String, Double> allDemand = new HashMap<>();
        Double currentDemand;
        
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
    public Double getCost(String producer, String consumer) {
        int producerIndex = producers.indexOf(producer);
        int consumerIndex = consumers.indexOf(consumer);
        return costTable.get(producerIndex).get(consumerIndex);
    }

}
