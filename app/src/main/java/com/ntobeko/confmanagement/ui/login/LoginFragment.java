package com.ntobeko.confmanagement.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ntobeko.confmanagement.AuthActivity;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.databinding.FragmentLoginBinding;
import com.ntobeko.confmanagement.models.Login;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel homeViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
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

            Object email = binding.eMail.getText();
            Object password = binding.passwords.getText();

            if(email == null || password == null)
                return;

            Login login = new Login();
            login.setEmail(email.toString().trim());
            login.setPassword(password.toString().trim());

            Intent i = new Intent(getContext(), AuthActivity.class);
            startActivity(i);
            requireActivity().finish();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}