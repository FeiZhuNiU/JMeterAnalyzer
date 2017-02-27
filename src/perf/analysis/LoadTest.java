package perf.analysis;

import perf.data.BasicCSV;
import perf.data.ReportCSV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/27.
 */
public class LoadTest {
    List<SynthesisReport> synthesisReports;

    public LoadTest() {
        synthesisReports = new ArrayList<>();
    }
    public void addSynthesisReport(SynthesisReport report){
        synthesisReports.add(report);
    }

    public ReportCSV generateReport() {
        for (SynthesisReport synthesisReport : synthesisReports) {

        }
        return null;
    }
}
