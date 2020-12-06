package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReaderCSV extends Thread {
    private static Map<String, List<String>> contentByTitle = new HashMap<>();
    private boolean isFinished;
    private String fileName;

    public ReaderCSV(String fileName) {
        this.fileName = fileName;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String oneString = bufferedReader.readLine();
            String[] fileNames = oneString.split(";");
            for (String name : fileNames) {
                synchronized (contentByTitle) {
                    if (!contentByTitle.containsKey(name)) {
                        contentByTitle.put(name, new ArrayList<>());
                    }
                }
            }
            while ((oneString = bufferedReader.readLine()) != null) {
                String[] contentString = oneString.split(";");
                for (int i = 0; i < contentString.length; i++) {
                    if (!contentByTitle.get(fileNames[i]).contains(contentString[i])) {
                        contentByTitle.get(fileNames[i]).add(contentString[i]);
                    }
                }
            }
            isFinished = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Map<String, List<String>> getContentByTitle() {
        return contentByTitle;
    }
}
