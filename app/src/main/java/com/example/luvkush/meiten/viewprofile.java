package com.example.luvkush.meiten;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;


public class viewprofile extends AppCompatActivity {
    public String SERVER_URL = "http://192.168.43.36/profile/nameSender.php";
    public String name, searchString;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> dummy = new ArrayList<>();
    String userNames[];
    MyAdapter adapter;
    ListView listview;
    EditText search;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_side_viewprofile);

        HttpGetRequest r = new HttpGetRequest();
        r.execute();

   }


    public class MyAdapter extends BaseAdapter {
        Context context;
        List<String> rowData;


        public MyAdapter(Context context, List<String> items) {
            this.context = context;
            this.rowData = items;
        }
        /*private view holder class*/
        private class ViewHolder {
            TextView txtTitle;

        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.custom_row, null);
                holder = new ViewHolder();
                holder.txtTitle = (TextView) convertView.findViewById(R.id.listDisplayRow);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            String rowItem = getItem(position);
            System.out.println(rowItem);
            holder.txtTitle.setText(rowItem);
            return convertView;
        }
        @Override
        public int getCount() {
            return rowData.size();
        }
        @Override
        public String getItem(int position) {
            return rowData.get(position);
        }
        @Override
        public long getItemId(int position) {
            return rowData.indexOf(getItem(position));
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            list.clear();



            if (charText.length() == 0) {
                list.addAll(dummy);

            } else {


                for (String wp : dummy) {

                    if (wp.toLowerCase(Locale.getDefault()).contains(charText) ) {
                        list.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }










   public class HttpGetRequest extends AsyncTask<String, Void, String>  {
        public static final String REQUEST_METHOD = "POST";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        @Override
        protected String doInBackground(String... params){
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                System.out.println("Entered doInBackground");
                URL myUrl = new URL(SERVER_URL);
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


            System.out.println(result);
            try {

                JSONArray arr = new JSONArray(result);
                int count = 1;
                for (int i = 0; i < arr.length();i++, count++)
                {
                    JSONObject obj = arr.getJSONObject(i);

                    list.add(obj.getString("Name"));
                    dummy.add(obj.getString("Name"));
                }
                System.out.println(list);

                listview = (ListView)findViewById(R.id.listview3);
                userNames = list.toArray(new String[0]);

                adapter = new MyAdapter(viewprofile.this,list);



                listview.setAdapter(adapter);
                listview.setTextFilterEnabled(true);

                b = (Button) findViewById(R.id.SearchButton);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search = (EditText) findViewById(R.id.StudentName);
                        adapter.filter(search.getText().toString());
                    }
                });




                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        System.out.println(position);
                        Intent i = new Intent(viewprofile.this, profileDisplay.class);
                        i.putExtra("name",list.get(position));
                        startActivity(i);
                    }
                });






            }
            catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(viewprofile.this, "Oops !", Toast.LENGTH_LONG).show();
            }





        }
    }

}
