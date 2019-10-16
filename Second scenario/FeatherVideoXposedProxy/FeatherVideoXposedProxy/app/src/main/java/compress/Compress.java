package compress;

import android.content.Context;
import android.os.Environment;
import android.widget.TextView;

import com.github.tcking.giraffecompressor.GiraffeCompressor;

import java.io.File;

import app.previous.state.PreviousStateUser;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class Compress implements CompressInterface {

    private static String outputDir;
    private static TextView text;
    private static PreviousStateUser state;

    public Compress() {
        outputDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Output/";
    }

    public double compress(File input, Context context, TextView text) {
        state = new PreviousStateUser(context);
        this.text = text;
        File output = new File(outputDir + state.getNumberVideoCompressed(input.getName()) + input.getName());
        android.util.Log.d("CompressVideo","Original: " + input.length() + " Kb");
        double factor = getFactor();
        compressFile(input,output, context);
        return factor;

    }

    public void compressFile (File input, File output, Context context) {
        giraffeCompressor(input,output,context,getFactor());
    }

    public static File giraffeCompressor (File inputFile, final File outputFile, Context context, double factor) {
        GiraffeCompressor.init(context);
        final long inputSize = inputFile.length();
        GiraffeCompressor.create() //two implementations: mediacodec and ffmpeg,default is mediacodec
                .input(inputFile) //set video to be compressed
                .output(outputDir + state.getNumberVideoCompressed(inputFile.getName())+ inputFile.getName()) //set compressed video output
                .bitRate(13736000)//set bitrate
                .resizeFactor((float) factor)//set video resize factor
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
                        String msg = String.format("compress completed \ntake time:%s ms\nout put file:%s", s.getCostTime(), s.getOutput());
                        msg = msg + "\ninput file size:"+ inputSize/1024 + " kb";
                        msg = msg + "\noutput file size:"+ outputFile.length()/1024 + " kb";
                        text.setText(msg);
                    }
                });
        return outputFile;
    }

    public double getFactor() {
        return 1;
    }

}

