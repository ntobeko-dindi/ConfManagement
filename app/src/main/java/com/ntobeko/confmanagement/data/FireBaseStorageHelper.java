package com.ntobeko.confmanagement.data;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.models.LoadingDialog;

public class FireBaseStorageHelper {
    private final StorageReference mStorageRef;

    public FireBaseStorageHelper() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void uploadImage(Uri file, Activity activity, String storageRefPath, View view, String docName) {
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        StorageReference riversRef = mStorageRef.child(storageRefPath);

        riversRef.putFile(file)
            .addOnSuccessListener(taskSnapshot -> {
                dialog.dismissLoader();
                TextView textView = view.findViewById(R.id.abstractBody);
                Button button = view.findViewById(R.id.chooseFile);
                TextView url = view.findViewById(R.id.hiddenConfIds);
                TextView url2 = view.findViewById(R.id.downloadProofOfPaymentUrl);


                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(task -> {
                   if(docName.equalsIgnoreCase("payment")){
                       url2.setText(task.getResult().toString());
                   }else{
                       url.setText(task.getResult().toString());
                       textView.setVisibility(View.GONE);
                       button.setVisibility(View.GONE);
                   }
                });
            })
            .addOnFailureListener(exception -> dialog.dismissLoader())
            .addOnProgressListener(snapshot -> {
                double progressPercentage = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                String p = ((int) progressPercentage) + "%";
            }
        );
    }
}
