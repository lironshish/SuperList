package com.example.superlist.superlist.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.superlist.R;
import com.example.superlist.superlist.Adapters.ItemAdapter;
import com.example.superlist.superlist.Finals.Keys;
import com.example.superlist.superlist.Firebase.DataManager;
import com.example.superlist.superlist.Objects.Item;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
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
    private MaterialTextView header_TXT_username;
    private CircleImageView header_IMG_user;
    private CircularProgressIndicator header_BAR_progress;
    private DrawerLayout drawer_layout;
    private BottomAppBar panel_AppBar_bottom;

    private Bundle bundle;
    private String currentListName;
    private String currentListSerialNumber;

    private ArrayList<Item> myItems = new ArrayList();
    private ItemAdapter adapter;

    private MaterialToolbar panel_Toolbar_Top;
    private MenuItem add_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        if (getIntent().getBundleExtra("Bundle") != null) {
            this.bundle = getIntent().getBundleExtra("Bundle");
            currentListName = bundle.getString("currentListName");
            currentListSerialNumber = bundle.getString("currentListSerialNumber");
        } else {
            this.bundle = new Bundle();
        }

        findViews();
        initButtons();
        add_person.setVisible(true);
        updateUI();
    }

    private void updateUI() {

        panel_Toolbar_Top.setTitle(currentListName);

        DatabaseReference myRef = realtimeDB.getReference(Keys.KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                header_TXT_username.setText(snapshot.child(Keys.KEY_USER_NAME).getValue(String.class));

                Uri myUri = Uri.parse(snapshot.child(Keys.KEY_USER_PROFILE_IMAGE_URL).getValue(String.class));
                Glide.with(MyListActivity.this)
                        .load(myUri)
                        .into(header_IMG_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference userRef = realtimeDB.getReference(Keys.KEY_LISTS).child(currentListSerialNumber).child("items");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myItems = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        String name = child.child(Keys.KEY_ITEM_NAME).getValue(String.class);
                        Float amount = child.child(Keys.KEY_ITEM_AMOUNT).getValue(Float.class);
                        String suffix = child.child("suffix").getValue(String.class);
                        Item tempItem = new Item();
                        tempItem.setName(name);
                        tempItem.setAmount(amount);
                        tempItem.setSuffix(suffix);
                        myItems.add(tempItem);
                    } catch (Exception ex) {
                    }
                }
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
        list_RECYC_items.setLayoutManager(new GridLayoutManager(this, 1));
        list_RECYC_items.setAdapter(adapter);
    }


    private void delete(int position, String name) {

        DatabaseReference listsRef = realtimeDB.getReference(Keys.KEY_LISTS).child(currentListSerialNumber).child(Keys.KEY_ITEMS);
        Query query = listsRef.child(name);
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

        header_TXT_username = header.findViewById(R.id.header_TXT_username);
        header_IMG_user = header.findViewById(R.id.header_IMG_user);
        header_BAR_progress = header.findViewById(R.id.header_BAR_progress);
        toolbar_FAB_add = findViewById(R.id.toolbar_FAB_add);

        list_RECYC_items = findViewById(R.id.list_RECYC_items);

        panel_Toolbar_Top = findViewById(R.id.panel_Toolbar_Top);
        panel_AppBar_bottom = findViewById(R.id.panel_AppBar_bottom);
        drawer_layout = findViewById(R.id.drawer_layout);
        add_person = panel_Toolbar_Top.getMenu().findItem(R.id.add_person);

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

        panel_AppBar_bottom.setNavigationOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(drawer_layout.getForegroundGravity());
            }
        });


        toolbar_FAB_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyListActivity.this, AddItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentListName", currentListName);
                bundle.putString("currentListSerialNumber", currentListSerialNumber);
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
                finish();
            }
        });


        panel_Toolbar_Top.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MyListActivity.this, ShareListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentListName", currentListName);
                bundle.putString("currentListSerialNumber", currentListSerialNumber);
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
                return false;
            }
        });
    }


}