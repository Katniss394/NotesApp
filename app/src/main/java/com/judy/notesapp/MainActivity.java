package com.judy.notesapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    Button signIn;
    EditText email;
    EditText password;
    String Email,Password;
    ProgressBar spinner;
    UserLoginTask mAuthTask;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SharedPreferences pref;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "hello@example.com:hello", "hi@example.com:hello"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signIn =(Button)findViewById(R.id.email_sign_in_button);
        email=(EditText)findViewById(R.id.email);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        password=(EditText)findViewById(R.id.password);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=email.getText().toString();
                Password=password.getText().toString();

                if (TextUtils.isEmpty(Password)) {
                    password.setError(getString(R.string.error_invalid_password));

                }
                else if (TextUtils.isEmpty(Email)) {
                    email.setError(getString(R.string.error_field_required));

                }

                 else {
                    spinner.setVisibility(View.VISIBLE);
                    mAuthTask = new UserLoginTask(Email, Password);
                    mAuthTask.execute((Void) null);

                }
            }
        });



    }
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {

                    return pieces[1].equals(mPassword);
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            spinner.setVisibility(View.GONE);
            if (success) {
                pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Userid",Email);
                editor.putString("Password",Password);
                editor.commit();
                NotesFragment fragment = new NotesFragment();
                fragmentTransaction.replace(android.R.id.content, fragment);
            } else {
                password.setError(getString(R.string.error_incorrect_password));
                password.requestFocus();
            }
            fragmentTransaction.commit();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            spinner.setVisibility(View.GONE);
        }
    }

}


