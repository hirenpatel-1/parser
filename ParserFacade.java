package test.parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * This class is thread safe.
 */
public class ParserFacade {

    private static ParserFacade instance;

    public static ParserFacade getInstance() {
        if (instance == null) {
            instance = new ParserFacade();
        }
        return instance;
    }

    private File file;

    public synchronized void setFile(File fileInput) {
        file = fileInput;
    }

    public synchronized Optional<File> getFile() {
        if (file != null) {
            return Optional.of(file);
        } else {
            return null;
        }
    }

    public String getContent() throws IOException {
        @SuppressWarnings("resource")
		FileInputStream input = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = input.read()) > 0) output += (char) data;
        return output;
    }
    public String getContentWithoutUnicode() throws IOException {
        @SuppressWarnings("resource")
		FileInputStream inputStream = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = inputStream.read()) > 0) if (data < 0x80) {
            output += (char) data;
        }
        return output;
    }
    public void saveContent(String content) throws IOException {
        @SuppressWarnings("resource")
		FileOutputStream output = new FileOutputStream(file);
        for (int row = 0; row < content.length(); row += 1) {
            output.write(content.charAt(row));
        }
    }
}