package tmtest.tm.com.testtm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<WebsiteModel> dataModels;
    private ListView listView;
    private static CustomAdapter adapter;
    private Calendar myCalendar ;

    private EditText fromEditText;
    private EditText toEditText;
    private String fromDateString;
    private String toDateString;

    private Button filter;
    private Button all;
    private Button ascending;
    private Button desending;
    private Button graph;


    private int selectTag;
    private int filterTag;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDates();
        }

    };
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateDates() {
        String myFormat = "yy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);


        if (selectTag == 1) {
            fromEditText.setText(sdf.format(myCalendar.getTime()));
            Log.d("out",""+sdf.format(myCalendar.getTime()));

        } else {
            Log.d("out",""+sdf.format(myCalendar.getTime()));

            toEditText.setText(sdf.format(myCalendar.getTime()));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.list);
        dataModels= new ArrayList<>();
        fromDateString= "2017-03-02";
        toDateString= "2017-03-04";


        selectTag = 0 ;
        filterTag = 1 ;
        myCalendar = Calendar.getInstance();
        fromEditText= (EditText) findViewById(R.id.fromEditText);

        graph = (Button) findViewById(R.id.graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent myIntent = new Intent(MainActivity.this, Graph.class);
                myIntent.putExtra("mylist", dataModels); //Optional parameters
                MainActivity.this.startActivity(myIntent);


            }
        });


        ascending = (Button) findViewById(R.id.ascending);
        ascending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dataModels = sortArray(dataModels,1);

                listView.notifyAll();


            }
        });



        desending = (Button) findViewById(R.id.desending);
        desending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dataModels = sortArray(dataModels,2);

                listView.notifyAll();

            }
        });
        filter = (Button) findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataModels.clear();
                filterTag = 1;
                new AsyncTaskRunner().execute();


            }
        });


        all = (Button) findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataModels.clear();
                filterTag = 2;

                new AsyncTaskRunner().execute();


            }
        });

        fromEditText.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selectTag = 1;
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        toEditText= (EditText) findViewById(R.id.toEditText);
        toEditText.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectTag = 2;

                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        new AsyncTaskRunner().execute();

    }



    private ArrayList<WebsiteModel> sortArray (ArrayList<WebsiteModel> array , int type) {




        for ( int i=0 ; i< array.size()-1;i++) {


            WebsiteModel item1 = array.get(i);
            WebsiteModel item2 = array.get(i+1);

            if (type == 1) {
                if (item1.total_visits>item2.total_visits) {

                    WebsiteModel temp=  item1;
                    array.add(i,item2);
                    array.add(i+1,temp);

                }
            } else   if (type == 2){
                if (item1.total_visits<item2.total_visits) {

                    WebsiteModel temp=  item1;
                    array.add(i,item2);
                    array.add(i+1,temp);

                }
            }



        }

        return  array;

    }


    private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        private String resp;
        ProgressDialog progressDialog;
        private OkHttpClient client;



        String run() throws IOException {

            String url = "";

            if (filterTag == 1) {
                url = "http://nurulfirzana-001-site1.1tempurl.com/filter.php?start="+fromDateString+"&end="+toDateString;
            } else {

                url = "http://nurulfirzana-001-site1.1tempurl.com/all_data.php";

            }
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        @Override
        protected void onPostExecute(Void result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            client = new OkHttpClient();

            try {
                Log.d("out", run());
                JSONArray jArray = new JSONArray(run());



                for (int i=0 ; i<jArray.length();i++) {

                    JSONObject jObject = (JSONObject) jArray.get(i);


                    dataModels.add(new WebsiteModel(jObject.getInt("id_website"),
                            jObject.getString("website_name"),
                            jObject.getString("visit_date"),
                            jObject.getInt("total_visits")));
                }


                //listView.notifyAll();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        adapter= new CustomAdapter(dataModels,getApplicationContext());

                        listView.setAdapter(adapter);
                        // Stuff that updates the UI

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Wait for a moment");
        }


        @Override
        protected void onProgressUpdate(Void... text) {

        }
    }
}
