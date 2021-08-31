package com.first_app.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private int positionCountry;

    TextView mTxtCountryName, mTxtCases, mTxtTodayCases, mTxtTotalDeath, mTxtTodaysDeath, mTxtRecovered, mTxtActive, mTxtCritical, mTxtCasesPM, mTxtDeathPM, mTxtTests, mTxtTestsPM, mTxtContinent;
    Button mBtnShortcut;
    SharedPreferences sharedPreferences;
    String countryName, cases, todayCases, totalDeath, todayDeath, recovered, active, critical, casesPM, deathPM, test, testPM, continent;
    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position", 0);

        getSupportActionBar().setTitle("Details of " + AffectedCountriesActivity.countryModelsList.get(positionCountry).getCountry());
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
        mBtnShortcut = findViewById(R.id.btnShortcut);
        mScrollView = findViewById(R.id.scrollView);

        mScrollView.setVisibility(View.VISIBLE);

        countryName = AffectedCountriesActivity.countryModelsList.get(positionCountry).getCountry();
        cases = AffectedCountriesActivity.countryModelsList.get(positionCountry).getCases();
        todayCases = AffectedCountriesActivity.countryModelsList.get(positionCountry).getTodayCases();
        totalDeath = AffectedCountriesActivity.countryModelsList.get(positionCountry).getDeaths();
        todayDeath = AffectedCountriesActivity.countryModelsList.get(positionCountry).getTodayDeaths();
        recovered = AffectedCountriesActivity.countryModelsList.get(positionCountry).getRecovered();
        active = AffectedCountriesActivity.countryModelsList.get(positionCountry).getActive();
        critical = AffectedCountriesActivity.countryModelsList.get(positionCountry).getCritical();
        casesPM = AffectedCountriesActivity.countryModelsList.get(positionCountry).getCasesPerOneMillion();
        deathPM = AffectedCountriesActivity.countryModelsList.get(positionCountry).getDeathsPerOneMillion();
        test = AffectedCountriesActivity.countryModelsList.get(positionCountry).getTests();
        testPM = AffectedCountriesActivity.countryModelsList.get(positionCountry).getTestsPerOneMillion();
        continent = AffectedCountriesActivity.countryModelsList.get(positionCountry).getContinent();

        mTxtCountryName.setText(countryName);
        mTxtCases.setText(cases);
        mTxtTodayCases.setText(todayCases);
        mTxtTotalDeath.setText(totalDeath);
        mTxtTodaysDeath.setText(todayDeath);
        mTxtRecovered.setText(recovered);
        mTxtActive.setText(active);
        mTxtCritical.setText(critical);
        mTxtCasesPM.setText(casesPM);
        mTxtDeathPM.setText(deathPM);
        mTxtTests.setText(test);
        mTxtTestsPM.setText(testPM);
        mTxtContinent.setText(continent);

        mBtnShortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences = getSharedPreferences("myKey",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("countryName",countryName);
                editor.putString("cases",cases);
                editor.putString("todayCases",todayCases);
                editor.putString("totalDeath",totalDeath);
                editor.putString("todayDeath",todayDeath);
                editor.putString("recovered",recovered);
                editor.putString("active",active);
                editor.putString("critical",critical);
                editor.putString("casesPM",casesPM);
                editor.putString("deathPM",deathPM);
                editor.putString("tests",test);
                editor.putString("testPM",testPM);
                editor.putString("continent", continent);
                editor.apply();
                Intent intentAddToShortcut = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentAddToShortcut);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
