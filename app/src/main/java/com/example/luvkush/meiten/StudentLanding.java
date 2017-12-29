package com.example.luvkush.meiten;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.view.MenuItem;
import android.widget.Toast;



public class StudentLanding extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing);

        button1 = (Button) findViewById(R.id.FAQ);
        button2 = (Button) findViewById(R.id.SamplePapers);
        button3 = (Button) findViewById(R.id.ViewJobs);
        button4 = (Button) findViewById(R.id.AboutUs);
        button5 = (Button) findViewById(R.id.ContactUs);
        button6 = (Button) findViewById(R.id.Logout);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentLanding.this, StudentSide_FAQ.class);
                startActivity(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentLanding.this, Student_side_training.class);
                startActivity(i);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StudentLanding.this, "Work in progress..!!", Toast.LENGTH_SHORT).show();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentLanding.this, about.class);
                startActivity(i);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentLanding.this, contact.class);
                startActivity(i);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (StudentLanding.this,MainActivity.class);
                startActivity(i);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent i = new Intent(StudentLanding.this, StudentHome.class);
                        startActivity(i);
                        break;
                    case R.id.navigation:
                        Intent z = new Intent(StudentLanding.this, StudentSide_Notification.class);
                        startActivity(z);
                        break;

                    case R.id.account:
                        Intent intent = new Intent(StudentLanding.this, Student_account.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }
}
