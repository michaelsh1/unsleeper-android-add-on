package com.example.unsleeper;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {

    boolean settingsCanWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.settingsCanWrite = Settings.System.canWrite(this);
        if(!this.settingsCanWrite)
        {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            startActivity(intent);
            this.settingsCanWrite = Settings.System.canWrite(this);
        }

    }
}