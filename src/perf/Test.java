package perf;

import perf.analysis.SynthesisReport;
import perf.analysis.LoadTestReport;
import perf.model.SimpleDataCSV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class Test {

    public static void main(String[] args) {
//        SimpleDataCSV csv = new SimpleDataCSV("resources\\50.jtl");
//        SynthesisReport report = new SynthesisReport(csv);
//        report.autoFilter();
//        ReportCSV reportCSV1 = report.getReport();
//        reportCSV1.writeXls("aaa.xls","sheeet");
        SimpleDataCSV csv1 = new SimpleDataCSV("resources/50.jtl");
        SimpleDataCSV csv2 = new SimpleDataCSV("resources/100.jtl");
        SynthesisReport report1 = new SynthesisReport(csv1);
        SynthesisReport report2 = new SynthesisReport(csv2);
        report1.getReport().writeXls("final.xls","50");
        report2.getReport().writeXls("final.xls","100");
        List<SynthesisReport> reports = new ArrayList<>();
        reports.add(report1);
        reports.add(report2);
        LoadTestReport finalReport = new LoadTestReport(reports);
        finalReport.getReport().writeXls("final.xls", "loadReport");


//        System.out.println(report.getAvgResponseTime("D2REST-Delete"));
//        Map<String, Double> allAvgResponseTime = report.getAllAvgResponseTime();
//        allAvgResponseTime.forEach((k,v)->{
//            System.out.println(k +"\t\t\t" +v);
//        });


    }

}
