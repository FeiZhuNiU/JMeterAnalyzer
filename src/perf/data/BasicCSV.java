package perf.data;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

/**
 * Created by Eric Yu on 2017/2/25.
 * yulin.jay@gmail.com
 */
public abstract class BasicCSV {
    
    protected List<CSVRecord> records;

    public List<CSVRecord> getRecords() {
        return records;
    }


}
