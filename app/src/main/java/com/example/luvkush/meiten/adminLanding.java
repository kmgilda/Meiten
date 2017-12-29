package com.example.luvkush.meiten;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class adminLanding extends AppCompatActivity implements View.OnClickListener {



    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing);

        //initializing views

        buttonLogout = (Button) findViewById(R.id.Logout);

        //displaying logged in user name
        //textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);

        button1 = (Button) findViewById(R.id.FAQ);
        button2 = (Button) findViewById(R.id.ViewProfile);
        button3 = (Button) findViewById(R.id.Training);
        button4 = (Button) findViewById(R.id.InsertCompany);
        button5 = (Button) findViewById(R.id.ApprovedProfile);
        button6 = (Button) findViewById(R.id.About);
        button7 = (Button) findViewById(R.id.Contact);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),adminSide_FAQ.class);
                startActivityForResult(intent, 0);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),viewprofile.class);
                startActivityForResult(intent, 0);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),TrainingActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),insertcompany.class);
                startActivityForResult(intent, 0);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),approvedprofile.class);
                startActivityForResult(intent, 0);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),about.class);
                startActivityForResult(intent, 0);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),contact.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
