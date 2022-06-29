package com.example.superlist.superlist.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.superlist.R;
import com.example.superlist.superlist.Adapters.ListAdapter;
import com.example.superlist.superlist.Finals.Keys;
import com.example.superlist.superlist.Firebase.DataManager;
import com.example.superlist.superlist.Objects.List;
import com.example.superlist.superlist.Objects.User;
import com.firebase.ui.auth.AuthUI;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer_layout;
    private BottomAppBar panel_AppBar_bottom;
    private MaterialToolbar panel_Toolbar_Top;
    private FloatingActionButton toolbar_FAB_add;
    private RecyclerView main_RECYC_lists;

    //Firebase
    private final DataManager dataManager = DataManager.getInstance();
    private final User currentUser = dataManager.getCurrentUser();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    //nav
    private NavigationView nav_view;
    private View header;
    private MaterialTextView header_TXT_username;
    private CircleImageView header_IMG_user;
    private CircularProgressIndicator header_BAR_progress;

    //List adapter
    private ArrayList<List> myLists = new ArrayList();
    private ListAdapter adapter;

    private String currentListName;
    private String currentListSerialNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(panel_AppBar_bottom);
        setSupportActionBar(panel_Toolbar_Top);

        findViews();
        initButtons();
        updateUI();

    }


    private void findViews() {
        panel_Toolbar_Top = findViewById(R.id.panel_Toolbar_Top);
        panel_AppBar_bottom = findViewById(R.id.panel_AppBar_bottom);
        nav_view = findViewById(R.id.nav_view);
        drawer_layout = findViewById(R.id.drawer_layout);
        toolbar_FAB_add = findViewById(R.id.toolbar_FAB_add);
        main_RECYC_lists = findViewById(R.id.main_RECYC_lists);
        //nav
        nav_view = findViewById(R.id.nav_view);
        header = nav_view.getHeaderView(0);
        header_TXT_username = header.findViewById(R.id.header_TXT_username);
        header_IMG_user = header.findViewById(R.id.header_IMG_user);
        header_BAR_progress = header.findViewById(R.id.header_BAR_progress);

    }


    private void initButtons() {

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.item2:
                        AuthUI.getInstance()
                                .signOut(MainActivity.this)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @SuppressLint("RestrictedApi")
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // user is now signed out
                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                        Toast.makeText(MainActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(MainActivity.this, CreateListActivity.class));

            }
        });
    }


    private void updateUI() {

        DatabaseReference myRef = realtimeDB.getReference(Keys.KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                header_TXT_username.setText(snapshot.child(Keys.KEY_USER_NAME).getValue(String.class));

                Uri myUri = Uri.parse(snapshot.child(Keys.KEY_USER_PROFILE_IMAGE_URL).getValue(String.class));
                Glide.with(MainActivity.this)
                        .load(myUri)
                        .into(header_IMG_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference listsRef = realtimeDB.getReference("");
        listsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> temp = new ArrayList<>();
                for (DataSnapshot child : snapshot.child(Keys.KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Keys.KEY_LISTS).getChildren()) {

                    String listStr = child.getValue(String.class);
                    temp.add(listStr);

                }
                for (String id : temp) {
                    DataSnapshot st = snapshot.child(Keys.KEY_LISTS).child(id);
                    List tempList = new List(st.child(Keys.KEY_LIST_TITLE).getValue(String.class), id);
                    tempList.setImage(st.child(Keys.KEY_LIST_IMAGE).getValue(String.class));
                    myLists.add(tempList);
                }

                initAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initAdapter() {

        adapter = new ListAdapter(this, myLists, new ListAdapter.ListListener() {
            @Override
            public void clicked(List list, int position) {
                currentListName = list.getTitle();
                currentListSerialNumber = list.getSerialNumber();

                Intent intent = new Intent(MainActivity.this, MyListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("currentListName", currentListName);
                bundle.putString("currentListSerialNumber", currentListSerialNumber);
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
            }

            @Override
            public void longClick(List list, int position) {
                delete(position, list.getSerialNumber());
            }
        });
        main_RECYC_lists.setLayoutManager(new GridLayoutManager(this, 1));
        main_RECYC_lists.setAdapter(adapter);
    }


    private void delete(int position, String list_id) {
        //delete from lists db
        DatabaseReference listsRef = realtimeDB.getReference(Keys.KEY_LISTS).child(list_id);
        listsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // remove the value at reference
                snapshot.getRef().removeValue();
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        //delete from users db
        DatabaseReference usersRef = realtimeDB.getReference(Keys.KEY_USERS);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userChild : snapshot.getChildren()) {

                    for (DataSnapshot newChild : userChild.child("lists").getChildren()) {
                        if (newChild.getValue(String.class).equals(list_id)) {
                            newChild.getRef().removeValue();
                            adapter.notifyItemRemoved(position);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        myLists.remove(position);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        header_BAR_progress.setVisibility(View.VISIBLE);
        StorageReference storageRef = dataManager.getStorage().getReference().child(Keys.KEY_PROFILE_PICTURES).child(dataManager.getFirebaseAuth().getCurrentUser().getUid());
        if (data != null) {
            Uri uri = data.getData();
            header_IMG_user.setImageURI(uri);

            // Get the data from an ImageView as bytes
            header_IMG_user.setDrawingCacheEnabled(true);
            header_IMG_user.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) header_IMG_user.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();


            UploadTask uploadTask = storageRef.putBytes(bytes);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                User userToStoreNav = dataManager.getCurrentUser();

                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        // If upload was successful, We want to get the image url from the storage
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                currentUser.setProfileImgUrl(uri.toString());
                                //Store the user UID by Phone number
                                DatabaseReference myRef = realtimeDB.getReference(Keys.KEY_USERS).child(userToStoreNav.getUid());
                                myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Image Save in Database Successfully...", Toast.LENGTH_SHORT).show();
                                        } else {
                                            String message = task.getException().getMessage();
                                            Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                        header_BAR_progress.setVisibility(View.INVISIBLE);
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Error: Null Data Received", Toast.LENGTH_SHORT).show();
        }
        // [END upload_memory]
    }
}