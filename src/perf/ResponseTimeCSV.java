package perf;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/24.
 */
public class ResponseTimeCSV extends BasicCSV {
    public ResponseTimeCSV(String dataFileName) {
        super(dataFileName);
    }

    public double getSuccessRate(List<CSVRecord> records) {
        int success = 0;
        for (CSVRecord record : records) {
            if (record.get(JMeterCSVHeader.SUCCESS.getKey()).equals("true")) {
                success++;
            }
        }
        return success / records.size();
    }

    public double getSuccessRate(JMeterCSVHeader header, String val) {
        List<CSVRecord> list = new ArrayList<>();
        records.forEach(record -> {
            if (record.get(header.getKey()).equals(val) && record.get(JMeterCSVHeader.SUCCESS.getKey()).equals("true"))
                list.add(record);
        });
        return getSuccessRate(list);
    }

    public double avgResponseTime(String label) {
        List<CSVRecord> records = getRecordsByHeader(JMeterCSVHeader.LABEL, label);
        return avg(records, JMeterCSVHeader.ELAPSED);
    }
}
