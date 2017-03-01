package perf.analysis;

import perf.model.ReportCSV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/25.
 * yulin.jay@gmail.com
 */
public abstract class AbstractReport {
    protected List<String> reportHeaders;
    protected ReportCSV report = null;

    public AbstractReport() {
        reportHeaders = new ArrayList<>();
    }

    public abstract ReportCSV getReport();

}