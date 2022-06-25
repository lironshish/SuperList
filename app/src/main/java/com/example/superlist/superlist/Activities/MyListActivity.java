package com.example.superlist.superlist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.superlist.R;
import com.example.superlist.superlist.Adapters.ItemAdapter;
import com.example.superlist.superlist.Adapters.ListAdapter;
import com.example.superlist.superlist.Finals.Keys;
import com.example.superlist.superlist.Firebase.DataManager;
import com.example.superlist.superlist.Objects.Item;
import com.example.superlist.superlist.Objects.List;
import com.example.superlist.superlist.Objects.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyListActivity extends AppCompatActivity {

    private RecyclerView list_RECYC_items;

    //Firebase
    private final DataManager dataManager = DataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    private FloatingActionButton toolbar_FAB_add;
    //nav
    private NavigationView nav_view;
    private View header;
    private FloatingActionButton navigation_header_container_FAB_profile_pic;
    private MaterialTextView header_TXT_username;
    private CircleImageView header_IMG_user;
    private CircularProgressIndicator header_BAR_progress;
    public static final String KEY_PROFILE_PICTURES = "profile_pictures";

    private Bundle bundle;
    private String currentListName;

    private ArrayList<Item> myItems = new ArrayList();
    ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        if (getIntent().getBundleExtra("Bundle") != null){
            this.bundle = getIntent().getBundleExtra("Bundle");
            currentListName = bundle.getString("currentListName");
        } else {
            this.bundle = new Bundle();
        }
        Log.d("pttt", "myListAc "+ currentListName);

        findViews();
        initButtons();
         updateUI(currentListName);
    }

    private void updateUI(String str) {
        Log.d("pttt",""+ FirebaseAuth.getInstance().getCurrentUser().getUid());
        Log.d("pttt",str);

        DatabaseReference listRef = realtimeDB.getReference("users/").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lists").child(str).child("items");

        listRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myItems = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {

                       // String image = child.child("image").getValue(String.class);
                        String name =   child.child("name").getValue(String.class);
                        Item tempItem = new Item();
                        tempItem.setName(name);
                        //tempItem.setImage(image);
                        myItems.add(tempItem);
                    } catch (Exception ex) {}
                }
               // Log.d("pttt",myLists.toString());
                initAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void initAdapter() {
        adapter = new ItemAdapter(this, myItems, new ItemAdapter.itemListener() {
            @Override
            public void clicked(Item item, int position) {

            }

            @Override
            public void longClick(Item item, int position) {
                delete(position, item.getName());

            }
        });
        list_RECYC_items.setLayoutManager(new GridLayoutManager(this,1));
        list_RECYC_items.setAdapter(adapter);
    }


    private void delete(int position, String name) {
        // creating a variable for our Database
        // Reference for Firebase.
        DatabaseReference dbref= realtimeDB.getReference(Keys.KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lists").child(currentListName).child("items");
        // we are use add listerner
        // for event listener method
        // which is called with query.
        Query query=dbref.child(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // remove the value at reference
                snapshot.getRef().removeValue();
                myItems.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void findViews() {
        //nav
        nav_view = findViewById(R.id.nav_view);
        header = nav_view.getHeaderView(0);
        navigation_header_container_FAB_profile_pic = (FloatingActionButton) header.findViewById(R.id.navigation_header_container_FAB_profile_pic);
        header_TXT_username = header.findViewById(R.id.header_TXT_username);
        header_IMG_user = header.findViewById(R.id.header_IMG_user);
        header_BAR_progress = header.findViewById(R.id.header_BAR_progress);
        toolbar_FAB_add = findViewById(R.id.toolbar_FAB_add);

        list_RECYC_items = findViewById(R.id.list_RECYC_items);
    }

    private void initButtons() {

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Toast.makeText(MyListActivity.this, "Profile", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.item2:
                        AuthUI.getInstance()
                                .signOut(MyListActivity.this)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @SuppressLint("RestrictedApi")
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // user is now signed out
                                        startActivity(new Intent(MyListActivity.this, LoginActivity.class));
                                        Toast.makeText(MyListActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                        break;
                }
                return true;
            }

        });
//
//        panel_AppBar_bottom.setNavigationOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                drawer_layout.openDrawer(drawer_layout.getForegroundGravity());
//            }
//        });

        navigation_header_container_FAB_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  editPic();
            }
        });

        header_IMG_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // editPic();
            }
        });

        toolbar_FAB_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyListActivity.this,AddItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentListName",currentListName);
                intent.putExtra("Bundle",bundle);
                startActivity(intent);
                finish();
            }
        });
    }



}