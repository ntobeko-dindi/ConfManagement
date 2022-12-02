package com.ntobeko.confmanagement.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.databinding.FragmentAuthnewsBinding;
import com.ntobeko.confmanagement.databinding.FragmentConferencesBinding;
import com.ntobeko.confmanagement.databinding.FragmentRegisterBinding;
import com.ntobeko.confmanagement.models.Conference;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.ntobeko.confmanagement.models.LoadingDialog;
import com.ntobeko.confmanagement.models.Login;
import com.ntobeko.confmanagement.models.NewsArticle;
import com.ntobeko.confmanagement.models.RegisteredAttendees;
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
        LoadingDialog dialog = new LoadingDialog(startActivity);
        dialog.showLoader();
        mAuth.createUserWithEmailAndPassword(user.getLogin().getEmail(), user.getLogin().getPassword())
            .addOnCompleteListener(startActivity, task -> {
                if (task.isSuccessful()) {
                    Map<String, Object> userDetails = new HashMap<>();
                    userDetails.put("firstName", user.getFirstName());
                    userDetails.put("lastName", user.getLastName());
                    userDetails.put("role", user.getUserRole().name());
                    userDetails.put("email", user.getLogin().getEmail());

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Users").document(Objects.requireNonNull(mAuth.getUid()))
                        .set(userDetails)
                        .addOnSuccessListener(aVoid -> {
                            dialog.dismissLoader();
                            startActivity.startActivity(new Intent(context, AuthActivity.class));
                            startActivity.finish();
                        })
                        .addOnFailureListener(e -> dialog.dismissLoader());
                }else {
                    dialog.dismissLoader();
                    new Utilities().showSnackBar("Failed to create user", view);
                }
            });
    }

    public boolean isLoggedIn(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    public void login(Login user, View view, FragmentActivity startActivity, Context context, Activity endActivity){
        LoadingDialog dialog = new LoadingDialog(startActivity);
        dialog.showLoader();
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
            .addOnCompleteListener(startActivity, task -> {
                if (task.isSuccessful()) {
                    dialog.dismissLoader();
                    startActivity.startActivity(new Intent(context, endActivity.getClass()));
                    startActivity.finish();
                } else {
                    dialog.dismissLoader();
                    new Utilities().showSnackBar(Objects.requireNonNull(task.getException()).getMessage(), view);
                }
            });
    }

    public void logout(Activity startActivity, Context context, Activity endActivity){
        mAuth.signOut();
        startActivity.startActivity(new Intent(context, endActivity.getClass()));
        startActivity.finish();
    }

    public void addNewsArticle(NewsArticle newsArticle, View view, Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        Map<String, Object> article = new HashMap<>();
        article.put("title", newsArticle.getTitle());
        article.put("description", newsArticle.getDescription());
        article.put("originatorId", newsArticle.getOriginatorId());
        article.put("datePosted", newsArticle.getDatePosted());
        article.put("link", newsArticle.getLink());

        db.collection("articles").document(Objects.requireNonNull(mAuth.getUid()) + "(" + newsArticle.getDatePosted() + ")")
            .set(article)
            .addOnSuccessListener(aVoid -> {new Utilities().showSnackBar("Article Posted", view);dialog.dismissLoader();})
            .addOnFailureListener(e -> {new Utilities().showSnackBar("Error occurred while posting the article", view);dialog.dismissLoader();});
    }

    public void getArticles(View view, Context context, FragmentNewsBinding binding, Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
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
                    dialog.dismissLoader();
                } else {
                    dialog.dismissLoader();
                    new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                }
            });
    }

    public void getAuthNews(View view, Context context, FragmentAuthnewsBinding binding,Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
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
                        dialog.dismissLoader();
                    } else {
                        dialog.dismissLoader();
                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                    }
                });
    }
    public void registerForConference(AbstractModel abstractModel, View view,Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        Map<String, Object> _abstract = new HashMap<>();
        _abstract.put("researchTopic", abstractModel.getResearchTopic());
        _abstract.put("abstractBody", abstractModel.getAbstractBody());
        _abstract.put("theme", abstractModel.getTheme());
        _abstract.put("conferenceId", abstractModel.getConferenceId());
        _abstract.put("submissionDate", abstractModel.getSubmissionDate());
        _abstract.put("coAuthors", abstractModel.getCoAuthors());
        _abstract.put("status", abstractModel.getStatus().name());

        db.collection("conferenceRegistrations").document(Objects.requireNonNull(mAuth.getUid()) + abstractModel.getConferenceId())
                .set(_abstract)
                .addOnSuccessListener(aVoid -> {new Utilities().showSnackBar("Conference Posted", view);dialog.dismissLoader();})
                .addOnFailureListener(e -> {new Utilities().showSnackBar("Error occurred while posting the conference", view);dialog.dismissLoader();});
    }

    public void getAbstracts(View view, Context context, FragmentApprovalsBinding binding,Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
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
                        dialog.dismissLoader();
                    } else {
                        dialog.dismissLoader();
                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                    }
                });
    }

    public void addNewConference(Conference conference, View view,Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        db.collection("Conferences").document(Objects.requireNonNull(mAuth.getUid()) + "(" + conference.getCreatedDate() + ")")
                .set(conference)
                .addOnSuccessListener(aVoid -> {new Utilities().showSnackBar("Conference Created", view);dialog.dismissLoader();})
                .addOnFailureListener(e -> {new Utilities().showSnackBar("Error occurred while creating the conference", view);dialog.dismissLoader();});
    }
    public void getConferences(View view, Context context, FragmentConferencesBinding binding,Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
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
                        dialog.dismissLoader();
                    } else {
                        dialog.dismissLoader();
                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                    }
                });
    }

    public void populateConferenceDropdown(View view, Context context, FragmentRegisterBinding binding, Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
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

                        String[] conf = new String[conferences.size()];

                        for (int i = 0; i < conf.length; i++) {
                            conf[i] = conferences.get(i).getName();
                        }
                        ArrayAdapter<String> conferenceAdapter = new ArrayAdapter<>(context, R.layout.dropdown_item, conf);
                        conferenceAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        binding.spinnerConference.setAdapter(conferenceAdapter);
                        dialog.dismissLoader();
                    } else {
                        dialog.dismissLoader();
                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                    }
                });
    }

    public void populateCoAuthorsDropdown(View view, Context context, FragmentRegisterBinding binding, Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<RegisteredAttendees> attendees = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            RegisteredAttendees attendee = new RegisteredAttendees(
                                    Objects.requireNonNull(document.getData().get("firstName")).toString(),
                                    Objects.requireNonNull(document.getData().get("lastName")).toString()
                            );
                            if(!Objects.requireNonNull(document.getData().get("email")).toString().equalsIgnoreCase(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())){
                                attendees.add(attendee);
                            }

                        }
                        if(attendees.isEmpty()){
                            new Utilities().showSnackBar("There are no registered attendees to show", view);
                        }

                        String[] att = new String[attendees.size()];

                        for (int i = 0; i < att.length; i++) {
                            att[i] = attendees.get(i).getLastName() + " " + attendees.get(i).getFirstName();
                        }
                        ArrayAdapter<String> coAuthorsAdapter = new ArrayAdapter<>(context, R.layout.dropdown_item, att);
                        coAuthorsAdapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice);
                        binding.spinnerCoAuthors.setAdapter(coAuthorsAdapter);
                        dialog.dismissLoader();
                    } else {
                        dialog.dismissLoader();
                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                    }
                });
    }

    public void getAbstractsAwaitingApproval(View view, Context context, FragmentApprovalsBinding binding, Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        db.collection("conferenceRegistrations")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<AbstractModel> abstracts = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            AbstractModel _abstract = new AbstractModel(
                                    Objects.requireNonNull(document.getData().get("researchTopic")).toString(),
                                    Objects.requireNonNull(document.getData().get("abstractBody")).toString(),
                                    Objects.requireNonNull(document.getData().get("theme")).toString(),
                                    Objects.requireNonNull(document.getData().get("conferenceId")).toString(),
                                    Objects.requireNonNull(document.getData().get("submissionDate")).toString(),
                                    Objects.requireNonNull(document.getData().get("coAuthors")).toString(),
                                    ProposalStatus.valueOf(Objects.requireNonNull(document.getData().get("status")).toString())
                            );
                            _abstract.setAbstractId(document.getId());
                            if(_abstract.getStatus().name().equalsIgnoreCase(ProposalStatus.Submitted.name())){
                                abstracts.add(_abstract);
                            }
                        }
                        if(abstracts.isEmpty()){
                            new Utilities().showSnackBar("There are no abstracts awaiting approval to show", view);
                        }
                        ListAdapter listAdapter = new ApprovalsListAdapter(context,abstracts);
                        binding.listview.setAdapter(listAdapter);
                        binding.listview.setClickable(true);
                        dialog.dismissLoader();

                    } else {
                        dialog.dismissLoader();
                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                    }
                });
    }

    public UserRoles getLoggedInUserRole(Activity activity){
        String email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        assert email != null;
        if(email.equalsIgnoreCase("john.smith@gmail.com"))
            return UserRoles.reviewer;
        return UserRoles.attendee;
    }
}
