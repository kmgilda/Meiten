package com.example.luvkush.meiten;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    String em, pass;
    String SERVER_URL;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void signInAdmin(View v) {
        flag = 1;
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        em = email.getText().toString();
        pass = password.getText().toString();
        SendData s = new SendData();
        SERVER_URL = "http://192.168.43.36/login/processLogin.php";
        s.execute(em,pass,SERVER_URL);

    }

    public void signInStudent(View v) {
        flag = 2;
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        em = email.getText().toString();
        pass = password.getText().toString();
        SendData s = new SendData();
        SERVER_URL = "http://192.168.43.36/login/processLoginStudent.php";
        s.execute(em,pass,SERVER_URL);

    }




    public class SendData extends AsyncTask<String, Void, String> {

        HttpURLConnection client;


        @Override
        protected String doInBackground(String... params) {

            try {

                URL url = new URL(SERVER_URL);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user",em );
                postDataParams.put("password", pass);

                client = (HttpURLConnection) url.openConnection();
                client.setReadTimeout(15000 /* milliseconds */);
                client.setConnectTimeout(15000 /* milliseconds */);
                client.setRequestMethod("POST");
                client.setDoInput(true);
                client.setDoOutput(true);

                OutputStream os = client.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=client.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            client.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(client != null) // Make sure the connection is not null.
                    client.disconnect();
            }
            return "Default Return Statement";
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while(itr.hasNext()){

                String key= itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            int num = 0;

            num = Integer.parseInt(result);
            if (num == 1) {
                if (flag == 1) {
                    Intent i = new Intent(MainActivity.this, adminLanding.class);
                    startActivity(i);
                } else if (flag == 2) {
                    Intent i = new Intent(MainActivity.this, StudentLanding.class);
                    startActivity(i);
                }

            }
            else
                Toast.makeText(MainActivity.this, "Please enter valid UserName and Password", Toast.LENGTH_LONG).show();



        }

    }

}