package perf;

import perf.analyze.SynthesisReport;
import perf.data.BasicJMeterCSV;
import perf.out.XlsWriter;

import java.util.Map;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class Test {

    public static void main(String[] args) {
        BasicJMeterCSV csv = new BasicJMeterCSV("resources\\50.jtl");
        SynthesisReport report = new SynthesisReport(csv);
        report.filterByTime(700,1500);
        XlsWriter.writeXls(csv.getHeaders(),csv.getRecords(),"test3.xls","sheet3");
//        csv.writeToXls("test2.xls");
        System.out.println(report.avgResponseTime("D2REST-Delete"));
        Map<String, Double> allAvgResponseTime = report.getAllAvgResponseTime();
        allAvgResponseTime.forEach((k,v)->{
            System.out.println(k +"\t\t\t" +v);
        });

//        System.out.println(csv.getHeaders());
//
//        csv.getRecordsByHeader(CommonCSVHeader.LABEL, "D2REST-Delete").forEach(record -> {
//            System.out.println(record.get("timeStamp"));
//        });
//        System.out.println(csv.getSuccessRate(CommonCSVHeader.LABEL, "D2REST-Delete"));
//        System.out.println(csv.avg(csv.getRecords(), CommonCSVHeader.ELAPSED.getKey()));
    }

}
