package perf.analysis;

import org.apache.commons.csv.CSVRecord;
import perf.data.ReportCSV;
import perf.data.SimpleDataCSV;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Eric Yu on 2017/2/24.
 */
public class SynthesisReport extends AbstractAnalyzer {

    private Set<String> labelSet;

    public SynthesisReport(SimpleDataCSV csv) {
        super(csv);
        for (Header header : Header.values()) {
            reportHeaders.add(header.toString());
        }
        labelSet = new TreeSet<>();
        List<CSVRecord> records = csv.getRecords();
        for (CSVRecord record : records) {
            labelSet.add(record.get(SimpleDataCSV.Header.LABEL));
        }
    }

    /**
     * get start and end timestamp automatically according to given threadNum
     * needs to be optimized in algorithm
     * @param threadNum
     */
    public void autoFilter(int threadNum) {
        long startTimeStamp = 0, endTimeStamp = 0;
        boolean startPointAlreadyFound = false;
        long firstTimeStamp = Long.parseLong(originData.get(0).get(SimpleDataCSV.Header.TIMESTAMP));
        for (CSVRecord data : originData) {
            int cur_threads = Integer.parseInt(data.get(SimpleDataCSV.Header.ALL_THREADS));
            long cur_timeStamp = Long.parseLong(data.get(SimpleDataCSV.Header.TIMESTAMP));
            if (!startPointAlreadyFound) {
                if ( cur_threads == threadNum ) {
                    startTimeStamp = cur_timeStamp;
                    startPointAlreadyFound = true;
                }
            }
            else{
                if (cur_threads < threadNum) {
                    endTimeStamp = cur_timeStamp;
                    break;
                }
            }
        }
        assert startTimeStamp != 0;
        assert endTimeStamp != 0;

        long startOffsetInSecond = (startTimeStamp - firstTimeStamp) / 1000 + 100;
        long endOffsetInSecond = (endTimeStamp - firstTimeStamp) / 1000 - 100;
        filterByTime(startOffsetInSecond, endOffsetInSecond);
    }


    public void filterByTime(long startOffsetInSecond, long endOffsetInSecond) {
        long startTimeStamp = Long.parseLong(originData.get(0).get(SimpleDataCSV.Header.TIMESTAMP));
        long startOffset = startTimeStamp + startOffsetInSecond * 1000;
        long endOffset = startTimeStamp + endOffsetInSecond * 1000;
        int l = 0, r = originData.size();
        int start, end;
        while (r > l + 1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(originData.get(mid).get(SimpleDataCSV.Header.TIMESTAMP));
            if (curTimeStamp < startOffset) {
                l = mid;
            } else {
                r = mid;
            }
        }
        start = r;
        l = 0;
        r = originData.size();
        while (r > l + 1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(originData.get(mid).get(SimpleDataCSV.Header.TIMESTAMP));
            if (curTimeStamp < endOffset) {
                l = mid;
            } else {
                r = mid;
            }
        }
        end = l;
        originData = originData.subList(start, end);
    }

    private boolean isSuccess(CSVRecord record) {
        return "true".equals(record.get(SimpleDataCSV.Header.SUCCESS));
    }

    private boolean isSpecifiedLabel(CSVRecord record, String label) {
        return label.equals(record.get(SimpleDataCSV.Header.LABEL));
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
     * return a list of originData according to its label
     *
     * @param label
     * @return
     */
    private List<CSVRecord> getRecordsByLabel(String label, boolean successOnly) {
        List<CSVRecord> ret = new ArrayList<>();
        for (CSVRecord record : originData) {
            if (isSpecifiedLabel(record, label)) {
                if (successOnly && !isSuccess(record))
                    continue;
                ret.add(record);
            }
        }
        return ret;
    }

    /**
     * @param label
     * @param header
     * @param successOnly
     * @return
     */
    private List<String> getValueListByLabel(String label, SimpleDataCSV.Header header, boolean successOnly) {
        List<String> ret = new ArrayList<>();
        List<CSVRecord> tmpRecords = getRecordsByLabel(label, successOnly);
        for (CSVRecord tmpRecord : tmpRecords) {
            ret.add(tmpRecord.get(header));
        }
        return ret;
    }

    public int getSampleCount(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, false);
        return recordList.size();
    }

    public double getAvgResponseTime(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        return CommonUtils.avg(recordList, SimpleDataCSV.Header.ELAPSED.toString());
    }

    public int getMinResponseTime(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        return (int) CommonUtils.getMinVal(recordList, SimpleDataCSV.Header.ELAPSED.toString());
    }

    public int getMaxResponseTime(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        return (int) CommonUtils.getMaxVal(recordList, SimpleDataCSV.Header.ELAPSED.toString());
    }

    public int getResponseTime90PercentLine(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        return (int) CommonUtils.getSpecifiedPositionVal(recordList, SimpleDataCSV.Header.ELAPSED.toString(), 90);
    }

    public double getStandartDeviation(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        return CommonUtils.getStandardDeviation(recordList, SimpleDataCSV.Header.ELAPSED.toString());
    }

    public double getErrorRate(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, false);
        return 1 - getSuccessRate(recordList);
    }

    /**
     * return hit/sec
     * @param label
     * @return
     */
    public double getThroughput(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        long timeStart = Long.parseLong(recordList.get(0).get(SimpleDataCSV.Header.TIMESTAMP));
        long timeEnd = Long.parseLong(recordList.get(recordList.size() - 1).get(SimpleDataCSV.Header.TIMESTAMP));
        double secondConsumed = (timeEnd - timeStart) / 1000;
        return (double)recordList.size() / secondConsumed;
    }


    @Deprecated
    public Map<String, Double> getAllAvgResponseTime() {
        Map<String, Double> ret = new TreeMap<>();
        Map<String, List<CSVRecord>> sortedMap = new HashMap<>();
        for (CSVRecord record : originData) {
            String label = record.get(SimpleDataCSV.Header.LABEL);
            sortedMap.computeIfAbsent(label, k -> new ArrayList<>());
            sortedMap.get(label).add(record);
        }
        sortedMap.forEach((label, list) -> {
            ret.put(label, CommonUtils.avg(list, SimpleDataCSV.Header.ELAPSED.toString()));
        });
        return ret;
    }


    @Override
    public ReportCSV generateReport() {
        DecimalFormat df =new DecimalFormat("#.##");
        for (String label : labelSet) {
            List<String> curLabelReport = new ArrayList<>();
            String sample = String.valueOf(getSampleCount(label));
            String average = df.format(getAvgResponseTime(label)/1000.0);
            String min = df.format(getMinResponseTime(label)/1000.0);
            String max = df.format(getMaxResponseTime(label)/1000.0);
            String line90 = df.format(getResponseTime90PercentLine(label)/1000.0);
            String stdDev = df.format(getStandartDeviation(label));
            String error = df.format(getErrorRate(label));
            String throughput = df.format(getThroughput(label));

            curLabelReport.add(label);
            curLabelReport.add(sample);
            curLabelReport.add(average);
            curLabelReport.add(min);
            curLabelReport.add(max);
            curLabelReport.add(line90);
            curLabelReport.add(stdDev);
            curLabelReport.add(error);
            curLabelReport.add(throughput);
            reportData.add(curLabelReport);
        }
        return new ReportCSV(reportHeaders, reportData);

    }

    public enum Header {
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
