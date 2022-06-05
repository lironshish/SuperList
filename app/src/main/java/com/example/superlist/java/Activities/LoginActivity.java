package com.example.superlist.java.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.superlist.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ImageView login_IMG_logo;
    private MaterialButton login_BTN_login;

    //Authentication
    private FirebaseAuth mAuth;

    //DB
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Authentication
        mAuth = FirebaseAuth.getInstance(); //Init
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
        //    reload();
            //TODO: Add function that check if is new user
        }


        findViews();
        initButtons();
        FirebaseDatabase db = FirebaseDatabase.getInstance();


    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result){
        if (result.getResultCode() == RESULT_OK) {
//            panel_BTN_login.setVisibility(View.INVISIBLE);
//            panel_PRG_bar.setVisibility(View.VISIBLE);
            checkIfUserExistInDB();
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }


    private void checkIfUserExistInDB() {

        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

//        // Successfully signed in
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        DocumentReference docRef = db.collection(Keys.KEY_USERS).document(user.getUid());
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    Log.d("pttt", "DocumentSnapshot data: " + documentSnapshot.getData());
////                    MyUser loadedUser = documentSnapshot.toObject(MyUser.class);
////                    MyDataManager.getInstance().setCurrentUser(loadedUser);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                } else {
//                    Log.d("pttt", "No such document");
//                    Log.d("pttt", user.getUid());
//                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
//                }
//                finish();
//            }
//
//        });
    }

    private void login(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.PhoneBuilder().build());


        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_logo)
                .build();
        //signInIntent.addFlags(PendingIntent.FLAG_IMMUTABLE);

        signInLauncher.launch(signInIntent);

    }

    public void findViews(){
        login_IMG_logo = findViewById(R.id.login_IMG_logo);
        login_BTN_login = findViewById(R.id.login_BTN_login);
    }


    public void initButtons() {
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                login();
        //        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

            }
            });
    }
}