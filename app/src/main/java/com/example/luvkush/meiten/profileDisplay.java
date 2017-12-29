package com.example.luvkush.meiten;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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

public class profileDisplay extends AppCompatActivity {
    public String SERVER_URL = "http://192.168.43.36/profile/returnFullProfile.php";
    public String Notification_url = "http://192.168.43.36/profile/processNotification.php";
    TextView name1, enroll, batch, phone, email, preferences;
    String name, notification;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        //System.out.println(name);
        flag = 0;
        final SendData s = new SendData();

        s.execute(name);

        Button b1 = (Button) findViewById(R.id.button8);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText notify = (EditText) findViewById(R.id.notification);
                flag = 1;
                SendData d = new SendData();
                notification = notify.getText().toString();
                d.execute(notification);
            }
        });



    }

    public class SendData extends AsyncTask<String, Void, String> {

        HttpURLConnection client;


        @Override
        protected String doInBackground(String... params) {

            try {

                URL url;
                if (flag == 1)
                    url = new URL(Notification_url);
                else
                    url = new URL(SERVER_URL);
                JSONObject postDataParams = new JSONObject();
                System.out.println(name);
                if (flag == 1) {
                    postDataParams.put("name", name);
                    postDataParams.put("notification",notification);
                }

                else
                    postDataParams.put("name", name);


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
            name1 = (TextView) findViewById(R.id.name);
            enroll = (TextView) findViewById(R.id.enroll);
            batch = (TextView) findViewById(R.id.batch);
            phone = (TextView) findViewById(R.id.phone);
            email = (TextView) findViewById(R.id.email);
            preferences = (TextView) findViewById(R.id.preferences);

            System.out.println(result);

            if (flag == 1) {
                Toast.makeText(profileDisplay.this, result, Toast.LENGTH_LONG).show();
            }

            else {

                try {
                    //JSONObject dummy = new JSONObject(result);
                    JSONArray arr = new JSONArray(result);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        name1.setText("Name : " + obj.getString("Name"));
                        enroll.setText("Enrollment Number : " + obj.getString("Enrollment_no"));
                        batch.setText("Batch : " + obj.getString("Batch"));
                        phone.setText("Phone : " + obj.getString("Phone_no"));
                        email.setText("Email : " + obj.getString("Email"));
                        preferences.setText("Preferences : " + obj.getString("preferences"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(profileDisplay.this, "Oops !", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

}
