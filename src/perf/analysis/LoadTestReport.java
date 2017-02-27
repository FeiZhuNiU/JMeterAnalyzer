package perf.analysis;

import perf.model.ReportCSV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/27.
 */
public class LoadTestReport extends AbstractReport{
    List<SynthesisReport> synthesisReports;

    public LoadTestReport(List<SynthesisReport> synthesisReports) {
        super();
        this.synthesisReports = synthesisReports;
    }
    public void addSynthesisReport(SynthesisReport report){
        synthesisReports.add(report);
    }

    public ReportCSV getReport() {
        if(report == null) {
            reportHeaders.add("Transaction");
            int transactionNum = synthesisReports.get(0).getLabelSet().size();
            List<String> labelList = new ArrayList<>(synthesisReports.get(0).getLabelSet());
            for (int i = 0; i < transactionNum; i++) {
                reportData.add(new ArrayList<>());
                reportData.get(i).add(labelList.get(i));
            }

            for (SynthesisReport synthesisReport : synthesisReports) {
                reportHeaders.add(String.valueOf(synthesisReport.getUserNum()));
                ReportCSV report = synthesisReport.getReport();
                List<List<String>> curSynReportData = report.getData();
                for (List<String> singleRowData : curSynReportData) {
                    reportData.get(curSynReportData.indexOf(singleRowData)).add(singleRowData.get(2));
                }
            }
            report = new ReportCSV(reportHeaders, reportData);
        }
        return report;
    }
}
