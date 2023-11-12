package com.example.datasetformas;

import static android.Manifest.permission.ACCESS_MEDIA_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VIDEO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    EditText directoryinput;
    EditText nameinput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        directoryinput = findViewById(R.id.directoryinput);
        nameinput = findViewById(R.id.nameinput);

        ActivityCompat.requestPermissions(this,
                new String[]{ACCESS_MEDIA_LOCATION, READ_MEDIA_IMAGES, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
    }

    public void buttonCapture(View view){

        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0); // 0 is for internal storage
        String directory = directoryinput.getText().toString();
        String name = nameinput.getText().toString();
        File newDirectory = new File(storageVolume.getDirectory().getPath() + "/Download/" + directory);
        if (!newDirectory.exists()){
            newDirectory.mkdirs();
        }
        File fileImage = new File(storageVolume.getDirectory().getPath() + "/Download/" + directory + "/" + name + ".jpeg"); // File name

        Uri uriImageFile = FileProvider.getUriForFile(this,
                "com.example.datasetformas.provider",
                fileImage);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImageFile);

        startActivity(cameraIntent);
    }
}