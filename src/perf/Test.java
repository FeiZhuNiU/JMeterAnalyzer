package perf;

import org.apache.commons.csv.CSVRecord;
import perf.analysis.CommonUtils;
import perf.analysis.SynthesisReport;
import perf.data.SimpleDataCSV;
import perf.out.XlsWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class Test {

    public static void main(String[] args) {
        SimpleDataCSV csv = new SimpleDataCSV("resources\\50.jtl");
        SynthesisReport report = new SynthesisReport(csv);
        report.filterByTime(700,1500);
        report.generateReport("synthesis report.xls","mySheet");
//        List<List<String>> data = new ArrayList<>();
//        List<CSVRecord> records = csv.getRecords();
//        for (CSVRecord record : records) {
//            data.add(CommonUtils.csvRecordtoList(record));
//        }
//        XlsWriter.writeXls(csv.getHeaders(),data,"test3.xls","sheet4");
////        csv.writeToXls("test2.xls");
        System.out.println(report.getAvgResponseTime("D2REST-Delete"));
        Map<String, Double> allAvgResponseTime = report.getAllAvgResponseTime();
        allAvgResponseTime.forEach((k,v)->{
            System.out.println(k +"\t\t\t" +v);
        });


    }

}
