package uw.ek.musiq;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uw.ek.musiq.R;


public class AdminActivity extends AppCompatActivity {
    static String adminPass;
    private TextInputEditText adminPassET;
    private Button savePassButton;
    public static final String MY_PREFS_NAME = "MyAdminPrefs";
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminPassET = findViewById(R.id.adminPassET);
        savePassButton = findViewById(R.id.savePassButton);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String restoredText = prefs.getString("password", null);
        if (restoredText != null){
            String pass = prefs.getString("password", "No pass defined");
            adminPassET.setText(pass);

        }


        savePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminPassET!= null){
                    adminPass = adminPassET.getText().toString();
                    editor.putString("password", adminPass);
                    editor.apply();

                }

            }
        });
    }



}
