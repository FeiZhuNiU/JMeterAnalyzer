package perf.analysis;

import org.apache.commons.csv.CSVRecord;
import perf.model.CSVHeader;
import perf.model.ReportCSV;
import perf.model.SimpleDataWriterCSV;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Eric Yu on 2017/2/24.
 */
public class SynthesisReport extends AbstractReport {

    protected SimpleDataWriterCSV csv = null;
    protected List<CSVRecord> filteredData;
    protected Set<String> labelSet;
    protected int userNum = 0;
    protected long filteredStartTimeStamp = 0;
    protected long filteredEndTimeStamp = 0;


    public SimpleDataWriterCSV getCsv() {
        return csv;
    }
    public Set<String> getLabelSet() {
        return labelSet;
    }

    public int getUserNum() {
        return userNum;
    }

    public List<CSVRecord> getFilteredData() {
        return filteredData;
    }

    public SynthesisReport(SimpleDataWriterCSV csv, boolean useAutoFilter) {
        super();
        this.csv = csv;
//        this.originData = csv.getRecords();
        for (Header header : Header.values()) {
            reportHeaders.add(header.toString());
        }
        labelSet = new TreeSet<>();
        List<CSVRecord> records = csv.getRecords();
        for (CSVRecord record : records) {
            labelSet.add(record.get(CSVHeader.LABEL));
        }
        if(useAutoFilter) {
            userNum = Integer.parseInt(csv.getFileName().split("\\.")[0]);
            autoFilter();
        }
        else {
            filteredStartTimeStamp = Long.parseLong(csv.getRecords().get(0).get(CSVHeader.TIMESTAMP));
            filteredEndTimeStamp = Long.parseLong(csv.getRecords().get(csv.getRecords().size()-1).get(CSVHeader.TIMESTAMP));
            this.filteredData = csv.getRecords();
        }
    }

    /**
     * get start and end timestamp automatically according to given threadNum,
     * the file name should also be the threadNum
     *
     * needs to be optimized in algorithm
     */
    public void autoFilter() {
        boolean startPointAlreadyFound = false;
        long firstTimeStamp = Long.parseLong(csv.getRecords().get(0).get(CSVHeader.TIMESTAMP));
        for (CSVRecord data : csv.getRecords()) {
            int cur_threads = Integer.parseInt(data.get(CSVHeader.ALL_THREADS));
            long cur_timeStamp = Long.parseLong(data.get(CSVHeader.TIMESTAMP));
            if (!startPointAlreadyFound) {
                if (cur_threads == userNum) {
                    filteredStartTimeStamp = cur_timeStamp;
                    startPointAlreadyFound = true;
                }
            } else {
                if (cur_threads < userNum) {
                    filteredEndTimeStamp = cur_timeStamp;
                    break;
                }
            }
        }
        assert filteredStartTimeStamp != 0;
        assert filteredEndTimeStamp != 0;

        long timeElapsed = filteredEndTimeStamp - filteredStartTimeStamp;
        this.filteredStartTimeStamp = filteredStartTimeStamp + timeElapsed / 10;
        this.filteredEndTimeStamp = filteredEndTimeStamp - timeElapsed / 10;
        filterByTime((filteredStartTimeStamp - firstTimeStamp) / 1000, (filteredEndTimeStamp - firstTimeStamp)/1000);

    }

    public void filterByTime(long startOffsetInSecond, long endOffsetInSecond) {
        long firstTimeStamp = Long.parseLong(csv.getRecords().get(0).get(CSVHeader.TIMESTAMP));
        this.filteredStartTimeStamp = firstTimeStamp + startOffsetInSecond * 1000;
        this.filteredEndTimeStamp = firstTimeStamp + endOffsetInSecond * 1000;
        int l = 0, r = csv.getRecords().size();
        int start, end;
        while (r > l + 1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(csv.getRecords().get(mid).get(CSVHeader.TIMESTAMP));
            if (curTimeStamp < filteredStartTimeStamp) {
                l = mid;
            } else {
                r = mid;
            }
        }
        start = r;
        l = 0;
        r = csv.getRecords().size();
        while (r > l + 1) {
            int mid = l + (r - l) / 2;
            long curTimeStamp = Long.parseLong(csv.getRecords().get(mid).get(CSVHeader.TIMESTAMP));
            if (curTimeStamp < filteredEndTimeStamp) {
                l = mid;
            } else {
                r = mid;
            }
        }
        end = l;
        filteredData = csv.getRecords().subList(start, end);
    }

    private boolean isSuccess(CSVRecord record) {
        return "true".equals(record.get(CSVHeader.SUCCESS));
    }

    private boolean isSpecifiedLabel(CSVRecord record, String label) {
        return label.equals(record.get(CSVHeader.LABEL));
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
        for (CSVRecord record : filteredData) {
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
    private List<String> getValueListByLabel(String label, CSVHeader header, boolean successOnly) {
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
        return CommonUtils.avg(recordList, CSVHeader.ELAPSED.toString());
    }

    public int getMinResponseTime(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        return (int) CommonUtils.getMinVal(recordList, CSVHeader.ELAPSED.toString());
    }

    public int getMaxResponseTime(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        return (int) CommonUtils.getMaxVal(recordList, CSVHeader.ELAPSED.toString());
    }

    public int getResponseTime90PercentLine(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        return (int) CommonUtils.getSpecifiedPositionVal(recordList, CSVHeader.ELAPSED.toString(), 90);
    }

    public double getStandardDeviation(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        return CommonUtils.getStandardDeviation(recordList, CSVHeader.ELAPSED.toString());
    }

    public double getErrorRate(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, false);
        return 1 - getSuccessRate(recordList);
    }

    /**
     * return hit/sec
     *
     * @param label
     * @return
     */
    public double getThroughput(String label) {
        List<CSVRecord> recordList = getRecordsByLabel(label, true);
        long timeStart = Long.parseLong(recordList.get(0).get(CSVHeader.TIMESTAMP));
        long timeEnd = Long.parseLong(recordList.get(recordList.size() - 1).get(CSVHeader.TIMESTAMP));
        double secondConsumed = (timeEnd - timeStart) / 1000.0;
        return (double) recordList.size() / secondConsumed;
    }


    @Deprecated
    public Map<String, Double> getAllAvgResponseTime() {
        Map<String, Double> ret = new TreeMap<>();
        Map<String, List<CSVRecord>> sortedMap = new HashMap<>();
        for (CSVRecord record : filteredData) {
            String label = record.get(CSVHeader.LABEL);
            sortedMap.computeIfAbsent(label, k -> new ArrayList<>());
            sortedMap.get(label).add(record);
        }
        sortedMap.forEach((label, list) -> {
            ret.put(label, CommonUtils.avg(list, CSVHeader.ELAPSED.toString()));
        });
        return ret;
    }


    @Override
    public ReportCSV getReport() {
        List<List<String>> reportData = new ArrayList<>();
        if (report == null) {
            DecimalFormat df = new DecimalFormat("#.##");
            for (String label : labelSet) {
                System.out.println(label);
                List<String> curLabelReport = new ArrayList<>();
                String sample = String.valueOf(getSampleCount(label));
                String average = df.format(getAvgResponseTime(label) / 1000.0);
                String min = df.format(getMinResponseTime(label) / 1000.0);
                String max = df.format(getMaxResponseTime(label) / 1000.0);
                String line90 = df.format(getResponseTime90PercentLine(label) / 1000.0);
                String stdDev = df.format(getStandardDeviation(label));
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
            report = new ReportCSV(reportHeaders, reportData);
        }
        return report;

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
