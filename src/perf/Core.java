package perf;

/**
 * Created by Eric Yu on 2017/2/23.
 */
public class Core {

    public static void main(String[] args) {
        JMeterCSV csv = new JMeterCSV("temp\\50.jtl");
        csv.getHeaders();
        csv.writeToXls("test.xls");
    }

}
