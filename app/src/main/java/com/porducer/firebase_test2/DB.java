package com.porducer.firebase_test2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DB<Data_add> {
    private static final String TAG = "DB.java";

    private static FirebaseFirestore db;

    public static FirebaseFirestore getDb() {
        return db;
    }

    public static void setDb(FirebaseFirestore db) {
        DB.db = db;
    }

    public static FirebaseFirestore DB_Connect() {
        db = FirebaseFirestore.getInstance();
        return db;
    }

    public static DocumentReference Data_add_user_regis(Map<String, Object> data) {
//        Map<String, Object> data = new HashMap<>();
//        user.put("Login", "noareyenonar");
//        user.put("Password", "kittiphon");
//        user.put("Date_of_register", "");
        Log.d(TAG,"Step into DB.Data_add_user_regis");
        db.collection("User_data").add(data);
        DocumentReference ref = db.collection("User_data").document();
        Log.d(TAG, "About to retuen user_ref code");
        return ref;

    }

    public static void Data_read(DocumentReference user_ref, final Context mContext, final TextView text) {
        Log.d(TAG, "Step into Data_read");
//        DocumentReference docRef = db.collection("kitti").document("Myname");
        user_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot data_in = task.getResult();
                    String User_string;
                    User_string = data_in.getString("Login") + data_in.getString("Password");
                    if (User_string == null) {
                        text.setText(User_string);
                        Toast.makeText(mContext,User_string,Toast.LENGTH_LONG).show();
                    } else {
                        Log.d(TAG, "Read done");
                    }
                } else {
                    Log.d(TAG, "DB connection failed");
                }
            }
        });
    }
}