package com.ntobeko.confmanagement.data;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ntobeko.confmanagement.AuthActivity;
import com.ntobeko.confmanagement.Enums.ConferenceAttendanceStatus;
import com.ntobeko.confmanagement.Enums.ProposalStatus;
import com.ntobeko.confmanagement.MainActivity;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.databinding.FragmentApprovalsBinding;
import com.ntobeko.confmanagement.databinding.FragmentApprovedBinding;
import com.ntobeko.confmanagement.databinding.FragmentAuthnewsBinding;
import com.ntobeko.confmanagement.databinding.FragmentConferencesBinding;
import com.ntobeko.confmanagement.databinding.FragmentNewsBinding;
import com.ntobeko.confmanagement.databinding.FragmentRegisterBinding;
import com.ntobeko.confmanagement.databinding.FragmentRejectedBinding;
import com.ntobeko.confmanagement.models.AbstractApproval;
import com.ntobeko.confmanagement.models.AbstractModel;
import com.ntobeko.confmanagement.models.Conference;
import com.ntobeko.confmanagement.models.ConferenceAttendance;
import com.ntobeko.confmanagement.models.ConferenceAttendanceApproval;
import com.ntobeko.confmanagement.models.LoadingDialog;
import com.ntobeko.confmanagement.models.Login;
import com.ntobeko.confmanagement.models.NewsArticle;
import com.ntobeko.confmanagement.models.SubmitConferenceAttendance;
import com.ntobeko.confmanagement.models.User;
import com.ntobeko.confmanagement.models.Utilities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public FirebaseAuth getmAuth() {
        return mAuth;
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
                            this.getLoggedInUserRole(view, context);
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
                    this.getLoggedInUserRole(view, context);
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

        db.collection("news").document()
            .set(article)
            .addOnSuccessListener(aVoid -> {new Utilities().showSnackBar("Article Posted", view);dialog.dismissLoader();})
            .addOnFailureListener(e -> {new Utilities().showSnackBar("Error occurred while posting the article", view);dialog.dismissLoader();});
    }

    public void getArticles(View view, Context context, FragmentNewsBinding binding, Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        db.collection("news")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ArrayList<NewsArticle> news = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        NewsArticle article = new NewsArticle(
                                Objects.requireNonNull(document.getData().get("title")).toString(),
                                Objects.requireNonNull(document.getData().get("description")).toString(),
                                Objects.requireNonNull(document.getData().get("originatorId")).toString(),
                                Objects.requireNonNull(document.getData().get("datePosted")).toString(),
                                Objects.requireNonNull(document.getData().get("link")).toString()
                        );
                        news.add(article);
                    }
                    if(news.isEmpty()){
                        new Utilities().showSnackBar("There are no news to show", view);
                    }
                    ListAdapter listAdapter = new NewsListAdapter(context,news);
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
        db.collection("news")
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
    public void submitConferenceAbstract(AbstractModel abstractModel, View view,Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        Map<String, Object> _abstract = new HashMap<>();
        _abstract.put("userId", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        _abstract.put("researchTopic", abstractModel.getResearchTopic());
        _abstract.put("abstractBody", abstractModel.getAbstractBody());
        _abstract.put("theme", abstractModel.getTheme());
        _abstract.put("conferenceId", abstractModel.getConferenceId());
        _abstract.put("submissionDate", abstractModel.getSubmissionDate());
        _abstract.put("coAuthors", abstractModel.getCoAuthors());
        _abstract.put("status", abstractModel.getStatus().name());
        _abstract.put("abstractPdfDownloadUrl", abstractModel.getAbstractPdfDownloadUrl());
        _abstract.put("downloadProofOfPaymentUrl", abstractModel.getDownloadProofOfPaymentUrl());

        db.collection("AbstractRegistrations").document()
                .set(_abstract)
                .addOnSuccessListener(aVoid -> {new Utilities().showSnackBar("Abstract Submitted", view);dialog.dismissLoader();})
                .addOnFailureListener(e -> {new Utilities().showSnackBar("Error occurred while posting the Abstract", view);dialog.dismissLoader();});
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
        db.collection("Conferences").document()
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
                            conference.setConferenceId(document.getId());
                            conference.setAttendanceFee("Attendance Fee : R " + Objects.requireNonNull(document.getData().get("attendanceFee")).toString());
                            conference.setAbstractSubmissionFee("Abstract Submission Fee : R " + Objects.requireNonNull(document.getData().get("abstractSubmissionFee")).toString());
                            conferences.add(conference);
                        }
                        if(conferences.isEmpty()){
                            new Utilities().showSnackBar("There are no conferences to show", view);
                        }

                        ListAdapter listAdapter = new ConferencesListAdapter(context, new ArrayList<>(Utilities.reverseList(conferences)));
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
                            conference.setConferenceId(document.getId());
                            conferences.add(conference);
                        }
                        if(conferences.isEmpty()){
                            new Utilities().showSnackBar("There are no conferences to show", view);
                            dialog.dismissLoader();
                            return;
                        }

                        String[] conf = new String[conferences.size()];
                        StringBuilder ids = new StringBuilder("");

                        for (int i = 0; i < conf.length; i++) {
                            conf[i] = conferences.get(i).getName();
                            ids.append(conferences.get(i).getConferenceId()).append("|");
                        }
                        TextView textView = view.findViewById(R.id.hiddenConfIds);
                        textView.setText(ids.substring(0, ids.toString().length() - 1));

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

    public void submitConferenceAttendance(SubmitConferenceAttendance confAttendance, View view, Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();

        confAttendance.setUserId(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        confAttendance.setStatus(ConferenceAttendanceStatus.AttendeeRegistrationSubmitted);

        db.collection("ConferenceAttendances").document()
                .set(confAttendance)
                .addOnSuccessListener(aVoid -> {new Utilities().showSnackBar("Conference attendance registration submitted", view);dialog.dismissLoader();})
                .addOnFailureListener(e -> {new Utilities().showSnackBar("Error occurred while submitting conference attendance registration", view);dialog.dismissLoader();});
    }

    public void populateCoAuthorsDropdown(View view, Context context, FragmentRegisterBinding binding, Activity activity){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<ConferenceAttendance> attendees = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ConferenceAttendance attendee = new ConferenceAttendance(
                                    //Objects.requireNonNull(document.getData().get("userId")).toString(),
                                    Objects.requireNonNull(document.getData().get("lastName")).toString(),
                                    Objects.requireNonNull(document.getData().get("firstName")).toString()
                                    //ConferenceAttendanceStatus.valueOf(Objects.requireNonNull(document.getData().get("status")).toString()),
                                    //Objects.requireNonNull(document.getData().get("registeredDate")).toString(),
                                    //Objects.requireNonNull(document.getData().get("isCoAuthor")).toString()
                            );
                            if(!Objects.requireNonNull(document.getData().get("email")).toString().equalsIgnoreCase(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())){
                                attendees.add(attendee);
                            }

                        }
                        if(attendees.isEmpty()){
                            new Utilities().showSnackBar("There are no registered attendees to show", view);
                            dialog.dismissLoader();
                            return;
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

    public void getAbstractsPendingApprovals(View view, Context context, Object binding, Activity activity, ProposalStatus proposalStatus, boolean isAttendee){
        LoadingDialog dialog = new LoadingDialog(activity);
        dialog.showLoader();
        db.collection("AbstractRegistrations")
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
                            _abstract.setAbstractPdfDownloadUrl((String) document.getData().get("abstractPdfDownloadUrl"));
                            _abstract.setDownloadProofOfPaymentUrl((String) document.getData().get("downloadProofOfPaymentUrl"));
                            _abstract.setUserId(Objects.requireNonNull(document.getData().get("userId")).toString());

                            if(_abstract.getStatus().name().equalsIgnoreCase(proposalStatus.name())){
                                abstracts.add(_abstract);
                            }
                        }
                        if(isAttendee){
                            for (int i = 0; i < abstracts.size(); i++) {
                                if(!abstracts.get(i).getUserId().equalsIgnoreCase(mAuth.getCurrentUser().getUid())){
                                    abstracts.remove(i);
                                }
                            }
                        }

                        if(abstracts.isEmpty()){
                            new Utilities().showSnackBar("There are no abstracts to show", view);
                        }else{
                            if(proposalStatus.name().equalsIgnoreCase(ProposalStatus.Submitted.name())){
                                FragmentApprovalsBinding bind = (FragmentApprovalsBinding) binding;
                                ListAdapter listAdapter = new ApprovalsListAdapter(context,abstracts, bind, activity);
                                ((FragmentApprovalsBinding) binding).listview.setAdapter(listAdapter);
                                ((FragmentApprovalsBinding) binding).listview.setClickable(true);
                            }else
                                if(proposalStatus.name().equalsIgnoreCase(ProposalStatus.Rejected.name())){
                                FragmentRejectedBinding bind = (FragmentRejectedBinding) binding;
                                ListAdapter listAdapter = new RejectedListAdapter(context,abstracts,activity);
                                ((FragmentRejectedBinding) binding).listview.setAdapter(listAdapter);
                                ((FragmentRejectedBinding) binding).listview.setClickable(true);
                            }else
                                if(proposalStatus.name().equalsIgnoreCase(ProposalStatus.Approved.name())){
                                FragmentApprovedBinding bind = (FragmentApprovedBinding) binding;
                                ListAdapter listAdapter = new ApprovedListAdapter(context,abstracts,activity);
                                ((FragmentApprovedBinding) binding).listview.setAdapter(listAdapter);
                                ((FragmentApprovedBinding) binding).listview.setClickable(true);
                            }
                        }
                        dialog.dismissLoader();
                    } else {
                        dialog.dismissLoader();
                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
                    }
                });
    }

    public void approveAbstract(AbstractApproval conferenceAbstract, View view, String successMsg, String failureMsg, Context context, FragmentApprovalsBinding binding, Activity activity){
        db.collection("ConferenceApprovals").document(Objects.requireNonNull(mAuth.getUid()))
                .set(conferenceAbstract)
                .addOnSuccessListener(aVoid -> {
                    db.collection("AbstractRegistrations").document(conferenceAbstract.getAbstractId()).update("status", conferenceAbstract.getDecisionStatus())
                            .addOnSuccessListener(a -> {new Utilities().showSnackBar(successMsg, view); this.getAbstractsPendingApprovals(view,context,binding,activity,ProposalStatus.Submitted,false);})
                            .addOnFailureListener(b -> new Utilities().showSnackBar(failureMsg, view));
                    new Utilities().showSnackBar(successMsg, view);
                });
    }

    public void approvePdfAbstract(AbstractApproval conferenceAbstract, View view, String successMsg, String failureMsg, Context context, Activity activity){
        db.collection("ConferenceApprovals").document(Objects.requireNonNull(mAuth.getUid()))
            .set(conferenceAbstract)
            .addOnSuccessListener(aVoid -> {
                db.collection("AbstractRegistrations").document(conferenceAbstract.getAbstractId()).update("status", conferenceAbstract.getDecisionStatus())
                        .addOnSuccessListener(a -> {new Utilities().showSnackBar(successMsg, view);})
                        .addOnFailureListener(b -> new Utilities().showSnackBar(failureMsg, view));
                new Utilities().showSnackBar(successMsg, view);
            });
    }

    public void approveConferenceAttendance(ConferenceAttendanceApproval confAttendanceApproval, View view, String successMsg, String failureMsg){
        db.collection("ConferenceAttendanceApprovals").document(Objects.requireNonNull(mAuth.getUid()))
                .set(confAttendanceApproval)
                .addOnSuccessListener(aVoid -> {
                    db.collection("ConferenceAttendances").document(confAttendanceApproval.getAttendanceId()).update("status", confAttendanceApproval.getDecisionStatus())
                            .addOnSuccessListener(a -> new Utilities().showSnackBar(successMsg, view))
                            .addOnFailureListener(b -> new Utilities().showSnackBar(failureMsg, view));
                    new Utilities().showSnackBar(successMsg, view);
                });
    }

    //    public void getConferenceAttendeePendingApprovals(View view, Context context, FragmentApprovalsBinding binding){
//        db.collection("ConferenceAttendances")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        ArrayList<ConferenceAttendance> attendanceApprovalsPending = new ArrayList<>();
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            ConferenceAttendance _pendingAttendeeApproval = new ConferenceAttendance(
//                                    Objects.requireNonNull(document.getData().get("userId")).toString(),
//                                    Objects.requireNonNull(document.getData().get("firstName")).toString(),
//                                    Objects.requireNonNull(document.getData().get("lastName")).toString(),
//                                    ConferenceAttendanceStatus.valueOf(Objects.requireNonNull(document.getData().get("status")).toString()),
//                                    Objects.requireNonNull(document.getData().get("registeredDate")).toString(),
//                                    Objects.requireNonNull(document.getData().get("isCoAuthor")).toString()
//                            );
//                            _pendingAttendeeApproval.setAttendanceId(document.getId());
//                            if(_pendingAttendeeApproval.getStatus().name().equalsIgnoreCase(ConferenceAttendanceStatus.AttendeeRegistrationSubmitted.name())){
//                                attendanceApprovalsPending.add(_pendingAttendeeApproval);
//                            }
//                        }
//                        if(attendanceApprovalsPending.isEmpty()){
//                            new Utilities().showSnackBar("There are no conference attendance approvals to show", view);
//                        }
//                        ListAdapter listAdapter = new ApprovalsListAdapter(context, attendanceApprovalsPending);
//                        binding.listview.setAdapter(listAdapter);
//                        binding.listview.setClickable(true);
//
//                    } else {
//                        new Utilities().showSnackBar("Error getting documents." + task.getException(), view);
//                    }
//                });
//    }
    public void getLoggedInUserRole(View view, Context context){
        String currentUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        db.collection("Users")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.getId().equalsIgnoreCase(currentUserId)){
                            String role = Objects.requireNonNull(document.getData().get("role")).toString();
                            SharedPreferences pref = context.getSharedPreferences("currentUserRole", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("role", role);
                            editor.apply();
                        }
                    }
                } else {
                    new Utilities().showSnackBar(Objects.requireNonNull(task.getException()).getMessage(), view);
                }
            });
    }
}
