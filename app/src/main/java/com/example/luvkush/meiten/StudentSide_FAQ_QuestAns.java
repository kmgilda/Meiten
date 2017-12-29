package com.example.luvkush.meiten;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentSide_FAQ_QuestAns extends AppCompatActivity {

    //public final String url = "http://192.168.137.1/mieten/dummy.php";
    public final String url = "http://192.168.43.36/Faq/adminSide.php";
    //TextView ed;
    final ArrayList<String> list = new ArrayList<String>();
    final ArrayList<String> questionBase = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_side__faq__quest_ans);


        new HttpGetRequest().execute(url);


    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }



    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                System.out.println("Connection is Ok !");
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null

                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
                //Toast.makeText(getApplicationContext(),"Get Request executed Successfully !", Toast.LENGTH_SHORT).show();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
                //Toast.makeText(getApplicationContext(),"Sorry could not process the get request !", Toast.LENGTH_SHORT).show();
            }
            return result;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //ed = (TextView) findViewById(R.id.textView2);
            //ed.setText("  ");
            //ed.setText(result);

            try {
                //JSONObject dummy = new JSONObject(result);
                JSONArray arr = new JSONArray(result);
                int count = 1;
                for (int i = 0; i < arr.length();i++, count++)
                {
                    JSONObject obj = arr.getJSONObject(i);
                    //System.out.println("Question No : " + obj.getInt("Id") + " Question : " + obj.getString("Question") + " Answer : " + obj.getString("Answer"));
                    //ed.append("Question No : " + obj.getInt("Id") + " Question : " + obj.getString("Question") + " Answer : " + obj.getString("Answer") + "\n");
                /*JSONObject obj = new JSONObject(result);
                ed.append("Id : " + obj.getInt("id") + " Name : " + obj.getString("name") + " URL : " + obj.getString("url"));
                ed.append("\n"); */
                    //System.out.println(obj.getString("Question"));
                    list.add(count + " " + obj.getString("Question") +"\n " + obj.getString("Answer"));
                    questionBase.add(obj.getString("Question"));
                    questionBase.add(obj.getString("Answer"));
                }
                //System.out.println(list);
                final ListView listview = (ListView)findViewById(R.id.listview3);
                final StableArrayAdapter adapter = new StableArrayAdapter(StudentSide_FAQ_QuestAns.this,
                        android.R.layout.simple_list_item_1, list);
                listview.setAdapter(adapter);
            }
            catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(StudentSide_FAQ_QuestAns.this, "Oops !", Toast.LENGTH_LONG).show();
            }

        }
    }






}
