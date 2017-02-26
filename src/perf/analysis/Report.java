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
    protected List<CSVRecord> data;
    protected List<String> headers;
    protected List<CSVRecord> reports;

    public Report(JMeterCSV csv) {
//        this.csv = csv;
        this.data = csv.getRecords();
        headers = new ArrayList<>();
        reports = new ArrayList<>();
    }

    public abstract void generateReport();

}
