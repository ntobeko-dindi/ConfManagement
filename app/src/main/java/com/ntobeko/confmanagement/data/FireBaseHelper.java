package com.ntobeko.confmanagement.data;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ntobeko.confmanagement.AuthActivity;
import com.ntobeko.confmanagement.Enums.UserRoles;
import com.ntobeko.confmanagement.models.Login;
import com.ntobeko.confmanagement.models.NewsArticle;
import com.ntobeko.confmanagement.models.User;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FireBaseHelper{

    private final FirebaseAuth mAuth;
    FirebaseFirestore db;

    public FireBaseHelper(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
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
                    Snackbar.make(view, Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
    }

    public void logout(Activity startActivity, Context context, Activity endActivity){
        mAuth.signOut();
        startActivity.startActivity(new Intent(context, endActivity.getClass()));
        startActivity.finish();
    }

    public void addNewsArticle(NewsArticle newsArticle, View view){
        Map<String, Object> article = new HashMap<>();
        article.put("title", newsArticle.getTitle());
        article.put("description", newsArticle.getDescription());
        article.put("originatorId", newsArticle.getOriginatorId());
        article.put("datePosted", newsArticle.getDatePosted());
        article.put("link", newsArticle.getLink());

        db.collection("articles").document(Objects.requireNonNull(mAuth.getUid()))
            .set(article)
            .addOnSuccessListener(aVoid -> showSnackBar("Article Posted", view))
            .addOnFailureListener(e -> showSnackBar("Error occurred while posting the article", view));
    }

    public void getArticles(){
        ArrayList<NewsArticle> articles = new ArrayList<>();
        db.collection("articles")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            NewsArticle article = new NewsArticle(
                                    Objects.requireNonNull(document.getData().get("title")).toString(),
                                    Objects.requireNonNull(document.getData().get("description")).toString(),
                                    Objects.requireNonNull(document.getData().get("originatorId")).toString(),
                                    Objects.requireNonNull(document.getData().get("datePosted")).toString(),
                                    Objects.requireNonNull(document.getData().get("link")).toString()
                            );
                            //add to sql lite
                        }
                    } else {
                        System.out.println("Error getting documents." + task.getException());
                    }
                });
    }

    private void showSnackBar(String message, View view){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
