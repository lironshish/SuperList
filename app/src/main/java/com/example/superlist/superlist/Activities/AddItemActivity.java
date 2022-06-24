package com.example.superlist.superlist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.superlist.R;
import com.example.superlist.superlist.Finals.Keys;
import com.example.superlist.superlist.Firebase.DataManager;
import com.example.superlist.superlist.Objects.Item;
import com.example.superlist.superlist.Objects.List;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {

    //Design elements
    private TextView add_item_TXT_title;
    private MaterialCardView add_item_CRD_card;
    private ShapeableImageView addItem_IMG_pic;
    private TextInputLayout addItem_EDT_name;
    private TextInputLayout addItem_EDT_amount;
    private MaterialButtonToggleGroup toggleButton;
    private MaterialButton addItem_TGBTN_units;
    private MaterialButton addItem_TGBTN_kilo;
    private MaterialButton addItem_BTN_add;

    //DB
    private final DataManager dataManager = DataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    private final String KILO = " KILO";
    private final String UNITS = " UNITS";

    private ArrayList<Item> myItems = new ArrayList();
    private String currentListName;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        if (getIntent().getBundleExtra("Bundle") != null){
            this.bundle = getIntent().getBundleExtra("Bundle");
            currentListName = bundle.getString("currentListName");
        } else {
            this.bundle = new Bundle();
        }

        findViews();
        initButtons();
    }

    private void initButtons() {

        addItem_BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = realtimeDB.getReference(Keys.KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lists");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {

                            try {
                                Item tempItem = new Item();
                                //store item in db
                                myRef.child(currentListName).child("items").child(addItem_EDT_name.getEditText().getText().toString()).child("name").setValue(addItem_EDT_name.getEditText().getText().toString());
                                // myRef.child("items").child(tempItem.getAmountStr()).child("amount").setValue(tempItem.getAmount());
                                // String name =   myRef.child(currentListName).child("items").child("name").getValue(String.class);
                                //  int amount = child.child("items").child("amount").getValue(Integer.class);
                                tempItem.setName(addItem_EDT_name.getEditText().getText().toString());
                                //tempItem.setName(name);
                                //tempItem.setAmount(amount);
                                myItems.add(tempItem);

                            } catch (Exception ex) {
                            }
                        }
                     //   Log.d("pttt",myRef.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(AddItemActivity.this, MyListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentListName",currentListName);
                intent.putExtra("Bundle",bundle);
               // Log.d("pttt",list.toString());
                startActivity(intent);


             //   startActivity(new Intent(AddItemActivity.this, MyListActivity.class));
                finish();
            }
        });

        toggleButton.setSingleSelection(true);
        addItem_TGBTN_kilo.setChecked(true); //default


        addItem_TGBTN_kilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addItem_TGBTN_kilo.isChecked()) {
                    addItem_EDT_amount.setSuffixText(KILO);
                    addItem_EDT_amount.getEditText().setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                }
            }
        });

        addItem_TGBTN_units.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addItem_TGBTN_units.isChecked()) {
                    addItem_EDT_amount.setSuffixText(UNITS);
                    addItem_EDT_amount.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }
        });

    }

    private void findViews() {

        add_item_TXT_title = findViewById(R.id.add_item_TXT_title);
        add_item_CRD_card = findViewById(R.id.add_item_CRD_card);
        addItem_IMG_pic = findViewById(R.id.addItem_IMG_pic);
        addItem_EDT_name = findViewById(R.id.addItem_EDT_name);
        addItem_EDT_amount = findViewById(R.id.addItem_EDT_amount);
        toggleButton = findViewById(R.id.toggleButton);
        addItem_TGBTN_units = findViewById(R.id.addItem_TGBTN_units);
        addItem_TGBTN_kilo = findViewById(R.id.addItem_TGBTN_kilo);
        addItem_BTN_add = findViewById(R.id.addItem_BTN_add);

    }


}