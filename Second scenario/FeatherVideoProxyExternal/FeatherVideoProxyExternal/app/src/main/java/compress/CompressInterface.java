package compress;

import android.content.Context;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Angel on 08/03/2017.
 */

public interface CompressInterface {

    double compress(File f, Context c, TextView t, boolean adapted);

}
