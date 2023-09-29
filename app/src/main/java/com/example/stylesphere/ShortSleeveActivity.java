package com.example.stylesphere;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.stylesphere.databinding.ActivityShortSleeveBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class ShortSleeveActivity extends AppCompatActivity {

    private ActivityShortSleeveBinding binding;
    ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShortSleeveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        // register the UI widgets with their appropriate IDs
        //IVPreviewImage = findViewById(R.id.IVPreviewImage);

        FloatingActionButton choosePhoto = binding.photos;
        mButtonUpload = findViewById(R.id.button_upload);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("shortSleeves");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("shortSleeves");

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            mImageUri = data.getData();
                            mImageView.setImageURI(mImageUri);
                        } else {
                            Toast.makeText(ShortSleeveActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageUri != null){
                    uploadToFirebase(mImageUri);
                } else  {
                    Toast.makeText(ShortSleeveActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadToFirebase(Uri uri){
        //String caption = uploadCaption.getText().toString();
        final StorageReference imageReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageData dataClass = new ImageData("shortSleeves", uri.toString());
                        String key = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(key).setValue(dataClass);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ShortSleeveActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShortSleeveActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ShortSleeveActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }

}