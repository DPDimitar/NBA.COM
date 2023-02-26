package org.example;

import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setStreams() {
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @AfterEach
    public void restoreInitialStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testCaseKobeBryant() {
        Main.main(new String[] {"Kobe Bryant"});
        String result = """
                Harvesting..
                YEAR: 2015-16 -  3PA: 10.0
                YEAR: 2014-15 -  3PA: 6.1
                YEAR: 2013-14 -  3PA: 3.6
                YEAR: 2012-13 -  3PA: 5.4
                YEAR: 2011-12 -  3PA: 5.1
                YEAR: 2010-11 -  3PA: 5.1
                YEAR: 2009-10 -  3PA: 4.2
                YEAR: 2008-09 -  3PA: 4.5
                YEAR: 2007-08 -  3PA: 5.2
                YEAR: 2006-07 -  3PA: 5.1
                YEAR: 2005-06 -  3PA: 6.3
                YEAR: 2004-05 -  3PA: 5.8
                YEAR: 2003-04 -  3PA: 3.5
                YEAR: 2002-03 -  3PA: 3.8
                YEAR: 2001-02 -  3PA: 1.7
                YEAR: 2000-01 -  3PA: 2.9
                YEAR: 1999-00 -  3PA: 2.3
                YEAR: 1998-99 -  3PA: 2.1
                YEAR: 1997-98 -  3PA: 4.3
                YEAR: 1996-97 -  3PA: 4.9
                """;
        assertEquals(result.replaceAll("\\n|\\r\\n", System.getProperty("line.separator")).trim(), out.toString().trim());
    }

    @Test
    public void testCaseLukaDoncic() {
        Main.main(new String[] {"Luka Doncic"});
        String result = """
                Harvesting..
                YEAR: 2022-23 -  3PA: 8.6
                YEAR: 2021-22 -  3PA: 9.9
                YEAR: 2020-21 -  3PA: 9.7
                YEAR: 2019-20 -  3PA: 10.6
                YEAR: 2018-19 -  3PA: 8.9""";
        assertEquals(result.replaceAll("\\n|\\r\\n", System.getProperty("line.separator")).trim(), out.toString().trim());
    }


}