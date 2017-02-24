package perf;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class Core {

    public static void main(String[] args) {
        ResponseTimeCSV csv = new ResponseTimeCSV("temp\\50.jtl");
        csv.filterByTime(700, 1500);
        csv.writeToXls("test2.xls");
        System.out.println(csv.avgResponseTime("D2REST-Delete"));

//        System.out.println(csv.getHeaders());
//
//        csv.getRecordsByHeader(JMeterCSVHeader.LABEL, "D2REST-Delete").forEach(record -> {
//            System.out.println(record.get("timeStamp"));
//        });
//        System.out.println(csv.getSuccessRate(JMeterCSVHeader.LABEL, "D2REST-Delete"));
//        System.out.println(csv.avg(csv.getRecords(), JMeterCSVHeader.ELAPSED.getKey()));
    }

}
