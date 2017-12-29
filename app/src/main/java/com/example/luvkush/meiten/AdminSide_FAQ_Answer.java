package com.example.luvkush.meiten;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class AdminSide_FAQ_Answer extends AppCompatActivity {

    public String SERVER_URL = "http://192.168.43.36/Faq/adminAnswer.php";
    String name, answer1;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_side__faq__answer);
        Intent d = getIntent();
        index = d.getIntExtra("position",0);
        //id = Integer.toString(index);
        String question = d.getStringExtra("question");
        System.out.println(index + " " + question);
        TextView ques = (TextView) findViewById(R.id.textView);
        ques.setText(question);


    }

    public void sendAnswer(View v) {

        name = Integer.toString(index);
        EditText answer = (EditText) findViewById(R.id.editText2);
        answer1 = answer.getText().toString();
        //Toast.makeText(getApplicationContext(), name + " is being submitted now.", Toast.LENGTH_SHORT).show();
        SendData s = new SendData();
        s.execute(name,answer1);
    }

    public class SendData extends AsyncTask<String, Void, String> {

        HttpURLConnection client;


        @Override
        protected String doInBackground(String... params) {

            try {

                URL url = new URL(SERVER_URL);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("index", name);
                postDataParams.put("answer", answer1);

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
            return null;
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

            Toast.makeText(AdminSide_FAQ_Answer.this, result, Toast.LENGTH_LONG).show();

        }

    }
}


