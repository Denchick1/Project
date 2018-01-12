package com.example.root.metr.root;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.root.metr.R;
import com.example.root.metr.databinding.DialogDismissOrderBinding;
import com.example.root.metr.root.stateMainActivity.GarageState;
import com.example.root.metr.root.stateMainActivity.MainActivityState;
import com.example.root.metr.root.stateMainActivity.ProfileState;
import com.example.root.metr.screens.auth.ui.StartLoginActivity;
import com.example.root.metr.screens.order.ui.OrderActivity;
import com.example.root.metr.utils.PermissionManager;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    final int CONTAINER = R.id.cstr_main_m;

    ConstraintLayout constraintLayout;

    MainActivityPresenter mainActivityPresenter;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_garage).setChecked(true);
        constraintLayout=findViewById(R.id.cstr_main_m);
        mainActivityPresenter = new MainActivityPresenter();
        mainActivityPresenter.attachView(this);
        mainActivityPresenter.create();

        PermissionManager permissionManager = new PermissionManager();

        if (!permissionManager.checkLocationEnabled(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Геолокация");
            builder.setMessage("Геолокация выключена. Чтобы работать с заявками, необходимо включить геолокацию");
            builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            });
            builder.create();
            builder.show();
        }
        permissionManager.checkPermissionAndRequestEnabled(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        MainActivityState.getInstance().execute(getSupportFragmentManager(), CONTAINER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setViewGroup(R.id.cstr_main_m);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivityState.getInstance().clear();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
            android.app.AlertDialog alertDialog=builder.create();
            DialogDismissOrderBinding dialogDismissOrderBinding= DataBindingUtil.inflate(alertDialog.getLayoutInflater(),R.layout.dialog_dismiss_order,constraintLayout,false);
            alertDialog.setView(dialogDismissOrderBinding.getRoot());
            dialogDismissOrderBinding.textView2.setText("Вы уверены, что хотите выйти?");
            dialogDismissOrderBinding.btCancelDialogOrder2.setText("Отмена");
            dialogDismissOrderBinding.btConfirmDialogOrder2.setText("Выход");
            alertDialog.show();
            dialogDismissOrderBinding.btConfirmDialogOrder2.setOnClickListener(v -> finish());
            dialogDismissOrderBinding.btCancelDialogOrder2.setOnClickListener(v -> alertDialog.dismiss());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.nav_garage) {
            MainActivityState.getInstance()
                    .setState(new GarageState()).execute(getSupportFragmentManager(), CONTAINER);
        } else if (id == R.id.nav_orders) {
            startActivity(new Intent(this, OrderActivity.class));
        } else if (id == R.id.nav_profile) {
            MainActivityState.getInstance()
                    .setState(new ProfileState()).execute(getSupportFragmentManager(), CONTAINER);
        } else if (id == R.id.nav_exite) {
            mainActivityPresenter.exite();
            startActivity(new Intent(this, StartLoginActivity.class));
            finish();
        }
        return true;
    }

    @Override
    public void hideItemMenuWebPage() {
        navigationView.getMenu().findItem(R.id.nav_web).setVisible(false);
    }

    @Override
    public void showItemMenuWebPage() {
        navigationView.getMenu().findItem(R.id.nav_web).setVisible(true);
    }
}
