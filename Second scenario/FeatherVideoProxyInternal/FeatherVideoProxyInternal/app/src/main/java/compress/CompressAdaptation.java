package compress;

import android.content.Context;
import android.os.Environment;
import android.widget.TextView;

import com.github.tcking.giraffecompressor.GiraffeCompressor;

import java.io.File;

import app.previous.state.PreviousStateUser;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class CompressAdaptation implements CompressInterface {

    private String outputDir;
    private TextView text;
    private PreviousStateUser state;

    public CompressAdaptation() {
        outputDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Output/";
    }

    public double compress(File input, Context context, TextView text) {
        state = new PreviousStateUser(context);
        this.text = text;
        File output = new File(outputDir + state.getNumberVideoCompressed(input.getName()) + input.getName());
        android.util.Log.d("CompressVideo","Original: " + input.length() + " Kb");
        giraffeCompressor(input,output, context);
        double factor = output.length()/input.length();
        return factor;

    }

    public File giraffeCompressor (File inputFile, final File outputFile, Context context) {
        GiraffeCompressor.init(context);
        final long inputSize = inputFile.length();
        GiraffeCompressor.create() //two implementations: mediacodec and ffmpeg,default is mediacodec
                .input(inputFile) //set video to be compressed
                .output(outputDir + state.getNumberVideoCompressed(inputFile.getName())+ inputFile.getName()) //set compressed video output
                .bitRate(13736000)//set bitrate
                .resizeFactor((float) 0.15)
                .ready()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GiraffeCompressor.Result>() {

                    @Override
                    public void onStart() {
                        text.setText("Compressing...");
                    }
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        text.setText(String.valueOf("Error!" + e.getMessage() ));
                    }

                    @Override
                    public void onNext(GiraffeCompressor.Result s) {
                        String msg = String.format("compress completed (Adapted mode)\ntake time:%s ms\nout put file:%s", s.getCostTime(), s.getOutput());
                        msg = msg + "\ninput file size:"+ inputSize/1024 + " kb";
                        msg = msg + "\noutput file size:"+ outputFile.length()/1024 + " kb";
                        text.setText(msg);
                    }
                });
        return outputFile;
    }

}

