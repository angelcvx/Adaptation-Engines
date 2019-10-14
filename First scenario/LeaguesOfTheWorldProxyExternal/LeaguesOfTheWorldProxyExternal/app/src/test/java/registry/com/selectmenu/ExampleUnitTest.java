package registry.com.selectmenu;

import org.junit.Before;
import org.junit.Test;

import generator.Generator;
import load.LoadInterface;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private LoadInterface myProxy = (LoadInterface) new Generator().generateObjectsFunction(); //LoadInterface es conocido por el usuario. Preaparacion
    private static int OFFSET = 97;

    @Before
    public void getLeagues() throws Exception {
        assertEquals(true, myProxy.loadLeagues());
    }

    @Test
    public void loadLeague1() throws Exception {
        assertEquals(true, myProxy.getLeague(1 + OFFSET));
    }

    @Test
    public void loadLeague2() throws Exception {
        assertEquals(true, myProxy.getLeague(2 + OFFSET));
    }

    @Test
    public void loadLeague3() throws Exception {
        assertEquals(true, myProxy.getLeague(3 + OFFSET));
    }

    @Test
    public void loadLeague4() throws Exception {
        assertEquals(true, myProxy.getLeague(4 + OFFSET));
    }

    @Test
    public void loadLeague5() throws Exception {
        assertEquals(true, myProxy.getLeague(5 + OFFSET));
    }

    @Test
    public void loadLeague6() throws Exception {
        assertEquals(true, myProxy.getLeague(6 + OFFSET));
    }
}