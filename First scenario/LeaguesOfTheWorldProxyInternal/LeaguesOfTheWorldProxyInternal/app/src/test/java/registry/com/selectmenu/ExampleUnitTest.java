package registry.com.selectmenu;

import org.junit.Test;

import generator.Generator;
import load.LoadInterface;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private int OFFSET = 97;

    @Test
    public void getLeagues() throws Exception {
        LoadInterface myProxy = (LoadInterface) new Generator().generateObjectsFunction();
        assertEquals(true, myProxy.loadLeagues());
        assertEquals(String.class, myProxy.getLeague(1 + OFFSET));
        assertEquals(String.class, myProxy.getLeague(2 + OFFSET));
        assertEquals(String.class, myProxy.getLeague(3 + OFFSET));
        assertEquals(String.class, myProxy.getLeague(4 + OFFSET));
        assertEquals(String.class, myProxy.getLeague(5 + OFFSET));
    }

}