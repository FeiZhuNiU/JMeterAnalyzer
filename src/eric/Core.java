package eric;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileReader;

/**
 * Created by yue8 on 2017/2/23.
 */
public class Core {
    public static void main(String[] args) {
        try {
            CSVParser parser = new CSVParser(new FileReader("temp\\50.jtl"), CSVFormat.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
