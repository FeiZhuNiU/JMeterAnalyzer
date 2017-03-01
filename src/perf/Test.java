package perf;

import perf.analysis.SynthesisReport;
import perf.analysis.LoadTestReport;
import perf.model.SimpleDataWriterCSV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class Test {

    public static void main(String[] args) {
        // jmeter data objects
        SimpleDataWriterCSV csv1 = new SimpleDataWriterCSV("resources/50.jtl");
        SimpleDataWriterCSV csv2 = new SimpleDataWriterCSV("resources/100.jtl");
        SimpleDataWriterCSV csv3 = new SimpleDataWriterCSV("resources/200.jtl");

        //Synthesis report objects
        SynthesisReport report1 = new SynthesisReport(csv1, true);
        SynthesisReport report2 = new SynthesisReport(csv2, true);
        SynthesisReport report3 = new SynthesisReport(csv3, true);
        // write synthesis report to excel
        report1.getReport().writeXls("final.xls","50");
        report2.getReport().writeXls("final.xls","100");
        report3.getReport().writeXls("final.xls","200");

        List<SynthesisReport> reports = new ArrayList<>();
        reports.add(report1);
        reports.add(report2);
        reports.add(report3);
        // load test report object
        LoadTestReport finalReport = new LoadTestReport(reports);
        // write to excel
        finalReport.getReport().writeXls("final.xls", "loadReport");

//        SimpleDataWriterCSV csv4 = new SimpleDataWriterCSV("resources/RU_50.csv");
//        SynthesisReport report = new SynthesisReport(csv4, false);
//        report.getReport().writeXls("RU.xls", "ttt");


    }

}
