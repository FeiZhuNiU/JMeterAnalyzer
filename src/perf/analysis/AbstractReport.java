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
    protected List<String> reportHeaders;
    protected List<List<String>> reportData;
    protected ReportCSV report = null;

    public AbstractReport() {
        reportHeaders = new ArrayList<>();
        reportData = new ArrayList<>();
    }

    public abstract ReportCSV getReport();

}
