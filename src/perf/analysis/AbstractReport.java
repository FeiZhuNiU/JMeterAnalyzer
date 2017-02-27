package perf.analysis;

import org.apache.commons.csv.CSVRecord;
import perf.model.BasicCSV;
import perf.model.ReportCSV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/25.
 * yulin.jay@gmail.com
 */
public abstract class AbstractReport {
//    protected BasicCSV csv;
    protected List<CSVRecord> originData;
    protected List<String> reportHeaders;
    protected List<List<String>> reportData;
    protected ReportCSV report = null;

    public AbstractReport(BasicCSV csv) {
//        this.csv = csv;
        this.originData = csv.getRecords();
        reportHeaders = new ArrayList<>();
        reportData = new ArrayList<>();
    }

    public abstract ReportCSV getReport();

}
