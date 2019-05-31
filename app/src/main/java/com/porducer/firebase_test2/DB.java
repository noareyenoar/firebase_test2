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

public class DB {
    private static final String TAG = "DB.java";

    private static FirebaseFirestore db;
    private static String User_string;

    public static FirebaseFirestore DB_Connect() {
        db = FirebaseFirestore.getInstance();
        return db;
    }

    public static DocumentReference Data_add_user_regis(Map<String, Object> data) {
//        Map<String, Object> data = new HashMap<>();
//        user.put("Login", "noareyenonar");

//        user.put("Password", "kittiphon");
//        user.put("Date_of_register", "");
        String Document = (String) data.get("Login");
        //String Collection = (String) "User or Hospital";
        Log.d(TAG,"Step into DB.Data_add_user_regis");
        db.collection("User_data").document(Document).set(data);
        DocumentReference ref = db.collection("User_data").document(Document);
        Log.d(TAG, "About to retuen user_ref code");
        return ref;

    }

    public static String Data_read(DocumentReference user_ref, final Context mContext) {
        Log.d(TAG, "Step into Data_read");

        user_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot data_in = task.getResult();

                    User_string = "Username : " + data_in.getString("Login") + "\n"
                            + "Password : " + data_in.getString("Password");
//                    if (User_string == null) {
//                    } else {
//                        Log.d(TAG, "Read done");
//                    }
//                } else {
//                    Log.d(TAG, "DB connection failed");
                }
            }
        });
        return User_string;

    }

}