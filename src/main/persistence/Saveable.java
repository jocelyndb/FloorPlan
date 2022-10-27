package persistence;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;

// Represents a file that can be saved
public abstract class Saveable implements Writable {
    private static final int TAB = 4;
    private PrintWriter writer;

    // EFFECTS: saves a Saveable object that shares the same path as another file as JSON
    //          throws FileNotFound if a file of the same name cannot be found
    public void saveOver(String path) throws FileNotFoundException {
        this.toJson();
        openWriter(path);
        writeJson();
        closeWriter();
    }

    // EFFECTS: saves a previously unsaved Saveable object as JSON
    //          throws FileAlreadyExistsException if a file of the same name already exists
    public void saveNew(String path) throws FileAlreadyExistsException, IOException {
        File file = new File(path);
        boolean createdNewFile;
        createdNewFile = file.createNewFile();
        if (createdNewFile) {
            saveOver(path);
        } else {
            throw new FileAlreadyExistsException(path);
        }
    }

    private void writeJson() {
        JSONObject json = this.toJson();
        writer.print(json);
    }

    // EFFECTS: opens writer at the specified path
    //          throws FileNotFoundException if destination file cannot
    //          be opened for writing
    private void openWriter(String path) throws FileNotFoundException {
        writer = new PrintWriter(new File(path));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    private void closeWriter() {
        writer.close();
    }
}
