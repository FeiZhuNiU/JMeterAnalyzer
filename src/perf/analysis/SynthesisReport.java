package perf.analysis;

import org.apache.commons.csv.CSVRecord;
import perf.data.SimpleDataCSV;

import java.util.*;

/**
 * Created by Eric Yu on 2017/2/24.
 */
public class SynthesisReport extends Report {

    public SynthesisReport(SimpleDataCSV csv) {
        super(csv);
        for (Header header : Header.values()) {
            headers.add(header.toString());
        }
    }

    public void filterByTime(long startSecond, long endSecond) {
        long startTimeStamp = Long.parseLong(data.get(0).get(SimpleDataCSV.Header.TIMESTAMP));
        long startOffset = startTimeStamp + startSecond * 1000;
        long endOffset = startTimeStamp + endSecond * 1000;
        int l = 0, r = data.size();
        int start, end;
        while (r > l + 1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(data.get(mid).get(SimpleDataCSV.Header.TIMESTAMP));
            if (curTimeStamp < startOffset) {
                l = mid;
            } else {
                r = mid;
            }
        }
        start = r;
        l = 0;
        r = data.size();
        while (r > l + 1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(data.get(mid).get(SimpleDataCSV.Header.TIMESTAMP));
            if (curTimeStamp < endOffset) {
                l = mid;
            } else {
                r = mid;
            }
        }
        end = l;
        data = data.subList(start, end);
    }

    private double getSuccessRate(List<CSVRecord> records) {
        int success = 0;
        for (CSVRecord record : records) {
            if (isSuccess(record)) {
                success++;
            }
        }
        return success / records.size();
    }

    /**
     * return a list of data according to its label
     *
     * @param label
     * @return
     */
    public List<CSVRecord> getRecordsByLabel(String label) {
        List<CSVRecord> ret = new ArrayList<>();
        for (CSVRecord record : data) {
            if (isSpecifiedLabel(record, label)) {
                ret.add(record);
            }
        }
        return ret;
    }

    public double avgResponseTime(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label);
        return CommonAnalysisUtils.avg(recordList, SimpleDataCSV.Header.ELAPSED);
    }

    public Map<String, Double> getAllAvgResponseTime() {
        Map<String, Double> ret = new TreeMap<>();
        Map<String, List<CSVRecord>> sortedMap = new HashMap<>();
        for (CSVRecord record : data) {
            String label = record.get(SimpleDataCSV.Header.LABEL);
            sortedMap.computeIfAbsent(label, k -> new ArrayList<>());
            sortedMap.get(label).add(record);
        }
        sortedMap.forEach((label, list) -> {
            ret.put(label, CommonAnalysisUtils.avg(list, SimpleDataCSV.Header.ELAPSED));
        });
        return ret;
    }

    /**
     * successed
     * @param label
     * @param header
     * @return
     */
    private List<String> getValueListOfSpecifiedLabel(String label, SimpleDataCSV.Header header){
        List<String> ret = new ArrayList<>();
        for (CSVRecord record : data) {
            if(isSpecifiedLabel(record, label) && isSuccess(record)){
                ret.add(record.get(header));
            }
        }
        return ret;
    }

    private boolean isSuccess(CSVRecord record){
        return "true".equals(record.get(SimpleDataCSV.Header.SUCCESS));
    }
    private boolean isSpecifiedLabel(CSVRecord record, String label){
        return label.equals(record.get(SimpleDataCSV.Header.LABEL));
    }

    @Override
    public void generateReport() {

    }

    public enum Header{
        LABEL("Label"),
        SAMPLES("#Samples"),
        AVERAGE("Average"),
        MIN("Min"),
        MAX("Max"),
        LINE90PERCENT("90% Line"),
        STANDARD_DEVIATION("Std. Dev."),
        ERROR_PERCENT("Error %"),
        THROUGHPUT("Throughput"),
        NETIO("KB/sec"),
        RESPONSE_SIZE("Avg. Bytes");

        private String val;

        Header(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val;
        }
    }
}
