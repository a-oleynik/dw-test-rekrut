package dw.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Utils {
    // The method is used for scrollLeftUntilElementPresentV2.
    // Need to check if we can use only awaitility and remove this one
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void saveBytesAsFile(String path, byte[] fileBytes) {
        try (OutputStream out = new FileOutputStream(path)) {
            out.write(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDirectoryIfNotExist(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
