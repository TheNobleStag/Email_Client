package Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyObjectOutputStream extends ObjectOutputStream {

    protected MyObjectOutputStream() throws IOException, SecurityException {
        super();
    }
    MyObjectOutputStream(OutputStream o) throws IOException{
        super(o);
    }
    public void writeStreamHeader() throws IOException{
        return;
    }
}
