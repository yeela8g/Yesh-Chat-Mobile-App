package com.example.myapplication.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.viewModels.ContactsVM;

public class MainActivity extends AppCompatActivity {
    private ContactsVM contactsViewModel;
    private static final String THEME_PREFS_KEY = "theme_prefs";
    private static final String SELECTED_THEME_KEY = "selected_theme";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the selected theme from SharedPreferences
        sharedPreferences = getSharedPreferences(THEME_PREFS_KEY, MODE_PRIVATE);
        int selectedTheme = sharedPreferences.getInt(SELECTED_THEME_KEY, R.style.LightTheme_MyApplication);
        setTheme(selectedTheme);

        setContentView(R.layout.activity_main);

        contactsViewModel = new ViewModelProvider(this).get(ContactsVM.class); //VM that will contain the contacts list that onActive being updated from the service8
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ChatlistFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);

        // Check if the current fragment is ChatlistFragment
        if (currentFragment instanceof ChatlistFragment) {
            // Call onBackPressed() method in the ChatlistFragment
            ((ChatlistFragment) currentFragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}