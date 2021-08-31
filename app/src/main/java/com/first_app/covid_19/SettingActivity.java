package com.first_app.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigation;
    CardView mAppInfo, mHelp, mFaq, mContact, mAboutMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mAppInfo = (CardView) findViewById(R.id.appInfo);
        mHelp = (CardView) findViewById(R.id.help);
        mFaq = (CardView) findViewById(R.id.faq);
        mContact = (CardView) findViewById(R.id.contact);
        mAboutMe = (CardView) findViewById(R.id.aboutMe);

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        //Set Countries selected
        mBottomNavigation.setSelectedItemId(R.id.settings);


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
                        startActivity(new Intent(getApplicationContext(), AffectedCountriesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        mAppInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(SettingActivity.this);
                alert.setTitle("Application Info")
                        .setMessage("Details about COVID-19 cases in all over the world.");

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
            }
        });

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.who.int/emergencies/diseases/novel-coronavirus-2019");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        mFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.canada.ca/en/sr/srb.html?_charset_=UTF-8&q=&wb-srch-sub=#wb-land");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] TO_EMAIL = {"jaimin7410@gmail.com"};

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto: "));
                i.setType("text/plain");

                i.putExtra(Intent.EXTRA_EMAIL, TO_EMAIL);
                i.putExtra(Intent.EXTRA_SUBJECT, "THIS IS SUBJECT");
                i.putExtra(Intent.EXTRA_TEXT, "THIS IS MESSAGE BODY");
                startActivity(Intent.createChooser(i,"Send Mail..."));

            }
        });

        mAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a = new AlertDialog.Builder(SettingActivity.this);
                a.setTitle("About Me");
                a.setMessage("Hello folks,\nMy name is Jaimin.\nI create this app for COVID-19.\nIt gives you live update of cases all around the world.\nThis app is build only for educational purpose")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                a.show();
            }
        });
    }
}
