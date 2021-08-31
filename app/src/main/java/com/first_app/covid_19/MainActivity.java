package com.first_app.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leo.simplearcloader.SimpleArcLoader;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView mTxtCases, mTxtRecovered, mTxtCritical, mTxtActive, mTxtTodaysCases, mTotalDeaths, mTxtTodaysDeaths, mTxtAffectedCountries, mTxtViewShortcut;
    ScrollView mScrollview;
    SimpleArcLoader mSimpleArcLoader;
    //    PieChart mPieChart;
    AnimatedPieView mPieChart;
    LottieAnimationView mLottieCovid, mLottieNoInternet;
    CardView mCardViewGraph, mCardViewList, mCardViewShortcut;
    SharedPreferences sharedPreferences;
    View mViewForStyle;
    String countryName;
    String c;

    BottomNavigationView mBottomNavigation;

    private ArrayList<String> mText = new ArrayList<>();
    private ArrayList<String> mImageUrl = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCardViewGraph = (CardView) findViewById(R.id.cardViewGraph);
        mCardViewList = (CardView) findViewById(R.id.cardViewList);
        mCardViewShortcut = (CardView) findViewById(R.id.cardViewShortcut);

        mTxtCases = (TextView) findViewById(R.id.txtCases);
        mTxtRecovered = (TextView) findViewById(R.id.txtRecovered);
        mTxtCritical = (TextView) findViewById(R.id.txtCritical);
        mTxtActive = (TextView) findViewById(R.id.txtActive);
        mTxtTodaysCases = (TextView) findViewById(R.id.txtTodayCases);
        mTotalDeaths = (TextView) findViewById(R.id.txtTotalDeath);
        mTxtTodaysDeaths = (TextView) findViewById(R.id.txtTodaysDeaths);
        mTxtAffectedCountries = (TextView) findViewById(R.id.txtAffectedCountries);
        mTxtViewShortcut = (TextView) findViewById(R.id.txtViewShortcut);
        mSimpleArcLoader = (SimpleArcLoader) findViewById(R.id.loader);
        mScrollview = (ScrollView) findViewById(R.id.scrollView);
        mViewForStyle = (View) findViewById(R.id.viewForStyle);

//        Bottom Navigation
        mBottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);

//        mPieChart = (PieChart) findViewById(R.id.pieChart);
        mPieChart = (AnimatedPieView) findViewById(R.id.pieChart);

//        mLottieCovid = (LottieAnimationView) findViewById(R.id.lottieCovid);
        mLottieNoInternet = (LottieAnimationView) findViewById(R.id.lottieNoInternet);

//        mLottieCovid.playAnimation();
//        mLottieCovid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mLottieCovid.setVisibility(View.GONE);
//
        mCardViewGraph.setVisibility(View.VISIBLE);
        mCardViewList.setVisibility(View.VISIBLE);
        mCardViewShortcut.setVisibility(View.VISIBLE);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        //Set Home selected
        mBottomNavigation.setSelectedItemId(R.id.home);

        //perform item selected listener
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.countries:
                        startActivity(new Intent(getApplicationContext(), AffectedCountriesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        fetchData();
//            }
//        });

    }

    public void fetchData() {
        //fetching data from trusted api and check this link in postman working or not.
        String url = "https://corona.lmao.ninja/v2/all/";

        mSimpleArcLoader.start();

        //volley library using
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //json handler
                    JSONObject jsonObject = new JSONObject(response.toString());

                    //copy names from postman
                    mTxtCases.setText(jsonObject.getString("cases"));
                    mTxtRecovered.setText(jsonObject.getString("recovered"));
                    mTxtCritical.setText(jsonObject.getString("critical"));
                    mTxtActive.setText(jsonObject.getString("active"));
                    mTxtTodaysCases.setText(jsonObject.getString("todayCases"));
                    mTotalDeaths.setText(jsonObject.getString("deaths"));
                    mTxtTodaysDeaths.setText(jsonObject.getString("todayDeaths"));
                    mTxtAffectedCountries.setText(jsonObject.getString("affectedCountries"));

                    //default project pie chart
                    //mTxtCases.getText().toString()) -> get data from above line and change in piechart
//                    mPieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(mTxtCases.getText().toString()), Color.parseColor("#FFA726")));
//                    mPieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(mTxtRecovered.getText().toString()), Color.parseColor("#66BB6A")));
//                    mPieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(mTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
//                    mPieChart.addPieSlice(new PieModel("Active", Integer.parseInt(mTxtActive.getText().toString()), Color.parseColor("#29B6F6")));
//                    mPieChart.startAnimation();

                    //additional pie chart
                    AnimatedPieViewConfig config = new AnimatedPieViewConfig();
                    config.startAngle(90)
                            .addData(new SimplePieInfo(Integer.parseInt(mTxtCases.getText().toString()), Color.parseColor("#54123B"), "Cases"))
                            .addData(new SimplePieInfo(Integer.parseInt(mTxtRecovered.getText().toString()), Color.parseColor("#84142D"), "Recovered"))
                            .addData(new SimplePieInfo(Integer.parseInt(mTotalDeaths.getText().toString()), Color.parseColor("#F64B3C"), "Death"))
                            .addData(new SimplePieInfo(Integer.parseInt(mTxtActive.getText().toString()), Color.parseColor("#29C7AC"), "Active"))
                            .drawText(true)
                            .strokeMode(true)
                            .drawText(true)
                            .autoSize(true)
                            .textSize(30)
                            .animOnTouch(true)
                            .floatShadowRadius(15)
                            .floatExpandSize(15)
                            .focusAlphaType(config.FOCUS_WITH_ALPHA_REV)
                            .focusAlpha(150)
                            .animaPie(true)
                            .duration(2000);
                    mPieChart.applyConfig(config);
                    mPieChart.start();

                    imageRecyclerView();

                    mSimpleArcLoader.stop();
                    mSimpleArcLoader.setVisibility(View.GONE);
                    mScrollview.setVisibility(View.VISIBLE);

                    sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                    countryName = sharedPreferences.getString("countryName", "No Favorite Available");

                    c = countryName;
                    mTxtViewShortcut.setText(c);

                    mTxtViewShortcut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FancyToast.makeText(getApplicationContext(), (String) mTxtViewShortcut.getText(), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();

                            if (c == "No Favorite Available") {
                                FancyToast.makeText(getApplicationContext(), "No Favorite Available", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                            } else {
                                if( mTxtViewShortcut.getText().equals("No")) {
                                    FancyToast.makeText(getApplicationContext(), "why?", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                                }
                                else {
                                    Intent intentShortcut = new Intent(getApplicationContext(), ShortcutActivity.class);
                                    intentShortcut.putExtra("countryName", countryName);
                                    intentShortcut.putExtra("cases", sharedPreferences.getString("cases", ""));
                                    intentShortcut.putExtra("todayCases", sharedPreferences.getString("todayCases", ""));
                                    intentShortcut.putExtra("totalDeath", sharedPreferences.getString("totalDeath", ""));
                                    intentShortcut.putExtra("todayDeath", sharedPreferences.getString("todayDeath", ""));
                                    intentShortcut.putExtra("recovered", sharedPreferences.getString("recovered", ""));
                                    intentShortcut.putExtra("active", sharedPreferences.getString("active", ""));
                                    intentShortcut.putExtra("critical", sharedPreferences.getString("critical", ""));
                                    intentShortcut.putExtra("casesPM", sharedPreferences.getString("casesPM", ""));
                                    intentShortcut.putExtra("deathPM", sharedPreferences.getString("deathPM", ""));
                                    intentShortcut.putExtra("tests", sharedPreferences.getString("tests", ""));
                                    intentShortcut.putExtra("testPM", sharedPreferences.getString("testPM", ""));
                                    intentShortcut.putExtra("continent", sharedPreferences.getString("continent", ""));
                                    startActivity(intentShortcut);
                                }
                            }
                        }
                    });

                    mTxtViewShortcut.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                            alert.setTitle("Application Info")
                                    .setMessage("Details about COVID-19 cases in all over the world.");

                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mTxtViewShortcut.setText("No");
                                    sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                                    countryName = sharedPreferences.getString("countryName", "No Favorite Available");

                                }
                            });
                            alert.show();
                            return false;
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                    mSimpleArcLoader.stop();
                    mSimpleArcLoader.setVisibility(View.GONE);
                    mScrollview.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSimpleArcLoader.stop();
                mSimpleArcLoader.setVisibility(View.GONE);
                mScrollview.setVisibility(View.VISIBLE);
//                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                checkConnection();
            }
        });

        //request handling
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (null == activeNetwork) {
            FancyToast.makeText(getApplicationContext(), "NO INTERNET CONNECTION", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            mLottieNoInternet.setVisibility(View.VISIBLE);
            mViewForStyle.setVisibility(View.GONE);
            mCardViewGraph.setVisibility(View.GONE);
            mCardViewList.setVisibility(View.GONE);
            mCardViewShortcut.setVisibility(View.GONE);
        } else {
            fetchData();
        }
    }

    public void imageRecyclerView() {
        mImageUrl.add("https://cdn1.iconfinder.com/data/icons/wash-hands-2/512/43_Corona_hand_hand_sanitizer_sanitizer-256.png");
        mText.add("Wash/Soap your hand frequently with hand sanitizer");

        mImageUrl.add("https://cdn0.iconfinder.com/data/icons/stop-virus-outline-iconset-2/128/ic_dont_touch-256.png");
        mText.add("Avoid touching unnecessary things");

        mImageUrl.add("https://cdn4.iconfinder.com/data/icons/coronavirus-information/128/stay_home_coronovirus-256.png");
        mText.add("Stay home and avoid going outside");

        mImageUrl.add("https://cdn3.iconfinder.com/data/icons/covid-19-coronavirus-protection-and-information-1/512/1-18-512.png");
        mText.add("Keep Atleast 6 feet distance");

        mImageUrl.add("https://cdn1.iconfinder.com/data/icons/smashicons-medical-flat-vol-3/58/143_-_Health_medical_health_healthcare-256.png");
        mText.add("Keep exercising at home and stay healthy");

        mImageUrl.add("https://cdn2.iconfinder.com/data/icons/unigrid-phantom-weather/60/027_032_thermometer_temperature_control_monitoring_indicator_weather-256.png");
        mText.add("measure your body temperature");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView mRecyclerViewSymptoms = (RecyclerView) findViewById(R.id.recyclerViewSymptoms);
        mRecyclerViewSymptoms.setLayoutManager(layoutManager);
        SymptomsRecyclerViewAdapter adapter = new SymptomsRecyclerViewAdapter(this, mText, mImageUrl);
        mRecyclerViewSymptoms.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
