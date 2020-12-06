package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParserCSV {

    public static void main(String[] args) throws InterruptedException {
        List<ReaderCSV> readerCSVList = createReaders(args);
        while (calculateReadersFinished(readerCSVList) != readerCSVList.size()) {
        }
        List<WriterCSV> writerCSVList = createWriters(ReaderCSV.getContentByTitle());
        while (calculateWritersFinished(writerCSVList) != writerCSVList.size()) {
        }
    }

    private static List<ReaderCSV> createReaders(String[] fileNames) {
        List<ReaderCSV> readerCSVList = new ArrayList<>();
        for (String str : fileNames) {
            ReaderCSV newReaderCSV = new ReaderCSV(str);
            newReaderCSV.start();
            readerCSVList.add(newReaderCSV);
        }
        return readerCSVList;
    }

    private static List<WriterCSV> createWriters(Map<String, List<String>> contentByTitle) {
        List<WriterCSV> writerCSVList = new ArrayList<>();
        for (Map.Entry<String, List<String>> entrySet : contentByTitle.entrySet()) {
            WriterCSV newWriterCSV = new WriterCSV(entrySet.getKey(), entrySet.getValue());
            newWriterCSV.start();
            writerCSVList.add(newWriterCSV);
        }
        return writerCSVList;
    }

    private static int calculateReadersFinished(List<ReaderCSV> readers) throws InterruptedException {
        int finishedCount = 0;
        for (ReaderCSV readerCSV : readers) {
            if (!readerCSV.isFinished()) {
                readerCSV.join();
            } else finishedCount++;
        }
        return finishedCount;
    }

    private static int calculateWritersFinished(List<WriterCSV> writers) throws InterruptedException {
        int finishedCount = 0;
        for (WriterCSV writerCSV : writers) {
            if (!writerCSV.isFinished()) {
                writerCSV.join();
            } else finishedCount++;
        }
        return finishedCount;
    }
}
