package com.porducer.firebase_test2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;




public class MainActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    Context Context = MainActivity.this;
    DocumentReference user_ref;
    TextView textfor;
    String Login;
    private static final String TAG = "FacebookLogin";
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private TextView mStatusTextView;
    private TextView mDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declare View Object
        final Button button = (Button) findViewById(R.id.button);
        final Button button2 = (Button) findViewById(R.id.button2);
        Button Facebook_Signout = (Button) findViewById(R.id.Facebook_Signout);
        mStatusTextView = findViewById(R.id.Status);
        mDetailTextView = findViewById(R.id.Detail);

        //Facebook Login code
        mAuth = FirebaseAuth.getInstance();
        LoginButton loginButton = findViewById(R.id.buttonFacebookLogin);
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        textfor = findViewById(R.id.textView);


        db = DB.DB_Connect();

        //Button//
        button.setOnClickListener(Add_user_Listener);
        button2.setOnClickListener(Read_user_Listener);
        Facebook_Signout.setOnClickListener(Facebook_Signout_Listener);

    }

    private View.OnClickListener Add_user_Listener = new View.OnClickListener() {
        public void onClick(View v) {
            Map<String, Object> user = new HashMap<>();
            user.put("Login", "noareyenonar");
            user.put("Password", "kittiphon");
            user.put("Date_of_register", new Timestamp(new Date()));
            Login = (String) user.get("Login");

            user_ref = DB.Data_add_user_regis(user);
            Toast.makeText(Context, "User add DONE", Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener Read_user_Listener = new View.OnClickListener() {
        public void onClick(View v) {
            Map<String, Object> user_data = new HashMap<>();
            String User_string = DB.Data_read(user_ref, Context);
            //textfor.setText(User_string);
            Toast.makeText(Context, User_string, Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener Facebook_Signout_Listener = new View.OnClickListener() {
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.Facebook_Signout) {
                signOut();
            }
        }
    };

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUI(null);
    }

    //FB Login code
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        // FB Login Code
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            mStatusTextView.setText(user.getDisplayName());
            mDetailTextView.setText(user.getUid());

            //findViewById(R.id.buttonFacebookLogin).setVisibility(View.GONE);
            findViewById(R.id.Facebook_Signout).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText("Signout Already");
            mDetailTextView.setText(null);

            findViewById(R.id.buttonFacebookLogin).setVisibility(View.VISIBLE);
            //findViewById(R.id.Facebook_Signout).setVisibility(View.GONE);
        }
    }



}
