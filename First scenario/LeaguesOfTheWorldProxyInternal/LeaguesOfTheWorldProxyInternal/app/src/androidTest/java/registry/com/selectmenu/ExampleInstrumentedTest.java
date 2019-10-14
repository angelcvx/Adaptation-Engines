package registry.com.selectmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import generator.Generator;
import load.LoadInterface;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends ActivityUnitTestCase<MainActivity> {
    //private int OFFSET = 97;
    public ExampleInstrumentedTest () {
        super (MainActivity.class);
    }

    @Test
    public void useAppContext() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        int OFFSET = 97;

        LoadInterface myProxy = (LoadInterface) new Generator().generateObjectsFunction();
        assertEquals(true, myProxy.loadLeagues());
        assertEquals(String.class, myProxy.getLeague(1 + OFFSET));
        android.util.Log.v("TEST", "esta pasando por aqui");
        assertEquals(String.class, myProxy.getLeague(2 + OFFSET));
        assertEquals(String.class, myProxy.getLeague(3 + OFFSET));
        assertEquals(String.class, myProxy.getLeague(4 + OFFSET));
        assertEquals(String.class, myProxy.getLeague(5 + OFFSET));
        // Context of the app under test.
        assertEquals("registry.com.selectmenu", appContext.getPackageName());
    }
}
