package com.simplex.simplelight.deceived;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AssignPlayer.OnNextPlayer} interface
 * to handle interaction events.
 */
public class AssignPlayer extends Fragment {

    private Camera mCamera=null;
    private CameraPreview mPreview;
    private ImageView picture;
    private int cameraId = 0;
    private static int playernum=0;
    private boolean frontCamera=false, redoflag;
    private ImageView redo;
    private ImageView pictureview;
    private OnNextPlayer mListener;
    private SharedPreferences sharedpreferences;

    public static AssignPlayer newInstance(int playernumber){
        AssignPlayer fragment=new AssignPlayer();
        playernum=playernumber;
        Bundle args = new Bundle();
        args.putInt("Playernumber", playernum);
        fragment.setArguments(args);
        return fragment;
    }

    public AssignPlayer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playernum = getArguments().getInt("Playernumber");
        }
        sharedpreferences = getActivity().getSharedPreferences("one", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_assign_player, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState){
        super.onViewCreated( view, savedInstanceState );



        // Inflate the layout for this fragment
        pictureview = (ImageView) getView().findViewById(R.id.picture_review);
        pictureview.setVisibility(View.GONE);


        redo = (ImageView) getView().findViewById(R.id.redo);
        redo.setVisibility(View.GONE);
        redoflag=false;
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRedoButton(v);
            }
        });

        final RelativeLayout preview = (RelativeLayout) getView().findViewById(R.id.camera_preview);
        preview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                preview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setPreviewSize(preview); //height is ready
            }
        });
    }

    private void cameraInitialize() {
        if (!getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
        } else {
            cameraId = findFrontFacingCamera();
            if (cameraId < 0) {
                try {
                    mCamera = Camera.open();
                    mCamera.setDisplayOrientation(90);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("FrontCamera", 0);
                    editor.commit();
                } catch (Exception e) {
                }
            } else {
                try {
                    mCamera = Camera.open(cameraId);
                    mCamera.setDisplayOrientation(90);
                    frontCamera=true;
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("FrontCamera", 1);
                    editor.commit();
                } catch (Exception e) {
                    Log.d("Camera", "Camera not Opened");
                }
            }
        }

        if (mCamera != null) {
            // Create our Preview view and set it as the content of our activity.
            mPreview = new CameraPreview(getActivity().getApplicationContext(), mCamera);
            RelativeLayout preview = (RelativeLayout) getView().findViewById(R.id.camera_preview);
            preview.addView(mPreview);

            Camera.Parameters params = mCamera.getParameters();

            List<String> focusModes = params.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                // Autofocus mode is supported
                // set the focus mode
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                // set Camera parameters
            }

            if(frontCamera==false)
            {
                params.setRotation(90);
            }else params.setRotation(270);
            mCamera.setParameters(params);


            // Add a listener to the Capture button
            final Button captureButton = (Button) getView().findViewById(R.id.button_capture);
            captureButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // get an image from the camera
                            if(redoflag!=true) {
                                mCamera.takePicture(null, null, mPicture);
                                captureButton.setText("NEXT PLAYER");
                                redoflag=true;
                            }
                            else nextPlayer(v);
                        }
                    }
            );
        }
    }

    private void setPreviewSize(RelativeLayout preview){
        if(mCamera!=null) {
            Camera.Size size = mCamera.getParameters().getPreviewSize();


            //landscape
            //float ratio = (float) size.width / size.height;

            //portrait
            float ratio = (float)size.height/size.width;


            int new_width = 0, new_height = 0;
            if (preview.getWidth() / preview.getHeight() < ratio) {
                new_width = Math.round(preview.getHeight() * ratio);
                new_height = preview.getHeight();
            } else {
                new_width = preview.getWidth();
                new_height = Math.round(preview.getWidth() / ratio);
            }
            RelativeLayout.LayoutParams paramadjust = new RelativeLayout.LayoutParams(new_width, new_height);
            Toast.makeText(getContext(),"Ratio "+ratio+" Width "+new_width+" Height "+new_height, Toast.LENGTH_SHORT).show();
            preview.setLayoutParams(paramadjust);
        }
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.d("Error F", "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                saveToInternalStorage(getActivity().getApplicationContext(), bmp);

                Log.d("Error A", "Error creating media file, check storage permissions: ");
                return;
            } else {
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                } catch (FileNotFoundException e) {
                    Log.d("Error B", "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d("Error C", "Error accessing file: " + e.getMessage());
                }

                mCamera.stopPreview();
                /*
                RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
                preview.removeAllViews();
                preview.addView(picture);
                picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
                picture.setAdjustViewBounds(true);*/

                RelativeLayout preview = (RelativeLayout) getView().findViewById(R.id.camera_preview);
                preview.setVisibility(View.GONE);
                pictureview.setImageBitmap(setImage());
                Toast.makeText(getContext(),"Picture - Height "+pictureview.getHeight()+" Width "+pictureview.getWidth(),Toast.LENGTH_SHORT).show();
                pictureview.setVisibility(View.VISIBLE);


                if(redo!=null) {
                    Animation myFadeInAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fadein);
                    redo.startAnimation(myFadeInAnimation);
                    redo.setVisibility(View.VISIBLE);
                }
            }
        }
    };



    public static final int MEDIA_TYPE_IMAGE = 1;

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        boolean[] storageavailable = checkStorage();
        File mediaStorageDir = null;

        if (storageavailable[0] == true && storageavailable[1] == true) {
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "MyCameraApp");
            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.
        } else mediaStorageDir = new File(Environment.getDataDirectory(), "MyCameraApp");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        saveNoMedia(mediaStorageDir);

        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "Player" + playernum + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    private static void saveNoMedia(File path){
        String NOMEDIA_FILE = ".nomedia";
        File file= new File(path,NOMEDIA_FILE);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
    }

    private static boolean[] checkStorage() {
        boolean[] storage = new boolean[2];

        boolean externalStorageAvailable, externalStorageWriteable;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            externalStorageAvailable = externalStorageWriteable = true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            externalStorageAvailable = true;
            externalStorageWriteable = false;
        } else externalStorageAvailable = externalStorageWriteable = false;

        storage[0] = externalStorageAvailable;
        storage[1] = externalStorageWriteable;

        return storage;
    }


    private static void saveToInternalStorage(Context context, Bitmap b) {
        FileOutputStream fos;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            fos = context.openFileOutput("IMG_" + timeStamp, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("InternalStorage", "file not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("InternalStorage", "io exception");
            e.printStackTrace();
        }
    }

    private Bitmap setImage()
    {
        File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File directory=new File(sdCard.getAbsolutePath()+"/MyCameraApp");
        File file=new File(directory, "Player"+playernum+".jpg");
        Bitmap bitmap=rotateImage(file.getAbsolutePath());
        return bitmap;
    }

    private Bitmap rotateImage(String file)
    {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, bounds);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(file, opts);
        ExifInterface exif=null;
        try {
            exif = new ExifInterface(file);
        }catch(IOException e){
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;

        int rotationAngle = 0;

        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        if(frontCamera==true)
        {
            matrix.preScale(1.0f,-1.0f);
        }
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);

        return rotatedBitmap;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void nextPlayer(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    public void onRedoButton(View view)
    {
        redo.setVisibility(View.GONE);
        if(mCamera!=null)
        {
            releaseCamera();
            cameraInitialize();
            mPreview = new CameraPreview(getContext(), mCamera);
            RelativeLayout preview = (RelativeLayout) getView().findViewById(R.id.camera_preview);
            preview.removeAllViews();
            preview.addView(mPreview);
            pictureview.setVisibility(View.GONE);
            preview.setVisibility(View.VISIBLE);
            /*
            if(mPreview==null) {
                mPreview = new CameraPreview(getActivity().getApplicationContext(), mCamera);
            }
            RelativeLayout preview = (RelativeLayout) getView().findViewById(R.id.camera_preview);
            preview.removeAllViews();
            preview.addView(mPreview);*/
        }else cameraInitialize();

        redoflag=false;
        Button captureButton = (Button) getView().findViewById(R.id.button_capture);
        captureButton.setText("CAPTURE");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNextPlayer) {
            mListener = (OnNextPlayer) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNextPlayer");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnNextPlayer {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    @Override
    public void onPause(){
        super.onPause();
        mCamera.setPreviewCallback(null);
        mPreview.getHolder().removeCallback(mPreview);
        releaseCamera();              // release the camera immediately on pause event
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mCamera==null){
            Toast.makeText(getActivity().getApplicationContext(),"Camera Opening", Toast.LENGTH_SHORT).show();
            cameraInitialize();
        }
        redo.setVisibility(View.GONE);
    }

    private void releaseCamera() {
        mCamera.release();        // release the camera for other applications
        mCamera = null;
    }
}
