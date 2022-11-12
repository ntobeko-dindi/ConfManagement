package com.ntobeko.confmanagement.data;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ntobeko.confmanagement.AuthActivity;
import com.ntobeko.confmanagement.Enums.UserRoles;
import com.ntobeko.confmanagement.models.Login;
import com.ntobeko.confmanagement.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FireBaseHelper{

    private FirebaseAuth mAuth;

    public FireBaseHelper(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void createUser(User user, View view, FragmentActivity startActivity, Context context){
        mAuth.createUserWithEmailAndPassword(user.getLogin().getEmail(), user.getLogin().getPassword())
            .addOnCompleteListener(startActivity, task -> {
                if (task.isSuccessful()) {
                    Map<String, Object> userDetails = new HashMap<>();
                    userDetails.put("user", user);
                    userDetails.put("role", UserRoles.attendee);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("users").document(Objects.requireNonNull(mAuth.getUid()))
                        .set(userDetails)
                        .addOnSuccessListener(aVoid -> {
                            startActivity.startActivity(new Intent(context, AuthActivity.class));
                            startActivity.finish();
                        })
                        .addOnFailureListener(e -> {});
                }else {
                    Snackbar.make(view, "Failed to create user", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
    }

    public boolean isLoggedIn(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    public void login(Login user, View view, FragmentActivity startActivity, Context context, Activity endActivity){
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
            .addOnCompleteListener(startActivity, task -> {
                if (task.isSuccessful()) {
                    startActivity.startActivity(new Intent(context, endActivity.getClass()));
                    startActivity.finish();
                } else {
                    Snackbar.make(view, "No such user is known, Please enter a valid username and password", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
    }

    public void logout(Activity startActivity, Context context, Activity endActivity){
        mAuth.signOut();
        startActivity.startActivity(new Intent(context, endActivity.getClass()));
        startActivity.finish();
    }
}
