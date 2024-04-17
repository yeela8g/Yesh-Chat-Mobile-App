package com.example.myapplication.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.utilities.Info;

public class SettingActivity extends AppCompatActivity {
    private RadioGroup radioGroupTheme;
    private Button buttonSaveSettings;
    private Button buttonConnectServer;
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

        setContentView(R.layout.activity_setting);

        radioGroupTheme = findViewById(R.id.radioGroupTheme);
        buttonSaveSettings = findViewById(R.id.buttonSaveSettings);
        buttonSaveSettings.setOnClickListener(v -> {
            applyTheme();
        });

        EditText editTextServerAddress = findViewById(R.id.editTextServerAddress);
        buttonConnectServer = findViewById(R.id.buttonConnectServer);
        buttonConnectServer.setOnClickListener(v -> {
            String serverAddress = editTextServerAddress.getText().toString().trim();
            // Save the server ID and port in the Info class
            Info.baseUrlServer = serverAddress;
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void applyTheme() {
        int selectedRadioButtonId = radioGroupTheme.getCheckedRadioButtonId();
        int selectedTheme = R.style.LightTheme_MyApplication; // Default to Light theme
        if (selectedRadioButtonId == R.id.radioButtonDark) {
            selectedTheme = R.style.DarkTheme2_MyApplication; // Set to Dark theme
        }

        // Save the selected theme to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SELECTED_THEME_KEY, selectedTheme);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        }

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}

