package com.first_app.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.widget.PullRefreshLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountriesActivity extends AppCompatActivity {

    EditText mEdSearch;
    ListView mListView;
    PullRefreshLayout mPullRefresh;

    BottomNavigationView mBottomNavigation;

    //store api data and then Manipulate when use
    public static List<CountryModel> countryModelsList = new ArrayList<>();
    CountryModel countryModel;
    CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_coutries);

        mEdSearch = (EditText) findViewById(R.id.edSearch);
        mListView = (ListView) findViewById(R.id.listView);

        mPullRefresh = (PullRefreshLayout) findViewById(R.id.pullRefresh);
        mPullRefresh.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        mPullRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FancyToast.makeText(getApplicationContext(), "Cases Updated", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                new AsyncTask<Void, String, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        try {
                            Thread.sleep(1700);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        mPullRefresh.setRefreshing(false);
                    }
                }.execute();
            }
        });

        //Set Countries selected
        mBottomNavigation.setSelectedItemId(R.id.countries);

        //perform item selected listener
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.countries:
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        //back button
        //getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        fetchData();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("position", position));
            }
        });

        mEdSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                countryAdapter.getFilter().filter(s);
                countryAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEdSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEdSearch.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
    }

    public void fetchData() {
        //fetching data from trusted api and check this link in postman working or not.
        String url = "https://corona.lmao.ninja/v2/countries/";

        //volley library using
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //handle getting data in array
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String countryName = jsonObject.getString("country");
                        String cases = jsonObject.getString("cases");
                        String todayCases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todayDeaths = jsonObject.getString("todayDeaths");
                        String recovered = jsonObject.getString("recovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");
                        String casesPerOneMillion = jsonObject.getString("casesPerOneMillion");
                        String deathsPerOneMillion = jsonObject.getString("deathsPerOneMillion");
                        String tests = jsonObject.getString("tests");
                        String testsPerOneMillion = jsonObject.getString("testsPerOneMillion");
                        String continent = jsonObject.getString("continent");

                        //for image because data store in countryinfo...
                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flag = object.getString("flag");

                        countryModel = new CountryModel(flag, countryName, cases, todayCases, deaths, todayDeaths, recovered, active, critical, casesPerOneMillion, deathsPerOneMillion, tests, testsPerOneMillion, continent);
                        countryModelsList.add(countryModel);

//                        countryModelList.add(new CountryModel(object.getString("flag"), jsonObject.getString("country"), jsonObject.getString("cases"),
//                                jsonObject.getString("todayCases"), jsonObject.getString("deaths"),jsonObject.getString("todayDeaths"), jsonObject.getString("recovered"),
//                                jsonObject.getString("active"), jsonObject.getString("critical"),jsonObject.getString("casePerOneMillion"), jsonObject.getString("deathsPerOneMillion"),
//                                jsonObject.getString("tests"),jsonObject.getString("testsPerOneMillion"),jsonObject.getString("continent")));

                    }
                    countryAdapter = new CountryAdapter(AffectedCountriesActivity.this, countryModelsList);

                    //setting adapter on list view
                    mListView.setAdapter(countryAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AffectedCountriesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //request handling
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
