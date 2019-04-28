package com.jonurq.workshoptucuman;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jonurq.workshoptucuman.adapter.MyAdapter;
import com.jonurq.workshoptucuman.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<Item> items;
    MyAdapter myAdapter;
    String TAG = "LOG MercadoLibre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String user = getIntent().getStringExtra("user");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Bienvenido " + user);

        items = new ArrayList<>();


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://workshop-go-utnfrt.herokuapp.com/purchases";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        JSONArray jArray = null;
                        try {
                            jArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < jArray.length(); i++) {
                            try {
                                JSONObject oneObject = jArray.getJSONObject(i);
                                // Pulling items from the array
                                String title = oneObject.getString("title");
                                String amount = oneObject.getString("amount");
                                String id = oneObject.getString("id");
                                String image = oneObject.getString("image");
                                String creation_date = oneObject.getString("creation_date");
                                String status = oneObject.getString("status");


                                ZonedDateTime instant = ZonedDateTime.parse(creation_date);
                                DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm");

                                String fechaConv = instant.format(fecha);
                                String horaConv = instant.format(hora);

                                items.add(new Item(title, "$ " + amount, image, "Status: " + status, id, "Fecha: "+fechaConv+"\nHora: "+horaConv));
                            } catch (JSONException e) {
                                // Oops
                            }
                        }

                        runOnUiThread(new Runnable() {
                            public void run() {
                                myAdapter.notifyDataSetChanged();
                                Log.e(TAG, "Enviando datos a la vista");
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error is: " + error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        myAdapter = new MyAdapter(items);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.e(TAG,"CLIKC");
                Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                Item item = (Item) myAdapter.getItem(position);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });


    }


}
