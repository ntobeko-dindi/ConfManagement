package com.ntobeko.confmanagement.ui.postnewsarticle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentPostnewsarticleBinding;
import com.ntobeko.confmanagement.models.LocalDate;
import com.ntobeko.confmanagement.models.NewsArticle;

import java.util.Objects;

public class PostNewsArticleFragment extends Fragment {

    private FragmentPostnewsarticleBinding binding;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPostnewsarticleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        binding.postArticle.setOnClickListener(v -> {
            String title = Objects.requireNonNull(binding.textTitle.getText()).toString().trim();
            String body = Objects.requireNonNull(binding.textBody.getText()).toString().trim();
            String url = Objects.requireNonNull(binding.textLink.getText()).toString().trim();

            if(title.isEmpty() || body.isEmpty()){
                Snackbar.make(root, "Title and Body are both required", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return;
            }
            NewsArticle article = new NewsArticle(title,body, mAuth.getUid(), new LocalDate().getLocalDateTime(), url);
            new FireBaseHelper().addNewsArticle(article, root,getActivity());
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}