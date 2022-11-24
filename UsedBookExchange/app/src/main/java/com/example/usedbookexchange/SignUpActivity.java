package com.example.usedbookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.usedbookexchange.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private ActivitySignUpBinding signUpBinding;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = signUpBinding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            goToMainMenu();

        signUpBinding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaableEditAndSignUp();

                String checkResult = checkAllFields();
                if (checkResult.equals("Everything passed.")) {
                    signUp();
                } else {
                    Toast.makeText(SignUpActivity.this, checkResult, Toast.LENGTH_SHORT).show();
                    enableEditAndSignUp();
                }
            }
        });

        signUpBinding.goToSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void goToMainMenu(){
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void diaableEditAndSignUp(){
        signUpBinding.email.setEnabled(false);
        signUpBinding.password.setEnabled(false);
        signUpBinding.repeatPassowrd.setEnabled(false);
        signUpBinding.signUpButton.setEnabled(false);
    }

    private String checkAllFields(){
        String displayName = signUpBinding.displayName.getText().toString();
        if (displayName.equals(""))
            return "Display name must be filled.";

        String email = signUpBinding.email.getText().toString();
        if (email.equals(""))
            return "Email must be filled.";

        String password = signUpBinding.password.getText().toString();
        if (password.equals(""))
            return "Password must be filled.";
        if (password.length() < 8)
            return "Password must be minimum 8 characters.";

        String repeatPassword = signUpBinding.repeatPassowrd.getText().toString();
        if (!repeatPassword.equals(password))
            return "Password not match.";

        return "Everything passed.";
    }

    private void signUp(){
        String email = signUpBinding.email.getText().toString();
        String password = signUpBinding.password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            setDisplayName(user);
                            goToMainMenu();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            signUpBinding.email.setText("");
                            signUpBinding.password.setText("");
                            signUpBinding.repeatPassowrd.setText("");
                            enableEditAndSignUp();
                        }
                    }
                });
    }

    private void enableEditAndSignUp(){
        signUpBinding.email.setEnabled(true);
        signUpBinding.password.setEnabled(true);
        signUpBinding.repeatPassowrd.setEnabled(true);
        signUpBinding.signUpButton.setEnabled(true);
    }

    private void setDisplayName(FirebaseUser user){
        String displayName = signUpBinding.displayName.getText().toString();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User display name set.");
                        }
                    }
                });
    }
}