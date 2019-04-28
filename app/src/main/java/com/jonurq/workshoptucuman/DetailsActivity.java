package com.jonurq.workshoptucuman;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DetailsActivity extends AppCompatActivity {
    String TAG = "LOG MercadoLibre";


    TextView TVtitle, TVstatus, TVamount, TVuserLastName, TVuserName, TVuserDni, TVuserID,TVFechaUser;
    ImageView IVimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        String id = getIntent().getStringExtra("id");
        Log.e(TAG, "ID=" + id);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Purchase ID:" + id);

        TVtitle = findViewById(R.id.title);
        TVstatus = findViewById(R.id.status);
        TVamount = findViewById(R.id.amount);
        TVuserLastName = findViewById(R.id.userLastName);
        TVuserName = findViewById(R.id.userName);
        TVuserDni = findViewById(R.id.userDni);
        TVuserID = findViewById(R.id.userId);
        IVimage = findViewById(R.id.imagen);
        TVFechaUser = findViewById(R.id.fechaUser);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://workshop-go-utnfrt.herokuapp.com/purchases/" + id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, response);
                        // Display the first 500 characters of the response string.
                        try {

                            JSONObject oneObject = new JSONObject(response);

                            Log.e(TAG, "oneObject: " + oneObject.toString());
                            // Pulling items from the array
                            String title = oneObject.getString("title");
                            String amount = oneObject.getString("amount");
                            String image = oneObject.getString("image");
                            String status = oneObject.getString("status");

                            ZonedDateTime instant = ZonedDateTime.parse(oneObject.getString("creation_date"));
                            DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
                            String fechaConv = instant.format(fecha);

                            TVFechaUser.setText("Fecha creacion: "+fechaConv);

                            TVtitle.setText("Titulo: " + title);
                            TVamount.setText("Amount: $" + amount);
                            TVstatus.setText("Status: " + status);


                            String user = oneObject.getString("user");

                            JSONObject objetoUsuario = new JSONObject(user);


                            Log.e(TAG,objetoUsuario.getString("name"));
                            Log.e(TAG,objetoUsuario.getString("last_name"));


                            TVuserID.setText("ID Usuario: "+objetoUsuario.getString("user_id"));
                            TVuserName.setText("Nombre: "+objetoUsuario.getString("name"));
                            TVuserLastName.setText("Apellido: "+objetoUsuario.getString("last_name"));
                            TVuserDni.setText("DNI Usuario: "+objetoUsuario.getString("dni"));





                            new MyAdapter.DownloadImageTask((ImageView) findViewById(R.id.imagen))
                                    .execute(image);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error is: " + error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}

