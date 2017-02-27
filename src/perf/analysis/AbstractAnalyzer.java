package perf.analysis;

import org.apache.commons.csv.CSVRecord;
import perf.data.BasicCSV;
import perf.data.ReportCSV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/25.
 * yulin.jay@gmail.com
 */
public abstract class AbstractAnalyzer {
//    protected BasicCSV csv;
    protected List<CSVRecord> originData;
    protected List<String> reportHeaders;
    protected List<List<String>> reportData;

    public AbstractAnalyzer(BasicCSV csv) {
//        this.csv = csv;
        this.originData = csv.getRecords();
        reportHeaders = new ArrayList<>();
        reportData = new ArrayList<>();
    }

    public abstract ReportCSV generateReport();

}
