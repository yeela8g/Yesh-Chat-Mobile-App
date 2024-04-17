package com.example.myapplication.UI;

import static kotlin.io.ByteStreamsKt.readBytes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.succeable.Successable;
import com.example.myapplication.viewModels.RegisterVM;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity implements Successable {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText displayNameEditText;
    private ImageView imageView;
    private String profilePic;
    private Button uploadImageButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private RegisterVM registerViewModel;
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

        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.reg_et_username);
        passwordEditText = findViewById(R.id.reg_et_password);
        confirmPasswordEditText = findViewById(R.id.reg_et_confirmPassword);
        displayNameEditText = findViewById(R.id.reg_et_displayName);
        imageView = findViewById(R.id.imageView);
        uploadImageButton = findViewById(R.id.uploadImage_btn);
        Button registerButton = findViewById(R.id.reg_btn);
        Button gotoLoginButton = findViewById(R.id.gotologin_btn);

        uploadImageButton.setOnClickListener(v -> openFileChooser());
        registerButton.setOnClickListener(v -> validateForm());

        registerViewModel = new ViewModelProvider(this).get(RegisterVM.class);
        registerViewModel.setSuccessable(this);

        gotoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void validateForm() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String displayName = displayNameEditText.getText().toString();

        if (username == null || username.length() == 0) {
            showError("Please enter a username");
            return;
        }

        if (username.length() < 3) {
            showError("Please enter at least 3 characters for the username");
            usernameEditText.setText("");
            return;
        }

        if (!Pattern.matches("[a-zA-Z0-9_]{3,}", username)) {
            showError("Please enter a valid username (a-z/A-Z/0-9/_)");
            usernameEditText.setText("");
            return;
        }

        if (password == null || password.length() == 0) {
            showError("Please enter a password");
            return;
        }

        if (password.length() < 5) {
            showError("Please enter at least 5 characters for the password");
            passwordEditText.setText("");
            return;
        }

        if (!Pattern.matches("[a-zA-Z0-9]{5,}", password)) {
            showError("Please enter a valid password (a-z/A-Z/0-9)");
            passwordEditText.setText("");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Password and confirm password do not match");
            confirmPasswordEditText.setText("");
            return;
        }

        if (displayName == null || displayName.length() == 0) {
            showError("Please enter a displayName");
            return;
        }
        registerViewModel.registerUser(username,password,displayName,profilePic);

    }
    @Override
    public void onSuccess() {
        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
        finish(); // Finish the activity and go back to the previous screen - login screen
    }
    @Override
    public void onFail() {
        showError("Registration failed or username taken"); //API call failure (executing/getting response)/usernametaken
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            // Check image size
            long imageSize = getImageSize(imageUri);
            if (imageSize > 1024 * 1024) {
                showError("Image size exceeds 1MB");
                imageView.setImageDrawable(null);
                return;
            }
            String base64Image = "";
            // Read the image data from the content URI
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                byte[] imageBytes = readBytes(inputStream);

                // Encode the image data as Base64
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
                    base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                }

                // Use the base64Image string as needed (e.g., store it in the database)
                // ...
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            //set the imageView to the chosen image
            imageView.setImageURI(imageUri);

            // Set the profilePic variable with the image URI
            profilePic = base64Image;
        }
    }

    private long getImageSize(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
        cursor.moveToFirst();
        long size = cursor.getLong(sizeIndex);
        cursor.close();
        return size;
    }
}
