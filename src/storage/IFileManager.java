package storage;

import java.io.FileReader;

public interface IFileManager {
    void write(String filePath, String data);
    FileReader read(String filePath);
}
