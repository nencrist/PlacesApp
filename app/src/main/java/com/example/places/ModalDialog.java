package com.example.places;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.places.util.UtilDomi;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class ModalDialog extends DialogFragment implements View.OnClickListener{

    private Button openGaleryBtn;
    private Button openCameraBtn;
    private ImageListener observador;
    private File file;
    public static final int CAMERA_CALLBACK = 12;
    public static final int GALLERY_CALLBACK = 8;

    public ModalDialog() {

    }

    public void setObservador(ImageListener observador){
        this.observador = observador;
    }

    public static ModalDialog newInstance() {
        ModalDialog fragment = new ModalDialog();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_modal_dialog, container, false);
        openCameraBtn = root.findViewById(R.id.openCameraBtn);
        openGaleryBtn = root.findViewById(R.id.openGaleryBtn);
        openGaleryBtn.setOnClickListener(this);
        openCameraBtn.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.openCameraBtn:
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(getActivity().getExternalFilesDir(null) + "/photo.png");
                Uri uri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName(), file);
                i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(i, CAMERA_CALLBACK);
                break;
            case R.id.openGaleryBtn:
                Intent j = new Intent(Intent.ACTION_GET_CONTENT);
                j.setType("image/*");
                startActivityForResult(j, GALLERY_CALLBACK);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_CALLBACK && resultCode == RESULT_OK){
            String path = file.getPath();
            /*Bitmap image = BitmapFactory.decodeFile(file.getPath());
            Bitmap thumbnail = Bitmap.createScaledBitmap(
                    image, image.getWidth()/4, image.getHeight()/4, true
            );*/
            observador.onImageChosen(path);
        }
        else if(requestCode == GALLERY_CALLBACK && resultCode == RESULT_OK){
            Uri uri = data.getData();
            String path = UtilDomi.getPath(getActivity(), uri);
            Log.e(">>>", path+"");
            //Bitmap image = BitmapFactory.decodeFile(path);
            observador.onImageChosen(path);
        }
    }

    public interface ImageListener{
        void onImageChosen(String path);
    }
}