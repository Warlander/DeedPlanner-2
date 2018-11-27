package pl.wurmonline.deedplanner.util;

import java.io.IOException;
import java.io.PrintStream;

public class DoublePrintStream extends PrintStream {

    private final PrintStream second;
    
    public DoublePrintStream(PrintStream first, PrintStream second) {
        super(first);
        this.second = second;
    }
    
    public void write(int b) {
        super.write(b);
        second.write(b);
    }
    public void write(byte[] b) throws IOException {
        super.write(b);
        second.write(b);
    }
    public void write(byte[] b, int off, int len) {
        super.write(b, off, len);
        second.write(b, off, len);
    }

}
