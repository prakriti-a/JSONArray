package com.prakriti.jsonarray;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue myRQ;
    TextView joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joke = findViewById(R.id.joke);

        myRQ = Volley.newRequestQueue(this);

        JsonArrayRequest myArray = new JsonArrayRequest(Request.Method.GET, "https://official-joke-api.appspot.com/random_ten",
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for(int index=0; index<response.length(); index++) {

                        JSONObject eachJoke = response.getJSONObject(index);
                        Log.i("RES","JOKE ID: "+ eachJoke.getInt("id") + ", TYPE: "+ eachJoke.getString("type"));

                        String oldJoke=joke.getText().toString();
                        joke.setText(oldJoke + eachJoke.getString("setup") + " - " + eachJoke.getString("punchline") + "\n\n");
                    }
                }
                catch(JSONException e) {
                    Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERR", error.getMessage());
            }
        });

        myRQ.add(myArray);

    }
}