package com.ntobeko.confmanagement.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.singUp.setOnClickListener(v -> {
            binding.logInLayout.setVisibility(View.GONE);
            binding.singUpLayout.setVisibility(View.VISIBLE);
            binding.singUp.setBackgroundResource(R.drawable.switch_trcks);
            binding.singUp.setTextColor(getResources().getColor(R.color.white));
            binding.logIn.setBackgroundResource(R.drawable.switch_tumbs_null);
            binding.logIn.setTextColor(getResources().getColor(R.color.pinkColor));
            binding.singIn.setText(getResources().getText(R.string.sign_up));
        });

        binding.logIn.setOnClickListener(v -> {
            binding.logInLayout.setVisibility(View.VISIBLE);
            binding.singUpLayout.setVisibility(View.GONE);
            binding.logIn.setBackgroundResource(R.drawable.switch_trcks);
            binding.logIn.setTextColor(getResources().getColor(R.color.white));
            binding.singUp.setBackgroundResource(R.drawable.switch_tumbs_null);
            binding.singUp.setTextColor(getResources().getColor(R.color.pinkColor));
            binding.singIn.setText(getResources().getText(R.string.log_in));
        });

        binding.singIn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Logging in...", Toast.LENGTH_LONG).show();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}