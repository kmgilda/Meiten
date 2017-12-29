package com.example.luvkush.meiten;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class StudentSide_Notification extends AppCompatActivity {

    public String SERVER_URL = "http://192.168.43.36/profile/receiveNotification.php";  // url of php script
    public String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_side__notification);


    }

    public void receiveNButton(View v) {

        EditText e1 = (EditText) findViewById(R.id.nameEnter);

        name = e1.getText().toString();

        ReceiveData s = new ReceiveData();
        s.execute(name);
    }


    class ReceiveData extends AsyncTask<String, Void, String> {

        HttpURLConnection client;


        @Override
        protected String doInBackground(String... params) {

            try {

                URL url = new URL(SERVER_URL);
                JSONObject postDataParams = new JSONObject();
                System.out.println(name);
                postDataParams.put("name", name);
                //postDataParams.put("notification", notification);

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

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            EditText a = (EditText) findViewById(R.id.notificationDisplay);
            String notify = null;
            try {
                //JSONObject dummy = new JSONObject(result);
                //JSONArray arr = dummy.getJSONArray("Value");
                //for (int i = 0; i < arr.length();i++)
                //{
                //JSONObject obj = arr.getJSONObject(i);
                //ed.setText("Id : " + obj.getInt("id") + " Name : " + obj.getString("name") + " URL : " + obj.getString("url"));
                JSONObject obj = new JSONObject(result);
                notify = obj.getString("Notification");
                System.out.println(notify);
                if (notify == null)
                    a.setText("No Notifications for you !");
                else
                    a.setText(notify);
                //}

            }
            catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(StudentSide_Notification.this, "Oops !", Toast.LENGTH_LONG).show();
            }


//            NotificationCompat.Builder dummy = new NotificationCompat.Builder(userSide.this)
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(StudentSide_Notification.this)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("Mieten")
                            .setContentText(notify);


// Gets an instance of the NotificationManager service//

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// mNotificationId is a unique integer your app uses to identify the
// notification. For example, to cancel the notification, you can pass its ID
// number to NotificationManager.cancel().
            mNotificationManager.notify(1, mBuilder.build());
        }

    }










}
