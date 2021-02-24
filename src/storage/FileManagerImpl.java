package storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManagerImpl implements IFileManager {
    @Override
    public void write(String filePath, String data) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileReader read(String filePath) {
        try {
            return new FileReader(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
