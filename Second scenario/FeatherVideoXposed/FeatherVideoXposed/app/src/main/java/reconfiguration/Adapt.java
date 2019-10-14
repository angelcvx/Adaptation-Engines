package reconfiguration;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.example.caosd.videocompressor.R;

import java.util.Map;
import java.util.TreeMap;

public class Adapt {
    private Map<String,Class<?>> methods;
    private boolean stateDefault;
    private Context context;

    public Adapt(Context c) {
        methods = new TreeMap<>();
        stateDefault = true;
        context = c;
    }

    public Map<String,Class<?>> getMethods() {
        return methods;
    }

    public void add(String methodName, Class<?> object) {
        methods.put(methodName,object);
    }

    public void remove(String methodName) {
        methods.remove(methodName);
    }

    public boolean isStateDefault() {
        return stateDefault;
    }

    public void setColors() {
        int black = context.getResources().getColor(R.color.black);
        int white = context.getResources().getColor(R.color.white);
        ConstraintLayout main = (ConstraintLayout) ((Activity) context).findViewById(R.id.main_background);
        TextView text = (TextView) ((Activity) context).findViewById(R.id.main_text);

        main.setBackgroundColor(black);
        text.setTextColor(white);
    }

}
