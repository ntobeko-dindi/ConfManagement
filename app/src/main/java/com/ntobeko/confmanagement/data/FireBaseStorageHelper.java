package com.ntobeko.confmanagement.data;

import android.app.Activity;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ntobeko.confmanagement.models.LoadingDialog;

public class FireBaseStorageHelper {
    private final StorageReference mStorageRef;

    public FireBaseStorageHelper() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void uploadImage(Uri file, Activity activity, String storageRefPath) {
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        StorageReference riversRef = mStorageRef.child(storageRefPath);

        riversRef.putFile(file)
            .addOnSuccessListener(taskSnapshot -> {
                dialog.dismissLoader();
            })
            .addOnFailureListener(exception -> dialog.dismissLoader())
            .addOnProgressListener(snapshot -> {
                double progressPercentage = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                String p = ((int) progressPercentage) + "%";
                //uploadProgress.setText(p);
            }
        );
    }
}
