package perf.analyze;

import org.apache.commons.csv.CSVRecord;
import perf.data.BasicCSV;
import perf.data.SimpleDataHeader;

import java.util.*;

/**
 * Created by Eric Yu on 2017/2/24.
 */
public class SynthesisReport extends BasicAnalyzer{


    public SynthesisReport(BasicCSV csv) {
        super(csv);
    }

    public void filterByTime(long startSecond, long endSecond) {
        long startTimeStamp = Long.parseLong(records.get(0).get(SimpleDataHeader.TIMESTAMP.getKey()));
        long startOffset = startTimeStamp + startSecond * 1000;
        long endOffset = startTimeStamp + endSecond * 1000;
        int l = 0, r = records.size();
        int start, end;
        while (r > l + 1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(records.get(mid).get(SimpleDataHeader.TIMESTAMP.getKey()));
            if (curTimeStamp < startOffset) {
                l = mid;
            } else {
                r = mid;
            }
        }
        start = r;
        l = 0;
        r = records.size();
        while (r > l + 1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(records.get(mid).get(SimpleDataHeader.TIMESTAMP.getKey()));
            if (curTimeStamp < endOffset) {
                l = mid;
            } else {
                r = mid;
            }
        }
        end = l;
        records = records.subList(start, end);
    }

    private double getSuccessRate(List<CSVRecord> records) {
        int success = 0;
        for (CSVRecord record : records) {
            if (record.get(SimpleDataHeader.SUCCESS.getKey()).equals("true")) {
                success++;
            }
        }
        return success / records.size();
    }

    public double getSuccessRate(SimpleDataHeader header, String val) {
        List<CSVRecord> list = new ArrayList<>();
        records.forEach(record -> {
            if (record.get(header.getKey()).equals(val))
                list.add(record);
        });
        return getSuccessRate(list);
    }

    public double avgResponseTime(String label) {
        List<CSVRecord> records = csv.getRecordsByHeader(SimpleDataHeader.LABEL, label);
        return avg(records, SimpleDataHeader.ELAPSED);
    }

    public Map<String, Double> getAllAvgResponseTime() {
        Map<String, Double> ret = new TreeMap<>();
        Map<String, List<CSVRecord>> sortedMap = new HashMap<>();
        for (CSVRecord record : records) {
            String label = record.get(SimpleDataHeader.LABEL.getKey());
            sortedMap.computeIfAbsent(label, k -> new ArrayList<>());
            sortedMap.get(label).add(record);
        }
        sortedMap.forEach((label, list) -> {
            ret.put(label, avg(list, SimpleDataHeader.ELAPSED));
        });
        return ret;
    }
}
