package perf;

import org.apache.commons.csv.CSVRecord;

import java.util.*;

/**
 * Created by Eric Yu on 2017/2/24.
 */
public class SimpleDataCSV extends BasicCSV {
    public SimpleDataCSV(String dataFileName) {
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
            if (record.get(header.getKey()).equals(val))
                list.add(record);
        });
        return getSuccessRate(list);
    }

    public double avgResponseTime(String labelVal) {
        List<CSVRecord> records = getRecordsByHeader(JMeterCSVHeader.LABEL, labelVal);
        return avg(records, JMeterCSVHeader.ELAPSED);
    }

    public Map<String, Double> getAllAvgResponseTime(){
        Map<String, Double> ret = new TreeMap<>();
        Map<String, List<CSVRecord>> sortedMap = new HashMap<>();
        for (CSVRecord record : records) {
            String label = record.get(JMeterCSVHeader.LABEL.getKey());
            sortedMap.computeIfAbsent(label, k -> new ArrayList<>());
            sortedMap.get(label).add(record);
        }
        sortedMap.forEach((label,list)->{
            ret.put(label, avg(list, JMeterCSVHeader.ELAPSED));
        });
        return ret;
    }
}
