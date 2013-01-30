package net.kkolyan.json2.rpc;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author nplekhanov
 */
public interface Shell {
    void execute(Reader in, Appendable out) throws IOException;
}
