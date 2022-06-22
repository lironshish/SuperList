package com.example.superlist.superlist.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.superlist.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private NavigationView nav_view;
    private DrawerLayout drawer_layout;
    private BottomAppBar panel_AppBar_bottom;
    private MaterialToolbar panel_Toolbar_Top;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(panel_AppBar_bottom);
        setSupportActionBar(panel_Toolbar_Top);

        findViews();
        initButtons();
    }


    private void findViews(){
        panel_Toolbar_Top = findViewById(R.id.panel_Toolbar_Top);
        panel_AppBar_bottom = findViewById(R.id.panel_AppBar_bottom);
        nav_view = findViewById(R.id.nav_view);
        drawer_layout = findViewById(R.id.drawer_layout);

    }


    private void initButtons(){
        panel_AppBar_bottom.setNavigationOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(drawer_layout.getForegroundGravity());
            }
        });
    }
}