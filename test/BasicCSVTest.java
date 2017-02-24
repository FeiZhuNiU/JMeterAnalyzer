import perf.BasicCSV;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Eric Yu on 2017/2/24.
 */
class BasicCSVTest {
    BasicCSV csv;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        csv = new BasicCSV("temp\\50.jtl");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        csv.close();
    }

    @org.junit.jupiter.api.Test
    void getRecords() {
        int size = csv.getRecords().size();
        assertEquals(9273, size);
    }

    @org.junit.jupiter.api.Test
    void getFileName() {
        assertEquals("temp\\50.jtl", csv.getFileName());
    }

    @org.junit.jupiter.api.Test
    void getHeaders() {

    }

    @org.junit.jupiter.api.Test
    void avg() {

    }

    @org.junit.jupiter.api.Test
    void count() {

    }

}