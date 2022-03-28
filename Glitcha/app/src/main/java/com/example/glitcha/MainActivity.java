package com.example.glitcha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    final int PICK_IMAGE = 1;
    Bitmap glitchBitmap;
    Bitmap originalBitmap;
    ImageView imageView;
    Uri imageUri;
    Button exportButton;
    Button loadButton;
    Button glitchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        exportButton = findViewById(R.id.button2);
        loadButton = findViewById(R.id.button);
        glitchButton = findViewById(R.id.button3);
    }

    public void loadImage(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            try {
                originalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void toGlitch(View view) {
        glitchBitmap = Glitcher.glitch(originalBitmap,
                30,
                ThreadLocalRandom.current().nextInt(1, 99),
                ThreadLocalRandom.current().nextInt(1, 99),
                70);
        imageView.setImageBitmap(glitchBitmap);
    }

    public void saveImage(View view) {
        File root = Environment.getExternalStorageDirectory();
        String name = "glitcha" + Long.toString(Calendar.getInstance().getTimeInMillis()) + ".jpg";
        File dir = new File(root.getAbsolutePath() + "/DCIM/Glitcha");
        dir.mkdirs();
        File outFile = new File(dir, name);
        try {
            outFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        glitchBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        try {
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}