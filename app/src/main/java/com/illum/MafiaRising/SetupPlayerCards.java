package com.illum.MafiaRising;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;


//shows setup player cards screen, loops for the number of players
public class SetupPlayerCards extends BaseActivity {

    SharedPreferences sharedPrefs;
    private int playerIndex;
    private int playerCount;
    private ImageView imageView;
    private int targetW, targetH;
    private View btnNext;
    private Camera camera;
    private String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_player_cards);

        imageView = (ImageView) findViewById(R.id.role_img);

        init();

        //get game session sharedPref
        final String sharedPrefsFileKey = getString(R.string.app_package) + "." + getString(R.string.game_session_file_key);
        sharedPrefs = getSharedPreferences(sharedPrefsFileKey, Context.MODE_PRIVATE);

        //get player count, defaulted to the minimum
        final String sharedPrefsPlayerCountkey = getString(R.string.app_package) + "." + getString(R.string.game_session_player_count_key);
        final String sharedPrefsPlayerCountMin = getString(R.string.game_session_player_count_min);
        playerCount = sharedPrefs.getInt(sharedPrefsPlayerCountkey, Integer.parseInt(sharedPrefsPlayerCountMin));

        //get the extra roles boolean string set, defaulted to empty set
        String sharedPrefsSetupExtraRolesKey = getString(R.string.app_package) + "." + getString(R.string.game_session_extra_roles_key);
        Set<String> selectedExtraRoles = sharedPrefs.getStringSet(sharedPrefsSetupExtraRolesKey, new HashSet<String>());

        //get loop index
        // default to 0 so it is not dependent on previous screen to start the loop
        playerIndex = getIntent().getIntExtra("SetupPlayerIndex", 0);

        //hide next btn programmatically because it is an included layout
        btnNext = findViewById(R.id.next_button);
        btnNext.setVisibility(View.INVISIBLE);

        final ImageView cameraButton = (ImageView) findViewById(R.id.btn_camera);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerIndex < playerCount) {
                    dispatchTakePictureIntent();
                    playerIndex++;
                }else {
                    cameraButton.setOnClickListener(null);
                    btnNext.setVisibility((View.VISIBLE));
                }
            }
        });


        //TODO: delete previous images if still present (in case game crashed last time)
        //TODO: check if from new game - reset playerIndex in shared preferences in onCreate
        //TODO: on btn press take picture
        //TODO: after take image, replace placeholder with image or set image as src and placeholder as bkgd and set right arrow to visible and set camera btn to hide
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        System.out.println(playerIndex);
        String imageFileName = "Player"+playerIndex;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir+"/"+imageFileName+".jpg");
        /*File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );*/

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
            //startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    private void setPic() {
        /*// Get the dimensions of the View
        //int targetW = imageView.getMeasuredWidth();
        //int targetH = imageView.getMeasuredHeight();

        if(imageView == null) {
            System.out.println("ImageView Null");
        }
        System.out.println(targetW + " " + targetH);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        //bmOptions.inPurgeable = true;*/

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);*/
            setPic();
        }
    }

    //on next btn click, handle loop logic
    public void onClickNext(View view) {
        //loop condition before incrementing so #loops - 1
        // clear previous player card setup screens
        if(playerIndex >= playerCount-1) {
            Intent intent = new Intent(this, GameReady.class);
            startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);

            clearPrevious();
        }
        //continue looping, increment playerIndex
        else {
            Intent intent = new Intent(this, SetupPlayerCards.class);
            intent.putExtra("SetupPlayerIndex", playerIndex + 1);
            startActivityForResult(intent, BaseActivity.REQUEST_EXIT_CODE);
        }
    }
}
