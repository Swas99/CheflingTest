package com.archer.recipeupload.modules.recipe_upload.tab_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.archer.recipeupload.R;
import com.archer.recipeupload.utils.CheflingUtils;
import com.archer.recipeupload.utils.SharedPreferenceManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by Swastik on 24-03-2016.
 */
public class RecipeUploadPage1
        extends Fragment
        implements View.OnClickListener {

    private static final int RC_GALLERY = 101;
    private static final int RC_CAMERA = 102;

    Dialog dialog;
    EditText et = null;
    TextInputLayout etLayout = null;

    public static RecipeUploadPage1 newInstance( ) {
        return new RecipeUploadPage1();
    }

    public RecipeUploadPage1() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_upload_p1, container, false);
        init(rootView);
        return rootView;
    }


    private void init(View rootView) {
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        rootView.findViewById(R.id.btnCam).setOnClickListener(this);
        rootView.findViewById(R.id.tvRecipeName).setOnClickListener(this);
        rootView.findViewById(R.id.btnEditRecipeName).setOnClickListener(this);
        rootView.findViewById(R.id.tvRecipeType).setOnClickListener(this);
        rootView.findViewById(R.id.btnEditRecipeType).setOnClickListener(this);
        rootView.findViewById(R.id.tvBeginner).setOnClickListener(this);
        rootView.findViewById(R.id.tvSousChef).setOnClickListener(this);
        rootView.findViewById(R.id.tvMaster).setOnClickListener(this);
        rootView.findViewById(R.id.tvServes).setOnClickListener(this);
        rootView.findViewById(R.id.btnEditServes).setOnClickListener(this);
        rootView.findViewById(R.id.tvCookingTime).setOnClickListener(this);
        rootView.findViewById(R.id.btnEditCookingTime).setOnClickListener(this);
        rootView.findViewById(R.id.tvNotes).setOnClickListener(this);
        rootView.findViewById(R.id.btnEditNotes).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnCam:
            {
                showImagePickerDialog();
                break;
            }

            case R.id.tvRecipeName:
            case R.id.btnEditRecipeName:
            {
                dialog = getDialog(R.layout.dialog_text_input);
                dialog.findViewById(R.id.btnCancel).setOnClickListener(this);
                dialog.findViewById(R.id.btnOk).setOnClickListener(this);
                dialog.findViewById(R.id.btnOk).setTag(R.id.tvRecipeName);
                et = ((TextInputEditText)dialog.findViewById(R.id.etTextInput));
                etLayout = ((TextInputLayout) dialog.findViewById(R.id.inputLayoutTextInput));
                String text = String.valueOf(((TextView)getView().findViewById(R.id.tvRecipeName)).getText());
                if(!text.equals(getContext().getString(R.string.placeholder_recipe_name)))
                    et.setText(text);
                break;
            }
            case R.id.tvRecipeType:
            case R.id.btnEditRecipeType:
            {
                CheflingUtils.showAlertMessage("Not implemented",
                        "This feature is not implemented",getContext());
                break;
            }
            case R.id.tvBeginner:
            {
                selectButton(v);
                break;
            }
            case R.id.tvSousChef:
            {
                selectButton(v);
                break;
            }
            case R.id.tvMaster:
            {
                selectButton(v);
                break;
            }
            case R.id.tvServes:
            case R.id.btnEditServes:
            {
                dialog = getDialog(R.layout.dialog_number_input);
                dialog.findViewById(R.id.btnCancel).setOnClickListener(this);
                dialog.findViewById(R.id.btnOk).setOnClickListener(this);
                dialog.findViewById(R.id.btnOk).setTag(R.id.tvServes);
                et = ((TextInputEditText)dialog.findViewById(R.id.etTextInput));
                etLayout = ((TextInputLayout) dialog.findViewById(R.id.inputLayoutTextInput));
                String text = String.valueOf(((TextView)getView().findViewById(R.id.tvServes)).getText());
                if(!text.equals("Serves  "))
                    et.setText(text);
                break;
            }
            case R.id.tvCookingTime:
            case R.id.btnEditCookingTime:
            {
                LaunchTimePicker();
                break;
            }
            case R.id.tvNotes:
            case R.id.btnEditNotes:
            {
                dialog = getDialog(R.layout.dialog_bulk_text_input);
                dialog.findViewById(R.id.btnCancel).setOnClickListener(this);
                dialog.findViewById(R.id.btnOk).setOnClickListener(this);
                dialog.findViewById(R.id.btnOk).setTag(R.id.tvNotes);
                et = ((TextInputEditText)dialog.findViewById(R.id.etTextInput));
                etLayout = ((TextInputLayout) dialog.findViewById(R.id.inputLayoutTextInput));
                String text = String.valueOf(((TextView)getView().findViewById(R.id.tvNotes)).getText());
                if(!text.equals(getContext().getString(R.string.placeholder_story)))
                    et.setText(text);
                break;
            }

            //select source dialog click events
            case R.id.btnUseCamera:
            {
                dialog.dismiss();
                captureImage();
                break;
            }
            case R.id.btnUseGallery:
            {
                dialog.dismiss();
                pickImageFromGallery();
                break;
            }

            //preview dialog click events
            case R.id.btnAdd:
            {
                ImageView imgPreview = (ImageView) getView().findViewById(R.id.ivImagePreview);
                if(imgPreview==null)
                {
                    Snackbar.make(getView().getRootView(),
                            "Oops, the image view has been garbage collected!!", Snackbar.LENGTH_LONG).show();
                    return;
                }
                int height = CheflingUtils.ConvertToPx(getContext(),180);
                int width = height;//CheflingUtils.getScreenWidth(getActivity().getWindowManager().getDefaultDisplay());
                SharedPreferenceManager pref = SharedPreferenceManager.getInstance(getContext());
                imgPreview.setBackgroundResource(0);
                imgPreview.setImageBitmap(getBitmap(pref.getKeyData(CheflingUtils.PHOTO_PATH),
                        width,height));


                RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                relativeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                relativeParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                getView().findViewById(R.id.btnCam).setLayoutParams(relativeParams);
                getView().findViewById(R.id.btnCam).requestLayout();
            }
                dialog.dismiss();
                break;
            case R.id.btnCancel:
                dialog.dismiss();
                break;

            //text input dialog events
            case R.id.btnOk:
            {
                int tag = Integer.parseInt(String.valueOf(v.getTag()));
                processInput(tag);
            }
        }
    }

    private void selectButton(View v) {
        int resId[] = { R.id.tvBeginner, R.id.tvSousChef, R.id.tvMaster };
        TextView temp;
        for (int id:resId)
        {
            temp = (TextView)getView().findViewById(id);
            temp.setTextColor(Color.parseColor("#333333"));
            temp.setBackgroundResource(R.drawable.bg_black_border);
        }

        ((TextView)v).setTextColor(Color.WHITE);
        v.setBackgroundResource(R.drawable.bg_green_rect);
    }

    private void processInput(int tag) {
        String input = et.getText().toString();
        switch (tag)
        {
            case R.id.tvRecipeName:
            {
                if(input.length()<2)
                {
                    etLayout.setError("Please enter a valid name");
                    return;
                }
                else
                    etLayout.setError(null);

                TextView tvRecipeName = (TextView)getView().findViewById(tag);
                tvRecipeName.setText(et.getText());
                tvRecipeName.setTextColor(Color.BLACK);
            }
            case R.id.tvNotes:
            {

                if(input.length()<2)
                {
                    etLayout.setError("Please provide more details..");
                    return;
                }
                else
                    etLayout.setError(null);

                TextView tvNotes = (TextView)getView().findViewById(tag);
                tvNotes.setText(et.getText());
                tvNotes.setTextColor(Color.BLACK);
            }
            case R.id.tvServes:
            {
                if(input.length()<1)
                {
                    etLayout.setError("Please enter valid data");
                    return;
                }
                else
                    etLayout.setError(null);

                TextView tvServes = (TextView)getView().findViewById(tag);
                tvServes.setText(et.getText().toString().trim()+"  ");
                tvServes.setTextColor(Color.BLACK);
            }
        }

        dialog.dismiss();
    }

    public void captureImage() {
        // Checking camera availability
        if (!CheflingUtils.isDeviceSupportCamera(getContext())) {
            Snackbar.make(getView().getRootView(), "Sorry! Your device doesn't support camera", Snackbar.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;
        try {
            f = setUpPhotoFile();
            SharedPreferenceManager pref = SharedPreferenceManager.getInstance(getContext());
            pref.saveKeyData(CheflingUtils.PHOTO_PATH,f.getAbsolutePath());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            startActivityForResult(intent, RC_CAMERA);
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            CheflingUtils.showAlertMessage("Oops..", "Could not create file to save pic data",getContext());
        }

    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = new File (
                    Environment.getExternalStorageDirectory()
                            + CheflingUtils.CAMERA_DIR
                            + "chefling"
            );

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "awesome_i_am";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, ".jpg", albumF);
        return imageF;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        SharedPreferenceManager pref = SharedPreferenceManager.getInstance(getContext());
        pref.saveKeyData(CheflingUtils.PHOTO_PATH,f.getAbsolutePath());

        return f;
    }

    private void pickImageFromGallery()
    {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RC_GALLERY);
    }


    public void showImagePickerDialog() {
        dialog = getDialog(R.layout.dialog_select_source);
        dialog.findViewById(R.id.btnUseCamera).setOnClickListener(this);
        dialog.findViewById(R.id.btnUseGallery).setOnClickListener(this);
    }

    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == RC_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                handlePic();
            }
            return;
        }else
        if (requestCode == RC_GALLERY  && null != data) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                SharedPreferenceManager pref = SharedPreferenceManager.getInstance(getContext());
                pref.saveKeyData(CheflingUtils.PHOTO_PATH,getRealPathFromURI(selectedImage));
                handlePic();
            }
            return;
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            // user cancelled Image capture
            Snackbar.make(getView().getRootView(), "User cancelled image capture", Snackbar.LENGTH_LONG).show();
        } else {
            // failed to capture image
            Snackbar.make(getView().getRootView(), "Sorry! Failed to capture image", Snackbar.LENGTH_LONG).show();
        }
    }

    private void handlePic() {
        SharedPreferenceManager pref = SharedPreferenceManager.getInstance(getContext());
        String mCurrentPhotoPath = pref.getKeyData(CheflingUtils.PHOTO_PATH);
        if (!mCurrentPhotoPath.isEmpty()) {
            previewCapturedImageWithUploadOption();
        }

    }


    public void previewCapturedImageWithUploadOption() {

        dialog = getDialog(R.layout.dialog_captured_photo);
        ImageView imgPreview = (ImageView) dialog.findViewById(R.id.imgPreview);
        Point ScreenSize = CheflingUtils.getWindowSize(getActivity().getWindowManager().getDefaultDisplay());
        int height = ScreenSize.y - CheflingUtils.ConvertToPx(getContext(),90);
        int width =ScreenSize.x - CheflingUtils.ConvertToPx(getContext(),90);
        imgPreview.getLayoutParams().height = height;
        imgPreview.getLayoutParams().width = width;
        SharedPreferenceManager pref = SharedPreferenceManager.getInstance(getContext());
        imgPreview.setImageBitmap(getBitmap(pref.getKeyData(CheflingUtils.PHOTO_PATH),
                width,height));
        dialog.findViewById(R.id.btnAdd).setOnClickListener(this);
        dialog.findViewById(R.id.btnCancel).setOnClickListener(this);
        dialog.show();
    }

    private Bitmap getBitmap(String path, int targetW,int targetH) {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */


		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        return BitmapFactory.decodeFile(path, bmOptions);
    }

    public Dialog getDialog(int id)
    {
        if(dialog==null)
        {
            dialog = new AlertDialog.Builder(getContext()).show();
            dialog.setCancelable(true);
        }
        LayoutInflater inflater = getLayoutInflater(null);
        View view = inflater.inflate(id, null, true);
        dialog.setContentView(view);
        dialog.show();
        dialog.getWindow()
                .clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }



    private void LaunchTimePicker()
    {
        TimePickerFragment newFragment;
        newFragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putInt("hour", 0);
        args.putInt("minute", 30);
        newFragment.setArguments(args);
        TimePickerDialog.OnTimeSetListener onDateSet = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour,
                                  int min) {

                String text = min + "m   ";
                if(hour>0)
                    text = hour + "h, " + text;

                ((TextView)getView().findViewById(R.id.tvCookingTime)).setText(text);
            }
        };
        newFragment.setCallBack(onDateSet);
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }
    public static class TimePickerFragment extends DialogFragment {
        TimePickerDialog.OnTimeSetListener onTimeSet;

        public TimePickerFragment() {
        }

        public void setCallBack(TimePickerDialog.OnTimeSetListener ondate) {
            onTimeSet = ondate;
        }

        private int hour, minute;

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            hour = args.getInt("hour");
            minute = args.getInt("minute");
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            TimePickerDialog mDialog = new TimePickerDialog(getActivity(),

                    onTimeSet, hour, minute, true);
            mDialog.setTitle("Cooking time (hr : min)");
            return mDialog;
        }
    }
}