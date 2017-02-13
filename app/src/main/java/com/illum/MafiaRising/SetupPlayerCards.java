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
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


//shows setup player cards screen, loops for the number of players
public class SetupPlayerCards extends BaseActivity {

    SharedPreferences sharedPrefs;
    private int playerIndex;
    private int playerCount;
    private com.illum.MafiaRising.CustomFontTextView playerCounter;
    private com.github.siyamed.shapeimageview.mask.PorterShapeImageView imageView;
    private int targetW, targetH;
    private View btnNext;
    private Camera camera;
    private String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private String[] roles;
    private int ratio;
    private int[] mafiaplaces;
    private String[] assignments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_player_cards);

        imageView = (com.github.siyamed.shapeimageview.mask.PorterShapeImageView) findViewById(R.id.test);

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

        setMafia();

        //get loop index
        // default to 0 so it is not dependent on previous screen to start the loop
        playerIndex = getIntent().getIntExtra("SetupPlayerIndex", 0);

        //hide next btn programmatically because it is an included layout
        btnNext = findViewById(R.id.next_button);
        btnNext.setVisibility(View.INVISIBLE);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (storageDir != null) {
            for(File file: storageDir.listFiles())
                if (!file.isDirectory())
                    file.delete();
        }

        playerCounter = (com.illum.MafiaRising.CustomFontTextView) findViewById(R.id.text_player_tally);
        final ImageView cameraButton = (ImageView) findViewById(R.id.btn_camera);

        playerCounter.setText((playerIndex+1)+"/"+playerCount);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                playerIndex++;
                if(playerIndex >= playerCount) {
                    cameraButton.setOnClickListener(null);
                }else {
                    playerCounter.setText((playerIndex + 1) + "/" + playerCount);
                }
            }
        });


        //TODO: delete previous images if still present (in case game crashed last time)
        //TODO: check if from new game - reset playerIndex in shared preferences in onCreate
        //TODO: refine image replacement
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
        // Get the dimensions of the View
        /*int targetW = imageView.getMeasuredWidth();
        int targetH = imageView.getMeasuredHeight();*/
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int targetW = size.x/2;
        int targetH = size.y/2;


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

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);

        if(playerIndex >= playerCount) {
            btnNext.setVisibility((View.VISIBLE));
        }
    }

    private void setMafia() {
        assignments=new String[playerCount];

        ratio=playerCount/4;
        mafiaplaces=new int[ratio];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=1; i<playerCount; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i=0; i<ratio; i++) {
            mafiaplaces[i]=list.get(i);
        }
        for(int i=0;i<playerCount;i++) {
            assignments[i]="Citizen";
            for(int mafiasel=0;mafiasel<ratio;mafiasel++) {
                if(mafiaplaces[mafiasel]==i)
                    assignments[i]="Mafia";
            }
        }

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
            Bundle b = new Bundle();
            b.putStringArray("RoleA", assignments);
            intent.putExtras(b);
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
