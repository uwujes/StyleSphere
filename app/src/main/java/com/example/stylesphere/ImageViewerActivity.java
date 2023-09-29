package com.example.stylesphere;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stylesphere.databinding.ActivityImageViewerBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageViewerActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button uploadPhoto;
    private Button choosePhoto;

    private ActivityImageViewerBinding binding;

    ActivityResultLauncher<Intent> imageChooserLauncher;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
//    private TextView mTextViewShowUploads;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        binding = ActivityImageViewerBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        uploadPhoto = root.findViewById(R.id.UploadPhoto);
        choosePhoto = root.findViewById(R.id.choosePhoto);

        imageView = root.findViewById(R.id.IVPreviewImage);
//        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mProgressBar = findViewById(R.id.progress_bar);


        mStorageRef = FirebaseStorage.getInstance().getReference("shortSleeves");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shortSleeves");

        imageChooserLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Uri selectedImageUri = data.getData();
                                if (selectedImageUri != null) {
                                    imageView.setImageURI(selectedImageUri);
                                }
                            }
                        }
                    }
                });


        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

//        uploadPhoto.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (mUploadTask != null && mUploadTask.isInProgress()) {
//                    Toast.makeText(ImageViewerActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
//                } else {
//                    uploadFile();
//                }
//            }
//        });
//
//        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    private void imageChooser(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        imageChooserLauncher.launch(Intent.createChooser(i, "Select Picture"));
    }

//    private void registerResult(){
//        resultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        try {
//                            Uri imageUri = result.getData().getData();
//                            imageView.setImageURI(imageUri);
//                        }catch (Exception e) {
//                            Toast.makeText(ImageViewerActivity.this, "NO Image", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//    };


    ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        mImageUri = result;
                        Picasso.get().load(mImageUri).into(imageView);
                    }
                }
            }
    );

//    private String getFileExtension(Uri uri) {
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }
//
//    private void uploadFile() {
//        if (mImageUri != null) {
//            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
//                    + "." + getFileExtension(mImageUri));
//
//            mUploadTask = fileReference.putFile(mImageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Task<Uri> downloadUrlTask = taskSnapshot.getStorage().getDownloadUrl();
//
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mProgressBar.setProgress(0);
//                                }
//                            }, 500);
//
//                            Toast.makeText(ImageViewerActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
//                            ImageData upload = new ImageData("shortSleeves", downloadUrlTask.toString());
//                            String uploadId = mDatabaseRef.push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(upload);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(ImageViewerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            mProgressBar.setProgress((int) progress);
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
//        }
//    }
}