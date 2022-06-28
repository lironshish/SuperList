package com.example.superlist.superlist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.superlist.R;
import com.example.superlist.superlist.Firebase.DataManager;
import com.example.superlist.superlist.Objects.List;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;

public class ShareListActivity extends AppCompatActivity {


    private TextView share_TXT_title;
    private TextInputLayout share_EDT_phone;
    private MaterialButton share_BTN_share;
    private LottieAnimationView share_LOT_animation;
    private CountryCodePicker share_CCP_picker;

    //Firebase
    private final DataManager dataManager = DataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    private Bundle bundle;
    private String currentListName;
    private String currentListSerialNumber;
    private String wantedUserPhoneNumber;
    private String wantedUserUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_list);

        if (getIntent().getBundleExtra("Bundle") != null) {
            this.bundle = getIntent().getBundleExtra("Bundle");
            currentListName = bundle.getString("currentListName");
            currentListSerialNumber = bundle.getString("currentListSerialNumber");
        } else {
            this.bundle = new Bundle();
        }

        findViews();
        initViews();
        initButtons();
    }

    private void initViews() {

        //Put get the current Country Selected in the CCP and put the compatible example
        Phonenumber.PhoneNumber example = PhoneNumberUtil.getInstance().getExampleNumberForType(share_CCP_picker.getSelectedCountryNameCode(), PhoneNumberUtil.PhoneNumberType.MOBILE);
        share_EDT_phone.setPlaceholderText(String.valueOf(example.getNationalNumber()));

        //Listen to any change in the CCP and update the placeholder with the compatible example
        share_CCP_picker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                Phonenumber.PhoneNumber example = PhoneNumberUtil.getInstance().getExampleNumberForType(share_CCP_picker.getSelectedCountryNameCode(), PhoneNumberUtil.PhoneNumberType.MOBILE);
                share_EDT_phone.setPlaceholderText(String.valueOf(example.getNationalNumber()));
                Log.d("pttt", "CCP Formatter:" + example);
            }
        });

        //Creating a compatible validator for the phone number using the CCP library built in Validator
        share_EDT_phone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if (!share_CCP_picker.isValidFullNumber()) {
                    share_EDT_phone.setError("Not A valid number");
                } else {
                    share_EDT_phone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initButtons() {
         List sharedList = new List();

        share_BTN_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneTemp =share_EDT_phone.getEditText().getText().toString();
                String newPhone = phoneTemp.replace("-", "");
                wantedUserPhoneNumber = "+972" + newPhone;

                DatabaseReference myRef = realtimeDB.getReference("users/");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Log.d("pttt","L "+ child.getChildren().toString());
                                if(child.child("phoneNumber").getValue().toString().equals(wantedUserPhoneNumber)) {
                                    wantedUserUID = child.child("uid").getValue(String.class);
                                    sharedList.setSerialNumber(currentListSerialNumber);
                                    sharedList.setTitle(currentListName);
                                    shareList(wantedUserUID,sharedList);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void findViews() {

        share_TXT_title = findViewById(R.id.share_TXT_title);
        share_EDT_phone = findViewById(R.id.share_EDT_phone);
        share_BTN_share = findViewById(R.id.share_BTN_share);
        share_LOT_animation = findViewById(R.id.share_LOT_animation);
        share_CCP_picker = findViewById(R.id.share_CCP_picker);
        share_CCP_picker.registerCarrierNumberEditText(share_EDT_phone.getEditText());

    }


    private void shareList(String userSharedUID, List list) {
           DatabaseReference listRef = realtimeDB.getReference("users/").child(userSharedUID).child("lists");
            listRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> myLists = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    myLists.add(dataSnapshot.getValue(String.class)); //save pervious list
                }
                myLists.add(list.getSerialNumber());
                listRef.setValue(myLists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //TODO: Add window "Shared Successful"
    }
}