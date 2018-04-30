package imat.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *  Utility class for reading and writing files
 */

public final class FileUtils {

    /**
     *  Reads all text from a file, including newlines, returning it as a single string
     * @param path Path to a text file
     * @return The full file content (ascii encoding)
     */
    public static String readAllTextFromFile(String path) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
