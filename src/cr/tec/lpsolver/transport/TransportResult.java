package cr.tec.lpsolver.transport;

import java.util.Arrays;
import static java.util.Arrays.stream;

/**
 *
 * @author José Andrés García Sáenz <jags9415@gmail.com>
 */
public class TransportResult {
    
    /**
     * The name of each consumer.
     */
    private final String[] consumers;
    
    /**
     * The name of each producer.
     */
    private final String[] producers;
    
    /**
     * The cost assignments.
     */
    private final double[][] assignments;
    
    /**
     * Creates a new TransportResult
     * 
     * @param consumers The consumers name.
     * @param producers The producers name.
     * @param assignments The costs assignments.
     */
    public TransportResult(String[] producers, String[] consumers, double[][] assignments) {
        this.consumers = consumers;
        this.producers = producers;
        this.assignments = assignments;
    }
    
    /**
     * Get the cost assigned from a producer to a consumer.
     * 
     * @param producer The producer.
     * @param consumer The consumer.
     * 
     * @return The assigned cost.
     */
    public double getAssignment(String producer, String consumer) {
        int consumerIndex = getConsumersIndex(consumer);
        int producerIndex = getProducersIndex(producer);
        
        if (consumerIndex > 0 && producerIndex > 0) {
            return assignments[producerIndex][consumerIndex];
        }
        
        return -1;
    }
    
    /**
     * Return the costs assignments.
     * 
     * @return The costs assignments.
     */
    public double[][] getAssignments() {
        return assignments;
    }

    /**
     * Returns the consumers names.
     * 
     * @return The consumers names.
     */
    public String[] getConsumers() {
        return consumers;
    }

    /**
     * Returns the producers names.
     * 
     * @return The producers names.
     */
    public String[] getProducers() {
        return producers;
    }

    /**
     * Obtains an index from the producers's vector.
     * 
     * @param producer the name of the producer to search for.
     * 
     * @return the index(or space) where the producer is stored. 
     */
    private int getProducersIndex(String producer) {
        return Arrays.binarySearch(producers, producer);
    }
    
    /**
     * Obtains an index from the consumers's vector.
     * 
     * @param consumer the name of the consumer to search for.
     * 
     * @return the index(or space) where the consumer is stored.
     */
    private int getConsumersIndex(String consumer) {
        return Arrays.binarySearch(consumers, consumer);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        stream(assignments).forEach(row -> sb.append(Arrays.toString(row)).append("\n"));
        return sb.toString();
    }
    
}
