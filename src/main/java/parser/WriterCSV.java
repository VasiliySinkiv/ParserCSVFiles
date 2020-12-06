package parser;

import java.io.*;
import java.util.List;

public class WriterCSV extends Thread {
    private boolean isFinished;
    private String fileName;
    private List<String> content;

    public WriterCSV(String fileName, List<String> content) {
        this.fileName = fileName;
        this.content = content;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void run() {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".csv"), "cp1251"))) {
            for (String str : content) {
                bw.write(str + ";");
            }
            isFinished = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
