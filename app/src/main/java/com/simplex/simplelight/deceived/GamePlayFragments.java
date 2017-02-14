package com.simplex.simplelight.deceived;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GamePlayFragments.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GamePlayFragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamePlayFragments extends Fragment {
    private OnFragmentInteractionListener mListener;
    private SharedPreferences sharedpreferences;
    private boolean frontCamera=false;
    private int playernum=0;
    private TextView roletext;
    private Bitmap[] images;
    private static String[] roleassigns;
    private int[] mImageResIds;
    private static String screentype;
    private static boolean[] selectedValues;
    private static boolean checked=false;

    public GamePlayFragments() {
        // Required empty public constructor
    }

    public static GamePlayFragments newInstance(String[] roles, String type, boolean[] values) {
        GamePlayFragments fragment = new GamePlayFragments();
        roleassigns=roles;
        screentype=type;
        selectedValues=values;
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        final Resources resources = context.getResources();

        final TypedArray typedArray = resources.obtainTypedArray(R.array.images);
        final int imageCount = roleassigns.length;
        mImageResIds = new int[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageResIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();

        sharedpreferences = getActivity().getSharedPreferences("one", Context.MODE_PRIVATE);
        int num = sharedpreferences.getInt("FrontCamera",0);
        if(num==1)
            frontCamera=true;
        playernum = sharedpreferences.getInt("Players",0);
        checked=false;
        /*
        images=new Bitmap[num];
        for(int a=0;a<playernum;a++)
        {
            images[a]=setImage(a);
        }*/


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_game_play_fragments,container,false);

        final Activity activity=getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager grid = new GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false);
        grid.setSpanCount(2);
        recyclerView.setLayoutManager(grid);
        recyclerView.setAdapter(new PictureAdapter(activity));
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textRoles = (TextView) getView().findViewById(R.id.roletext);
        textRoles.setText(screentype);
    }


    class PictureAdapter extends RecyclerView.Adapter<ViewHolder> {
        private LayoutInflater mLayoutInflater;
        public PictureAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            return new ViewHolder(mLayoutInflater
                    .inflate(R.layout.fragment_gameplay_pictures, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            final int imageResId = mImageResIds[position];
            final String role = roleassigns[position];
            viewHolder.setData(position, role);

            //viewHolder.setData(images[position], roleassigns[position]);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedValues[position]=true;
                    if(screentype.equals("Police")) {
                        if(checked==false)
                            viewHolder.showRole();
                    }else viewHolder.showSelect();
                }
            });
        }

        @Override
        public int getItemCount() {
            return roleassigns.length;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Views
        private ImageView mImageView;
        private TextView mNameTextView;
        private ImageView mSelectView;

        private ViewHolder(View itemView) {
            super(itemView);

            // Get references to image and name.
            mImageView = (ImageView) itemView.findViewById(R.id.picturefrag);
            mNameTextView = (TextView) itemView.findViewById(R.id.role);
            mSelectView = (ImageView) itemView.findViewById(R.id.select);
        }

        private void setData(int imageResId, String role) {
            //mImageView.setImageBitmap(imageFile);
            //mImageView.setImageResource(imageResId);
            Bitmap image=setImage(imageResId);
            mImageView.setImageBitmap(image);
            mNameTextView.setText(role);
            mNameTextView.setVisibility(View.GONE);
            mSelectView.setVisibility(View.GONE);
        }

        private void showRole(){
            mNameTextView.setVisibility(View.VISIBLE);
            checked=true;
        }

        private void showSelect() {
            mSelectView.setVisibility(View.VISIBLE);
        }
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.gamePlayFragment(selectedValues);
        }
    }

    private Bitmap setImage(int playernum){
        File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File directory=new File(sdCard.getAbsolutePath()+"/MyCameraApp");
        File file=new File(directory, "Player"+playernum+".jpg");
        Bitmap bitmap=rotateImage(file.getAbsolutePath());
        return bitmap;
    }

    private Bitmap rotateImage(String file){
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void gamePlayFragment(boolean[] selectedValues);
    }
}
