package cr.tec.lpsolver.io;

import cr.tec.lpsolver.Constraint;
import cr.tec.lpsolver.Linear;
import cr.tec.lpsolver.Problem;
import cr.tec.lpsolver.ProblemType;
import cr.tec.lpsolver.Relationship;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * A class for reading a Linear Programming File.
 * 
 * @author jose
 */
public class PLReader {

    private final String[] variables;
    
    /**
     * Construct a {@code PLReader}.
     */
    public PLReader() {
        this.variables = new String[] {"x", "y"};
    }
    
    /**
     * Read a problem from a File.
     * 
     * @param file The file to read.
     * @return The problem.
     * @throws FileNotFoundException If the file don't exist.
     * @throws IOException If an I/O error occurs.
     */
    public Problem read(File file) throws FileNotFoundException, IOException {
        return read(new FileReader(file));
    }
    
    /**
     * Read a problem from a String.
     * 
     * @param content The string to read.
     * @return The problem.
     * @throws IOException If an I/O error occurs.
     */
    public Problem read(String content) throws IOException {
        return read(new StringReader(content));
    }
    
    /**
     * Read a problem from a Reader.
     * 
     * @param reader The reader.
     * @return The problem.
     * @throws IOException If an I/O error occurs.
     */
    public Problem read(Reader reader) throws IOException {
        LineReader lineReader = new LineReader(reader);
        String[] buffer = lineReader.readLines();
        
        if (!hasValidLength(buffer)) {
            throw new IOException("Invalid format. Inconsistent length.");
        }
        
        Problem problem = new Problem();
        
        ProblemType type = readProblemType(buffer);
        Linear function = readObjectiveFunction(buffer);
        Constraint[] constraints = readConstraints(buffer);
        
        problem.setProblemType(type);
        problem.setObjetiveFunction(function);
        problem.addConstraints(constraints);
        
        return problem;
    }
    
    /**
     * Check if a buffer have a valid length for a PL File.
     * Minimum length is 8 lines.
     *   - Problem type (1 line)
     *   - Objective function (2 lines)
     *   - Constraint (5 lines)
     * If the buffer have more than 8 lines, the length of the constraints must be divisible by 5.
     * 
     * @param buffer The buffer.
     * @return True if the buffer has valid length.
     */
    private boolean hasValidLength(String[] buffer) {
        return buffer.length >= 8 && (buffer.length - 3) % 5 == 0;
    }
    
    /**
     * Get the problem type from a string buffer.
     * The problem type is located in the first index of the buffer.
     * 0 : MIN
     * 1 : MAX
     * 
     * @param buffer The buffer.
     * @return The problem type.
     * @throws IOException If any value different than 0 or 1 is located in the first index of the buffer.
     */
    private ProblemType readProblemType(String[] buffer) throws IOException {
        String character = null;
        try {
            character = buffer[0];
            int code = Integer.parseInt(character);
            switch (code) {
                case 0:
                    return ProblemType.MIN;
                case 1:
                    return ProblemType.MAX;
                default:
                    throw new IOException("Invalid problem type code: " + code);
            }
        }
        catch (NumberFormatException ex) {
            throw new IOException("Invalid format. Expected (int) Got (" + character + ")");
        }
    }
    
    /**
     * Get the objective function from a string buffer.
     * The Objective Function is located in index 1 - 2.
     * Index 1 : Coefficient X.
     * Index 2 : Coefficient Y.
     * 
     * @param buffer The buffer.
     * @return The Objective Function.
     * @throws IOException If the values in index 1 - 2 cannot be parsed as doubles.
     */
    private Linear readObjectiveFunction(String[] buffer) throws IOException {
        Linear linear = new Linear();
        String character = null;
        double value;
        
        try {
            for (int i = 1; i < 3; i++) {
                character = buffer[i];
                value = Double.parseDouble(character);
                linear.add(value, variables[i-1]);
            }
        }
        catch (NumberFormatException ex) {
            throw new IOException("Invalid format. Expected (double) Got (" + character + ")");
        }
        
        return linear;
    }
    
    /**
     * Parse a String as a {@link Relationship}.
     * <  Relationship.LEQ
     * >  Relationship.GQE
     * 
     * @param character The string.
     * @return The relationship.
     * @throws IOException If the string cannot be parsed as relationship.
     */
    private Relationship parseRelationship(String character) throws IOException {
        switch (character) {
            case "<":
                return Relationship.LEQ;
            case ">":
                return Relationship.GEQ;
            default:
                throw new IOException("Invalid format. Expected (> <) Got (" + character + ")");
        }
    }
    
    /**
     * Get the constraints from a string buffer.
     * A constraint have 5 spaces in a buffer.
     * Index 0 : Coefficient X.
     * Index 1 : Coefficient Y.
     * Index 2 : Relationship.
     * Index 3 : =
     * Index 4 : Constant.
     * 
     * @param buffer The buffer.
     * @return An array of constraints.
     * @throws IOException If an invalid format is encountered.
     */
    private Constraint[] readConstraints(String[] buffer) throws IOException {
        Constraint[] constraints = new Constraint[(buffer.length - 3) / 5];
        Relationship relationship = null;
        String character = null;
        Linear linear;
        double value, constant = 0;
        
        try {
            for (int i = 3; i < buffer.length; i += 5) {
                linear = new Linear();
                for (int j = 0; j < 5; j++) {
                    character = buffer[i+j];
                    switch (j) {
                        case 0:
                        case 1:
                            value = Double.parseDouble(character);
                            if (value != 0)
                                linear.add(value, variables[j]);
                            break;
                        case 2:
                            relationship = parseRelationship(character);
                            break;
                        case 3:
                            if (!character.equals("="))
                                throw new IOException("Invalid format. Expected (=) Got (" + character + ")");
                            break;
                        case 4:
                            constant = Double.parseDouble(character);
                            break;
                    }
                }
                constraints[(i-3)/5] = new Constraint(linear, relationship, constant);
            }
        }
        catch (NumberFormatException ex) {
            throw new IOException("Invalid format. Expected (double) Got (" + character + ")");
        }
        
        return constraints;
    }
    
}
