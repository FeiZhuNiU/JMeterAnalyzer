package perf.analysis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perf.model.SimpleDataWriterCSV;

import static org.junit.Assert.*;

/**
 * Created by Eric Yu on 2017/2/27.
 * yulin.jay@gmail.com
 */
public class SynthesisReportTest {
    SynthesisReport report = null;

    @BeforeEach
    public void setUp() throws Exception {
        report = new SynthesisReport(new SimpleDataWriterCSV("resources/50.jtl"));
    }

    @AfterEach
    public void tearDown() throws Exception {

    }

    @Test
    public void getUserNum() throws Exception {
        assertEquals(50, report.getUserNum());
    }

    @Test
    void getLabelSet() {
        assertEquals(40, report.getLabelSet().size());
    }


    @Test
    void autoFilter() {
        assertTrue(report.getFilteredData().size() < report.getOriginData().size());
    }



}