package com.porducer.firebase_test2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.Timestamp;
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
    private static final String EMAIL = "email";
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        final Button button2 = (Button) findViewById(R.id.button2);
        LoginButton buttonFacebookLogin = findViewById(R.id.buttonFacebookLogin);
        buttonFacebookLogin.setPermissions("email", "public_profile");
        textfor = findViewById(R.id.textView);


        db = DB.DB_Connect();
        button.setOnClickListener(Add_user_Listener);
        button2.setOnClickListener(Read_user_Listener);

        mCallbackManager = CallbackManager.Factory.create();
        buttonFacebookLogin.setOnClickListener(FB_Login_Listener);
    }

    private View.OnClickListener Add_user_Listener = new View.OnClickListener(){
        public void onClick(View v){
            Map<String, Object> user = new HashMap<>();
            user.put("Login", "noareyenonar");
            user.put("Password", "kittiphon");
            user.put("Date_of_register",new Timestamp(new Date()));
            Login = (String) user.get("Login");
            //
            user_ref = DB.Data_add_user_regis(user);
            Toast.makeText(Context,"User add DONE",Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener Read_user_Listener = new View.OnClickListener(){
        public void onClick(View v){
            Map<String, Object> user_data = new HashMap<>();
            String User_string = DB.Data_read(user_ref, Context);
            //textfor.setText(User_string);
            Toast.makeText(Context,User_string,Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener FB_Login_Listener = new View.OnClickListener(){
        public void onClick(View v){




        }



}
