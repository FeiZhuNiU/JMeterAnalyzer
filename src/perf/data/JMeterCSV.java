package perf.data;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/25.
 * yulin.jay@gmail.com
 */
public abstract class JMeterCSV {
    
    protected List<CSVRecord> records;
    protected String fileName;
    protected CSVParser parser;

    public List<CSVRecord> getRecords() {
        return records;
    }

    public CSVParser getParser() {
        return parser;
    }
    public String getFileName() {
        return fileName;
    }
    public JMeterCSV(String dataFileName) {
        this.fileName = dataFileName;
    }
    public void close() {
        try {
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
