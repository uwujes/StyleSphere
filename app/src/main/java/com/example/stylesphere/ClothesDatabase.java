package com.example.stylesphere;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ClothesDatabase {
    StorageReference mStorageRefLong;
    DatabaseReference mDatabaseRefLong;

    StorageReference mStorageRefShortSleeve;
    DatabaseReference mDatabaseRefShortSleeve;
    StorageReference mStorageRefPants;
    DatabaseReference mDatabaseRefPants;

    StorageReference mStorageRefShorts;
    DatabaseReference mDatabaseRShorts;

    StorageReference mStorageRefSkirts;
    DatabaseReference mDatabaseRefSkirts;

    StorageReference mStorageRefDresses;
    DatabaseReference mDatabaseRefDresses;

    public ClothesDatabase(String userID) {
        mStorageRefLong = FirebaseStorage.getInstance().getReference("longSleeves/" + userID);
        mDatabaseRefLong = FirebaseDatabase.getInstance().getReference("longSleeves/" + userID);

        mStorageRefShortSleeve = FirebaseStorage.getInstance().getReference("shortSleeves/" + userID);
        mDatabaseRefShortSleeve = FirebaseDatabase.getInstance().getReference("shortSleeves/" + userID);

        mStorageRefPants = FirebaseStorage.getInstance().getReference("pants/" + userID);
        mDatabaseRefPants = FirebaseDatabase.getInstance().getReference("pants/" + userID);

        mStorageRefShorts = FirebaseStorage.getInstance().getReference("shorts/" + userID);
        mDatabaseRShorts = FirebaseDatabase.getInstance().getReference("shorts/" + userID);

        mStorageRefSkirts = FirebaseStorage.getInstance().getReference("skirts/" + userID);
        mDatabaseRefSkirts = FirebaseDatabase.getInstance().getReference("skirts/" + userID);

        mStorageRefDresses = FirebaseStorage.getInstance().getReference("dresses/" + userID);
        mDatabaseRefDresses = FirebaseDatabase.getInstance().getReference("dresses/" + userID);
    }

    public void pantsSelector(final ClothesCallback callback) {
        final ArrayList<String> imageUrls = new ArrayList<>();
        mStorageRefPants.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference file : listResult.getItems()) {
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrls.add(uri.toString());
                            // Check if all images have been added before returning
                            if (imageUrls.size() == listResult.getItems().size()) {
                                int index = (int) (Math.random() * imageUrls.size());
                                String url = imageUrls.get(index);
                                callback.onCallback(url);
                            }
                        }
                    });
                }
            }
        });
    }

    public void skirtsSelector(final ClothesCallback callback) {
        final ArrayList<String> imageUrls = new ArrayList<>();
        mStorageRefSkirts.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference file : listResult.getItems()) {
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrls.add(uri.toString());
                            // Check if all images have been added before returning
                            if (imageUrls.size() == listResult.getItems().size()) {
                                int index = (int) (Math.random() * imageUrls.size());
                                String url = imageUrls.get(index);
                                callback.onCallback(url);
                            }
                        }
                    });
                }
            }
        });
    }

    public void shortsSelector(final ClothesCallback callback) {
        final ArrayList<String> imageUrls = new ArrayList<>();
        mStorageRefShorts.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference file : listResult.getItems()) {
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrls.add(uri.toString());
                            // Check if all images have been added before returning
                            if (imageUrls.size() == listResult.getItems().size()) {
                                int index = (int) (Math.random() * imageUrls.size());
                                String url = imageUrls.get(index);
                                callback.onCallback(url);
                            }
                        }
                    });
                }
            }
        });
    }

    public void longSSelector(final ClothesCallback callback) {
        final ArrayList<String> imageUrls = new ArrayList<>();
        mStorageRefLong.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference file : listResult.getItems()) {
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrls.add(uri.toString());
                            // Check if all images have been added before returning
                            if (imageUrls.size() == listResult.getItems().size()) {
                                int index = (int) (Math.random() * imageUrls.size());
                                String url = imageUrls.get(index);
                                callback.onCallback(url);
                            }
                        }
                    });
                }
            }
        });
    }

    public void shortSSelector(final ClothesCallback callback) {
        final ArrayList<String> imageUrls = new ArrayList<>();
        mStorageRefShortSleeve.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference file : listResult.getItems()) {
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrls.add(uri.toString());
                            // Check if all images have been added before returning
                            if (imageUrls.size() == listResult.getItems().size()) {
                                int index = (int) (Math.random() * imageUrls.size());
                                String url = imageUrls.get(index);
                                callback.onCallback(url);
                            }
                        }
                    });
                }
            }
        });
    }

    public void dressSelector(final ClothesCallback callback) {
        final ArrayList<String> imageUrls = new ArrayList<>();
        mStorageRefDresses.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference file : listResult.getItems()) {
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrls.add(uri.toString());
                            // Check if all images have been added before returning
                            if (imageUrls.size() == listResult.getItems().size()) {
                                int index = (int) (Math.random() * imageUrls.size());
                                String url = imageUrls.get(index);
                                callback.onCallback(url);
                            }
                        }
                    });
                }
            }
        });
    }

    public interface ClothesCallback {
        void onCallback(String url);
    }

}

