package in.cognitivo.erpapp;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.SearchView;

import java.util.List;

import in.cognitivo.erpapp.Entity.User;
import in.cognitivo.erpapp.Fragment.PerfilFragment;
import in.cognitivo.erpapp.Fragment.ProductionLineFragment;
import in.cognitivo.erpapp.Utility.SessionManager;


public class MainActivity extends AppCompatActivity implements  NumberPicker.OnValueChangeListener{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                    startPerfil(SessionManager.get(MainActivity.this));

                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                    listLineas(SessionManager.get(MainActivity.this));

                    return true;

            }
            return false;
        }

    };

   /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Redirección al Login
        if (!SessionManager.get(this).isLoggedIn()) {
            Log.e("Respuesta linea", String.valueOf(SessionManager.get(this).isLoggedIn()));
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
       }


        setContentView(R.layout.activity_main);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    public void startPerfil(SessionManager session) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PerfilFragment perfilFragment = new PerfilFragment();



        String name = session.getmPrefs().getString(session.getPrefAffiliateName(),null);
        String id = session.getmPrefs().getString(session.getPrefAffiliateId(),null);
        User user = new User(id,name);



        fragmentTransaction.replace(R.id.content, perfilFragment.newPerfil(user));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void listLineas(SessionManager session){

        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ProductionLineFragment productionLineFragment = new ProductionLineFragment();

        String id = session.getmPrefs().getString(session.getPrefAffiliateId(),null);

        fragmentTransaction.replace(R.id.content, productionLineFragment.newProductionLine(id));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();




    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (getFragmentManager().getBackStackEntryCount() != 0) {
                getFragmentManager().popBackStack();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
