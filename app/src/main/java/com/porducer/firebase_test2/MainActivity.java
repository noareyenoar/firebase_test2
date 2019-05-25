package com.porducer.firebase_test2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    Context Context;
    DocumentReference user_ref;
    TextView textfor = (TextView) findViewById(R.id.textView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        final Button button2 = (Button) findViewById(R.id.button2);


        db = DB.DB_Connect();
        button.setOnClickListener(Add_user_Listener);
        button2.setOnClickListener(Read_user_Listener);


    }

    private View.OnClickListener Add_user_Listener = new View.OnClickListener(){
        public void onClick(View v){
            Map<String, Object> user = new HashMap<>();
            user.put("Login", "noareyenonar");
            user.put("Password", "kittiphon");
            user.put("Date_of_register","ServerValue.TIMESTAMP");
            //Add data for use registeration into hashmap
            user_ref = DB.Data_add_user_regis(user);
        }
    };

    private View.OnClickListener Read_user_Listener = new View.OnClickListener(){
        public void onClick(View v){
            Map<String, Object> user_data = new HashMap<>();
            DB.Data_read(user_ref, Context, textfor);
        }
    };



}
