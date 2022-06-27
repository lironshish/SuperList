//package com.example.superlist.superlist.Fragments;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.superlist.R;
//import com.example.superlist.superlist.Activities.MainActivity;
//import com.example.superlist.superlist.Activities.MyListActivity;
//import com.example.superlist.superlist.Adapters.ListAdapter;
//import com.example.superlist.superlist.Finals.Keys;
//import com.example.superlist.superlist.Objects.List;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//
//public class HomePageFragment extends Fragment {
//
//    private AppCompatActivity activity;
//
//
//    private RecyclerView main_RECYC_lists;
//    private ListAdapter adapter;
//
//    public HomePageFragment() {
//        // Required empty public constructor
//    }
//    public Fragment setActivity(AppCompatActivity activity){
//        this.activity=activity;
//        return this;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_page_fragment, container, false);
//
//        findViews(view);
//        return view;
//    }
//
//    private void findViews(View view) {
//        main_RECYC_lists =  view.findViewById(R.id.main_RECYC_lists);
//
//    }
//
//
//
//    private void initAdapter() {
//
//        adapter = new ListAdapter(this, myLists, new ListAdapter.ListListener() {
//            @Override
//            public void clicked(List list, int position) {
//                currentListName = list.getTitle();
//                currentListSerialNumber = list.getSerialNumber();
//
//
//                Log.d("pttt", currentListName + " main");
//
//                Intent intent = new Intent(MainActivity.this, MyListActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("currentListName",currentListName);
//                bundle.putString("currentListSerialNumber",currentListSerialNumber);
//                intent.putExtra("Bundle",bundle);
////                Log.d("pttt",list.toString());
//                startActivity(intent);
//
//            }
//
//            @Override
//            public void longClick(List list, int position) {
//                delete(position, list.getTitle());
//            }
//        });
//        main_RECYC_lists.setLayoutManager(new GridLayoutManager(this,1));
//        main_RECYC_lists.setAdapter(adapter);
//    }
//
//    private void delete(int position, String name) {
//        // creating a variable for our Database
//        // Reference for Firebase.
//        DatabaseReference dbref= realtimeDB.getReference(Keys.KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lists");
//        // we are use add listerner
//        // for event listener method
//        // which is called with query.
//        Query query=dbref.child(name);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // remove the value at reference
//                snapshot.getRef().removeValue();
//                //dataManager.removeList(position);
//                adapter.notifyItemRemoved(position);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//    }
//
//
//
//
//
//
//
//
//}