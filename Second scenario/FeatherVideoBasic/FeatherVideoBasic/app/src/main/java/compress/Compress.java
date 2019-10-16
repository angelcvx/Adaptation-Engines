package compress;

import android.content.Context;
import android.os.Environment;
import android.widget.TextView;

import com.github.tcking.giraffecompressor.GiraffeCompressor;

import java.io.File;
import java.util.Random;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class Compress implements CompressInterface {

    private String outputDir;
    private TextView text;
    private int random;

    public Compress() {
        outputDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Output/";
    }

    public double compress(File input, Context context, TextView text) {
        this.text = text;
        random = getRandom();
        File output = new File(outputDir + random + input.getName());
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
                .output(outputFile) //set compressed video output
                .bitRate(13736000)//set bitrate
                .resizeFactor((float) 1)//set video resize factor
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

    public int getRandom() {
        Random rnd = new Random();
        return rnd.nextInt();
    }

}

