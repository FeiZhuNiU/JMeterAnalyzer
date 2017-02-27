package perf.analysis;

import perf.analysis.SynthesisReport;
import perf.model.ReportCSV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/27.
 */
public class LoadTestReport {
    List<SynthesisReport> synthesisReports;
    List<String> header;
    List<List<String>> rows;
    ReportCSV report = null;

    public LoadTestReport() {
        synthesisReports = new ArrayList<>();
        header = new ArrayList<>();
        rows = new ArrayList<>();
    }
    public void addSynthesisReport(SynthesisReport report){
        synthesisReports.add(report);
    }

    public ReportCSV getReport() {
        if(report == null) {
            header.add("Transaction");
            int transactionNum = synthesisReports.get(0).getLabelSet().size();
            List<String> labelList = new ArrayList<>(synthesisReports.get(0).getLabelSet());
            for (int i = 0; i < transactionNum; i++) {
                rows.add(new ArrayList<>());
                rows.get(i).add(labelList.get(i));
            }

            for (SynthesisReport synthesisReport : synthesisReports) {
                header.add(String.valueOf(synthesisReport.getUserNum()));
                ReportCSV report = synthesisReport.getReport();
                List<List<String>> curSynReportData = report.getData();
                for (List<String> singleRowData : curSynReportData) {
                    rows.get(curSynReportData.indexOf(singleRowData)).add(singleRowData.get(2));
                }
            }
            report = new ReportCSV(header, rows);
        }
        return report;
    }
}
