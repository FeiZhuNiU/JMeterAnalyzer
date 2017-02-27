package perf;

import perf.analysis.SynthesisReport;
import perf.data.ReportCSV;
import perf.data.SimpleDataCSV;

import java.util.Map;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class Test {

    public static void main(String[] args) {
        SimpleDataCSV csv = new SimpleDataCSV("resources\\50.jtl");
        SynthesisReport report = new SynthesisReport(csv);
        report.autoFilter(50);
        ReportCSV reportCSV1 = report.generateReport();
        reportCSV1.writeXls("aaa.xls","sheeet");

        System.out.println(report.getAvgResponseTime("D2REST-Delete"));
        Map<String, Double> allAvgResponseTime = report.getAllAvgResponseTime();
        allAvgResponseTime.forEach((k,v)->{
            System.out.println(k +"\t\t\t" +v);
        });


    }

}
