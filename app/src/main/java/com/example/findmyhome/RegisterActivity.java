package com.example.findmyhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findmyhome.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements  View.OnClickListener{


        EditText editTextFName,editTextLName,editTextEmail,editTextPassword,editTextConfimPass,
                    editTextPhoneNumber;
        Button BtnSignUp;
        private FirebaseFirestore db = FirebaseFirestore.getInstance();
        private FirebaseAuth Auth;
        private FirebaseAuth.AuthStateListener  authStateListener;
        private FirebaseUser currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextFName = findViewById(R.id.editTextFName);
        editTextLName = findViewById(R.id.editTextLName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfimPass = findViewById(R.id.editTextConfirmPassword);
        editTextPhoneNumber = findViewById(R.id.editTextPhone);

        BtnSignUp = findViewById(R.id.buttonSignUp);

        Auth = FirebaseAuth.getInstance();
        BtnSignUp.setOnClickListener(this);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = Auth.getCurrentUser();

                if(currentUser != null)
                {
                    //User Logged in
                }
                else
                {
                    //User Not logged in
                }
            }
        };





    }

        @Override
        public void onClick(View v) {
            final String FName = editTextFName.getText().toString();
            final String LName = editTextLName.getText().toString();
            final String Email = editTextEmail.getText().toString();
            final String Password = editTextPassword.getText().toString();
            final String Phone = editTextPhoneNumber.getText().toString();

            if(!TextUtils.isEmpty(FName) && !TextUtils.isEmpty(LName)
                    && !TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Password) &&!TextUtils.isEmpty(Phone))
            {
                User user =new User(FName,LName,Email,Password,Phone);

                Auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser Currentuser = Auth.getCurrentUser();
                                    final String currentUserId = Currentuser.getUid();

                                    Map<String, String> UserObj = new HashMap<>();
                                    UserObj.put("userId",currentUserId);
                                    UserObj.put("firstName", FName);
                                    UserObj.put("lastName",LName);
                                    UserObj.put("email",Email);
                                    UserObj.put("PhoneNumber",Phone);

                                    db.collection("Users").add(UserObj)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    documentReference.get()
                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                                    if(task.getResult().exists())
                                                                    {
                                                                        String UserName = task.getResult().getString("firstName") + " "
                                                                                + task.getResult().getString("lastName");
                                                                        String FName = task.getResult().getString("firstName");
                                                                        String LName = task.getResult().getString("lastName");
                                                                        String Email = task.getResult().getString("email");
                                                                        String phone = task.getResult().getString("PhoneNumber");

                                                                        User CUser = new User(currentUserId,FName,LName,Email,phone);

                                                                        Intent intent;
                                                                        intent = new Intent(RegisterActivity.this, MainMenuActivity.class);
                                                                        intent.putExtra("username", (Serializable) CUser);
                                                                        startActivity(intent);
                                                                        //updateUI(CUser);
                                                                    }
                                                                    else
                                                                    {

                                                                    }

                                                                }
                                                            });


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });




                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });



            }
            else
            {
                Toast.makeText(getApplicationContext(),"Enter All The Fields",Toast.LENGTH_SHORT).show();
            }
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = Auth.getCurrentUser();
        Auth.addAuthStateListener(authStateListener);
        //updateUI(CUser
    }

//    private void updateUI(User user) {
//
//        Intent intent = new Intent(this, MainMenuActivity.class);
//        intent.putExtra("username", (Serializable) user);
//        startActivity(intent);
//    }


}
