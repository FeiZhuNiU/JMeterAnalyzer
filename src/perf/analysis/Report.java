package perf.analysis;

import org.apache.commons.csv.CSVRecord;
import perf.data.JMeterCSV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/25.
 * yulin.jay@gmail.com
 */
public abstract class Report {
//    protected JMeterCSV csv;
    protected List<CSVRecord> originData;
    protected List<String> reportHeaders;
    protected List<List<String>> reportData;

    public Report(JMeterCSV csv) {
//        this.csv = csv;
        this.originData = csv.getRecords();
        reportHeaders = new ArrayList<>();
        reportData = new ArrayList<>();
    }

    public abstract void generateReport(String dstFileName, String sheetName);

}
