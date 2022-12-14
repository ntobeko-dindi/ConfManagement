package com.ntobeko.confmanagement.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ntobeko.confmanagement.AuthActivity;
import com.ntobeko.confmanagement.Enums.UserRoles;
import com.ntobeko.confmanagement.R;
import com.ntobeko.confmanagement.data.FireBaseHelper;
import com.ntobeko.confmanagement.databinding.FragmentLoginBinding;
import com.ntobeko.confmanagement.models.Login;
import com.ntobeko.confmanagement.models.User;
import com.ntobeko.confmanagement.models.Utilities;

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

        //login and sign up
        binding.singIn.setOnClickListener(v -> {
            if(binding.logInLayout.getVisibility() == View.VISIBLE){
                String email = Objects.requireNonNull(binding.eMail.getText()).toString().trim();
                String password = Objects.requireNonNull(binding.passwords.getText()).toString().trim();
                Login login = new Login();
                login.setEmail(email);
                login.setPassword(password);
                if(login.emailValid() || !login.passwordValid()){
                    new Utilities().showSnackBar("Invalid username or password", root);
                    return;
                }
                new FireBaseHelper().login(login, binding.getRoot(), getActivity(), getContext(), new AuthActivity());
            }else{
                User newUser = new User();
                String fName = Objects.requireNonNull(binding.firstName.getText()).toString().trim();
                String lName = Objects.requireNonNull(binding.lastname.getText()).toString().trim();
                String email = Objects.requireNonNull(binding.eMails.getText()).toString().trim();
                String pass = Objects.requireNonNull(binding.passwordss.getText()).toString().trim();
                String confPass = Objects.requireNonNull(binding.passwords01.getText()).toString().trim();

                if(fName.isEmpty() || lName.isEmpty() || email.isEmpty() || pass.isEmpty() || confPass.isEmpty()){
                    new Utilities().showSnackBar("All fields are required", root);
                    return;
                }
                if(!pass.equals(confPass)){
                    new Utilities().showSnackBar("Password Mismatch", root);
                    return;
                }
                newUser.setFirstName(fName);
                newUser.setLastName(lName);
                newUser.setLogin(new Login(email, pass));
                newUser.setUserRole(UserRoles.attendee);

                if(newUser.getLogin().emailValid()){
                    new Utilities().showSnackBar("Invalid email address", root);
                    return;
                }
                new FireBaseHelper().createUser(newUser,getView(),getActivity(),getContext());
            }
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}