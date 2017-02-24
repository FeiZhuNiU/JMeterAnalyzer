package perf;

import perf.analyze.SynthesisReport;
import perf.data.SimpleDataCSV;

import java.util.Map;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class Test {

    public static void main(String[] args) {
        SimpleDataCSV csv = new SimpleDataCSV("resources\\50.jtl");
        new SynthesisReport(csv).filterByTime(700, 1500);
        csv.writeToXls("test2.xls");
        System.out.println(csv.avgResponseTime("D2REST-Delete"));
        Map<String, Double> allAvgResponseTime = csv.getAllAvgResponseTime();
        allAvgResponseTime.forEach((k,v)->{
            System.out.println(k +"\t\t\t" +v);
        });

//        System.out.println(csv.getHeaders());
//
//        csv.getRecordsByHeader(SimpleDataHeader.LABEL, "D2REST-Delete").forEach(record -> {
//            System.out.println(record.get("timeStamp"));
//        });
//        System.out.println(csv.getSuccessRate(SimpleDataHeader.LABEL, "D2REST-Delete"));
//        System.out.println(csv.avg(csv.getRecords(), SimpleDataHeader.ELAPSED.getKey()));
    }

}
