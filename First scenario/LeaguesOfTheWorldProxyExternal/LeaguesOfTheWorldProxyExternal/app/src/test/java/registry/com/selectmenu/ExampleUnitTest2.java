package registry.com.selectmenu;

import org.junit.Before;
import org.junit.Test;

import dalvik.annotation.TestTargetClass;
import generator.Generator;
import load.LoadInterface;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest2 {

    private LoadInterface myProxy = (LoadInterface) new Generator().generateObjectsFunction(); //LoadInterface es conocido por el usuario. Preaparacion
    private static int OFFSET = 97;

    @Test
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
    public void loadLeague7() throws Exception {
        assertEquals(true, myProxy.getLeague(7 + OFFSET));
    }

    @Test
    public void loadLeague8() throws Exception {
        assertEquals(true, myProxy.getLeague(8 + OFFSET));
    }

    @Test
    public void loadLeague9() throws Exception {
        assertEquals(true, myProxy.getLeague(9 + OFFSET));
    }

    @Test
    public void loadLeague10() throws Exception {
        assertEquals(true, myProxy.getLeague(10 + OFFSET));
    }
}