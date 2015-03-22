package cr.tec.lpsolver.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for reading lines from an input.
 * 
 * @author jose
 */
public class LineReader {
    
    private final BufferedReader br;
    
    public LineReader(Reader reader) {
        this.br = new BufferedReader(reader);
    }
    
    /**
     * Read a single line.
     * 
     * @return The text in the current line.
     * 
     * @throws IOException If an I/O error occurs.
     */
    public String readLine() throws IOException {
        return br.readLine();
    }
    
    /**
     * Read all the lines.
     * 
     * @return An array containing the text in all lines.
     * 
     * @throws IOException If an I/O error occurs.
     */
    public String[] readLines() throws IOException {
        List<String> buffer = new ArrayList<>();
        String line;
        
        while ((line = br.readLine()) != null) {
            buffer.add(line);
        }
        
        String[] bufferArray = new String[buffer.size()];
        bufferArray = buffer.toArray(bufferArray);
        
        return bufferArray;
    }
    
    /**
     * Read a number of lines.
     * 
     * @param lines The number of lines to read.
     * 
     * @return An array containing the lines read.
     * 
     * @throws IOException 
     */
    public String[] readLines(int lines) throws IOException {
        String[] buffer = new String[lines];
        for (int i = 0; i < lines; i++) {
            buffer[i] = br.readLine();
            if (buffer[i] == null) break;
        }
        return buffer;
    }
    
}
