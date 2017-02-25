package perf.data;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/25.
 * yulin.jay@gmail.com
 */
public class CSVUtils {
    /**
     * return a list of records whose @param header equals @param value
     *
     * @param header
     * @param value
     * @return
     */
    public static List<CSVRecord> getRecordsByHeader(List<CSVRecord> records, String header, String value) {
        List<CSVRecord> ret = new ArrayList<>();
        for (CSVRecord record : records) {
            if (record.get(header).equals(value)) {
                ret.add(record);
            }
        }
        return ret;
    }

    public static List<CSVRecord> getRecordsByHeader(List<CSVRecord> records, CommonCSVHeader header, String value) {
        return getRecordsByHeader(records, header.getKey(), value);
    }
}
