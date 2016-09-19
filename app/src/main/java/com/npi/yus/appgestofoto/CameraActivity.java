package com.npi.yus.appgestofoto;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.tv.TvInputService;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
    Author: Jesús Sánchez de Castro
    Last Modification: 25/08/2016
    https://justyusblog.wordpress.com/
    https://github.com/Yussoft

    Bibliography:
    - http://stackoverflow.com/questions/5991319/capture-image-from-camera-and-display-in-activity
*/

/*
    CameraActivity class:

    Class that implements the Camera activity. Opens the device camera, takes a picture and displays
    it.
*/
public class CameraActivity extends AppCompatActivity {

    final int TAKE_PICTURE = 1;
    protected ImageView imageV;
    private Bitmap bmp;
    private File imageFile;
    private boolean pictureTaken = false;

    /**********************************************************************************************/
    /*
        Initializes the ImageView imageV and launches the camera activity with the intent.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(!pictureTaken) {
            //Intent to take a photo
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "picture.jpg");
            Uri temp = Uri.fromFile(imageFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, temp);
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
            startActivityForResult(cameraIntent,0);

            imageV = (ImageView) findViewById(R.id.pictureIV);
            pictureTaken=true;
        }
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }


    private Bitmap loadImage(String imgPath) {
        BitmapFactory.Options options;
        try {
            options = new BitmapFactory.Options();
            options.inSampleSize = 4;// 1/4 of origin image size from width and height
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**********************************************************************************************/
    /*
        Method used to show the picture taken with the camera when the activity has finished.
        Parameters:
            - requestCode: code used to know from what Activity we are getting the result.
            - resultCode: used to know if the process was done correctly.
            - data: intent containing the data from the activity (picture).
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0){
            switch (resultCode){
                case Activity.RESULT_OK:
                    if(imageFile.exists()){
                        Toast.makeText(this,"El archivo ha sido guardado en "+imageFile.getAbsolutePath()+".",Toast.LENGTH_LONG);

                        Bitmap scaledPhoto = BitmapFactory.decodeFile("/mnt/sdcard/" + Session.PHOTO_FILE_NAME);
                        imageV.setImageBitmap(loadImage(imageFile.getAbsolutePath()));

                    }
                    else{
                        Toast.makeText(this,"Ha habido un error al tomar la foto.",Toast.LENGTH_LONG);
                    }
                     break;
                case Activity.RESULT_CANCELED:

                    break;
                default: break;
            }
        }
    }
}
