package com.example.mynewzakatgold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Homepage extends AppCompatActivity {
    Button button,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        button = (Button) findViewById(R.id.btnZakat);
        button2 = (Button)findViewById(R.id.btnAboutus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent myintent1 = new Intent(Homepage.this, MainActivity.class);
                    startActivity(myintent1);

                }
            }
        );
        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myintent2 = new Intent(Homepage.this, AboutActivity.class);
                startActivity(myintent2);

            }
        });
    }

}
