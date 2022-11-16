package com.ntobeko.confmanagement.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ntobeko.confmanagement.databinding.FragmentAuthnewsBinding;
import com.ntobeko.confmanagement.databinding.FragmentConferencesBinding;
import com.ntobeko.confmanagement.models.Conference;
import android.view.View;
import android.widget.ListAdapter;
import androidx.fragment.app.FragmentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ntobeko.confmanagement.AuthActivity;
import com.ntobeko.confmanagement.Enums.UserRoles;
import com.ntobeko.confmanagement.databinding.FragmentApprovalsBinding;
import com.ntobeko.confmanagement.databinding.FragmentNewsBinding;
import com.ntobeko.confmanagement.models.AbstractModel;
import com.ntobeko.confmanagement.models.Login;
import com.ntobeko.confmanagement.models.NewsArticle;
import com.ntobeko.confmanagement.models.User;
import com.ntobeko.confmanagement.models.Utilities;


import java.util.ArrayList;
import java.util.HashMap;
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
                    new Utilities().showSnackBar("Failed to create user", view);
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
                    new Utilities().showSnackBar(Objects.requireNonNull(task.getException()).getMessage(), view);
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

        db.collection("articles").document(Objects.requireNonNull(mAuth.getUid()) + "(" + newsArticle.getDatePosted() + ")")
            .set(article)
            .addOnSuccessListener(aVoid -> new Utilities().showSnackBar("Article Posted", view))
            .addOnFailureListener(e -> new Utilities().showSnackBar("Error occurred while posting the article", view));
    }

    public void getArticles(View view, Context context, FragmentNewsBinding binding){
        db.collection("articles")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ArrayList<NewsArticle> articles = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        NewsArticle article = new NewsArticle(
                                Objects.requireNonNull(document.getData().get("title")).toString(),
                                Objects.requireNonNull(document.getData().get("description")).toString(),
                                Objects.requireNonNull(document.getData().get("originatorId")).toString(),
                                Objects.requireNonNull(document.getData().get("datePosted")).toString(),
                                Objects.requireNonNull(document.getData().get("link")).toString()
                        );
                        articles.add(article);
                    }
                    if(articles.isEmpty()){
                        new Utilities().showSnackBar("There are no news to show", view);
                    }
                    ListAdapter listAdapter = new NewsListAdapter(context,articles);
                    binding.listview.setAdapter(listAdapter);
                    binding.listview.setClickable(true);

                } else {
                    new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                }
            });
    }

    public void getAuthNews(View view, Context context, FragmentAuthnewsBinding binding){
        db.collection("articles")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<NewsArticle> articles = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            NewsArticle article = new NewsArticle(
                                    Objects.requireNonNull(document.getData().get("title")).toString(),
                                    Objects.requireNonNull(document.getData().get("description")).toString(),
                                    Objects.requireNonNull(document.getData().get("originatorId")).toString(),
                                    Objects.requireNonNull(document.getData().get("datePosted")).toString(),
                                    Objects.requireNonNull(document.getData().get("link")).toString()
                            );
                            articles.add(article);
                        }
                        if(articles.isEmpty()){
                            new Utilities().showSnackBar("There are no news to show", view);
                        }
                        ListAdapter listAdapter = new NewsListAdapter(context,articles);
                        binding.listview.setAdapter(listAdapter);
                        binding.listview.setClickable(true);

                    } else {
                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                    }
                });
    }
    public void registerForConference(AbstractModel abstractModel, View view){
        Map<String, Object> _abstract = new HashMap<>();
        _abstract.put("researchTopic", abstractModel.getResearchTopic());
        _abstract.put("abstractBody", abstractModel.getAbstractBody());
        _abstract.put("theme", abstractModel.getTheme());
        _abstract.put("conferenceId", abstractModel.getConferenceId());
        _abstract.put("submissionDate", abstractModel.getSubmissionDate());
        _abstract.put("coAuthors", abstractModel.getCoAuthors());
        _abstract.put("status", abstractModel.getStatus().name());

        db.collection("conferenceRegistrations").document(Objects.requireNonNull(mAuth.getUid()))
                .set(_abstract)
                .addOnSuccessListener(aVoid -> new Utilities().showSnackBar("Conference Posted", view))
                .addOnFailureListener(e -> new Utilities().showSnackBar("Error occurred while posting the conference", view));
    }

    public void getAbstracts(View view, Context context, FragmentApprovalsBinding binding){
        db.collection("conferenceRegistrations")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<NewsArticle> abstracts = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            NewsArticle _abstract = new NewsArticle(
                                    Objects.requireNonNull(document.getData().get("title")).toString(),
                                    Objects.requireNonNull(document.getData().get("description")).toString(),
                                    Objects.requireNonNull(document.getData().get("originatorId")).toString(),
                                    Objects.requireNonNull(document.getData().get("datePosted")).toString(),
                                    Objects.requireNonNull(document.getData().get("link")).toString()
                            );
                            abstracts.add(_abstract);
                        }
                        if(abstracts.isEmpty()){
                            new Utilities().showSnackBar("There are no conference registrations to show", view);
                        }
                        ListAdapter listAdapter = new NewsListAdapter(context,abstracts);
                        binding.listview.setAdapter(listAdapter);
                        binding.listview.setClickable(true);

                    } else {
                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                    }
                });
    }

    public void addNewConference(Conference conference, View view){
        db.collection("Conferences").document(Objects.requireNonNull(mAuth.getUid()) + "(" + conference.getCreatedDate() + ")")
                .set(conference)
                .addOnSuccessListener(aVoid -> new Utilities().showSnackBar("Conference Created", view))
                .addOnFailureListener(e -> new Utilities().showSnackBar("Error occurred while creating the conference", view));
    }
    public void getConferences(View view, Context context, FragmentConferencesBinding binding){
        db.collection("Conferences")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Conference> conferences = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Conference conference = new Conference(
                                    Objects.requireNonNull(document.getData().get("name")).toString(),
                                    "Theme : " + Objects.requireNonNull(document.getData().get("theme")),
                                    "Venue : " + Objects.requireNonNull(document.getData().get("venue")),
                                    "Date : " + Objects.requireNonNull(document.getData().get("date")),
                                    "Posted On : " + Objects.requireNonNull(document.getData().get("createdDate"))
                            );
                            conferences.add(conference);
                        }
                        if(conferences.isEmpty()){
                            new Utilities().showSnackBar("There are no conferences to show", view);
                        }
                        ListAdapter listAdapter = new ConferencesListAdapter(context, conferences);
                        binding.listview.setAdapter(listAdapter);
                        binding.listview.setClickable(true);

                    } else {
                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                    }
                });
    }
}
