package com.first_app.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShortcutActivity extends AppCompatActivity {

    TextView mTxtCountryName, mTxtCases, mTxtTodayCases, mTxtTotalDeath, mTxtTodaysDeath, mTxtRecovered, mTxtActive, mTxtCritical, mTxtCasesPM, mTxtDeathPM, mTxtTests, mTxtTestsPM, mTxtContinent;
    Button mBtnRemove;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);

        Intent intentShortcuts = getIntent();

        getSupportActionBar().setTitle("Details of " + intentShortcuts.getStringExtra("countryName"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mTxtCountryName = findViewById(R.id.txtCountryName);
        mTxtCases = findViewById(R.id.txtCases);
        mTxtTodayCases = findViewById(R.id.txtTodayCases);
        mTxtTotalDeath = findViewById(R.id.txtTotalDeath);
        mTxtTodaysDeath = findViewById(R.id.txtTodaysDeaths);
        mTxtRecovered = findViewById(R.id.txtRecovered);
        mTxtActive = findViewById(R.id.txtActive);
        mTxtCritical = findViewById(R.id.txtCritical);
        mTxtCasesPM = findViewById(R.id.txtCasesPM);
        mTxtDeathPM = findViewById(R.id.txtDeathPM);
        mTxtTests = findViewById(R.id.txtTests);
        mTxtTestsPM = findViewById(R.id.txtTestsPM);
        mTxtContinent = findViewById(R.id.txtContinent);
//        mBtnRemove = findViewById(R.id.btnRemove);

        mTxtCountryName.setText(intentShortcuts.getStringExtra("countryName"));
        mTxtCases.setText(intentShortcuts.getStringExtra("cases"));
        mTxtTodayCases.setText(intentShortcuts.getStringExtra("todayCases"));
        mTxtTotalDeath.setText(intentShortcuts.getStringExtra("totalDeath"));
        mTxtTodaysDeath.setText(intentShortcuts.getStringExtra("todayDeath"));
        mTxtRecovered.setText(intentShortcuts.getStringExtra("recovered"));
        mTxtActive.setText(intentShortcuts.getStringExtra("active"));
        mTxtCritical.setText(intentShortcuts.getStringExtra("critical"));
        mTxtCasesPM.setText(intentShortcuts.getStringExtra("casesPM"));
        mTxtDeathPM.setText(intentShortcuts.getStringExtra("deathPM"));
        mTxtTests.setText(intentShortcuts.getStringExtra("tests"));
        mTxtTestsPM.setText(intentShortcuts.getStringExtra("testPM"));
        mTxtContinent.setText(intentShortcuts.getStringExtra("continent"));

//        mBtnRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sharedPreferences = getSharedPreferences("myRemove",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("countryName","");
//                editor.apply();
//                Intent intentAddToShortcut = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intentAddToShortcut);
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
