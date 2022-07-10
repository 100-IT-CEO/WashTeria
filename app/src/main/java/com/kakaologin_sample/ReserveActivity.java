package com.kakaologin_sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReserveActivity extends AppCompatActivity{

    private static final String HOST = "143.248.199.17";
    private static final String PORT = "80";

    private int washteria_id;
    private String washteria_name;
    private int washer_big_num;
    private int washer_num;
    private int dryer_big_num;
    private int dryer_num;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        washteria_id = intent.getIntExtra("washteria_id", 0);
        washteria_name = intent.getStringExtra("washteria_name");

        setContentView(R.layout.reserve_activity);

        CardView button1 = findViewById(R.id.reserve_button_1);
        CardView button2 = findViewById(R.id.reserve_button_2);
        CardView button3 = findViewById(R.id.reserve_button_3);
        CardView button4 = findViewById(R.id.reserve_button_4);
        CardView button5 = findViewById(R.id.reserve_button_5);

        String url = "http://"+HOST+":"+PORT+"/washteria_machines?id="+washteria_id;

        RequestQueue requestQueue = Volley.newRequestQueue(ReserveActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener(){
            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray machines = jsonObject.getJSONArray("result");

                    Log.d("asdf", machines.toString());

                    LinearLayout layout_expand1 = findViewById(R.id.layout_expand_1);
                    LinearLayout layout_expand2 = findViewById(R.id.layout_expand_2);
                    LinearLayout layout_expand3 = findViewById(R.id.layout_expand_3);
                    LinearLayout layout_expand4 = findViewById(R.id.layout_expand_4);
                    LinearLayout layout_expand5 = findViewById(R.id.layout_expand_5);

                    TextView big_washer_num = findViewById(R.id.big_washer_num);
                    TextView washer_num = findViewById(R.id.washer_num);
                    TextView big_dryer_num = findViewById(R.id.big_dryer_num);
                    TextView dryer_num = findViewById(R.id.dryer_num);
                    TextView etc_num = findViewById(R.id.etc_num);

                    int[] count= {0, 0, 0, 0, 0};

                    for(int i=0; i<machines.length(); i++){
                        JSONObject machine = machines.getJSONObject(i);
                        int status = machine.getInt("status");
                        String machine_type = machine.getString("machine_type");

                        if(status == 0){
                            switch(machine_type){
                                case "big_washer" : count[0] += 1; break;
                                case "washer" : count[1] += 1; break;
                                case "big_dryer" : count[2] += 1; break;
                                case "dryer" : count[3] += 1; break;
                                default : count[4] += 1; break;
                            }
                        }

                        switch(machine_type){
                            case "big_washer" : create(layout_expand1, machine, status); break;
                            case "washer" : create(layout_expand2, machine, status); break;
                            case "big_dryer" : create(layout_expand3, machine, status); break;
                            case "dryer" : create(layout_expand4, machine, status); break;
                            default : create(layout_expand5, machine, status);
                        }
                    }

                    big_washer_num.setText(String.valueOf(count[0]));
                    washer_num.setText(String.valueOf(count[1]));
                    big_dryer_num.setText(String.valueOf(count[2]));
                    dryer_num.setText(String.valueOf(count[3]));
                    etc_num.setText(String.valueOf(count[4]));

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("asdf","에러: " + error.toString());
            }
        });

        requestQueue.add(stringRequest);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout_expand = findViewById(R.id.layout_expand_1);

                if(layout_expand.getVisibility() == View.VISIBLE){
                    hideView(layout_expand);
                }
                else if(layout_expand.getVisibility() == View.GONE){
                    showView(layout_expand);
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout_expand = findViewById(R.id.layout_expand_2);

                if(layout_expand.getVisibility() == View.VISIBLE){
                    hideView(layout_expand);
                }
                else if(layout_expand.getVisibility() == View.GONE){
                    showView(layout_expand);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout_expand = findViewById(R.id.layout_expand_3);

                if(layout_expand.getVisibility() == View.VISIBLE){
                    hideView(layout_expand);
                }
                else if(layout_expand.getVisibility() == View.GONE){
                    showView(layout_expand);
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout_expand = findViewById(R.id.layout_expand_4);

                if(layout_expand.getVisibility() == View.VISIBLE){
                    hideView(layout_expand);
                }
                else if(layout_expand.getVisibility() == View.GONE){
                    showView(layout_expand);
                }
            }
        });
    }



    private void showView(final View view){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_up);
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }
        });

        view.startAnimation(animation);
    }

    private void hideView(final View view){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_down);
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }
        });

        view.startAnimation(animation);
    }

    public void create(LinearLayout linearLayout, JSONObject machine, int status){
        Button button = new Button(ReserveActivity.this);
        try{
            switch(machine.getString("machine_type")){

                case "big_washer" : button.setText("대형 세탁기 #" + String.valueOf(machine.getInt("machine_id"))); break;
                case "washer" : button.setText("중형 세탁기 #" + String.valueOf(machine.getInt("machine_id"))); break;
                case "big_dryer" : button.setText("대형 건조기 #" + String.valueOf(machine.getInt("machine_id"))); break;
                case "dryer" : button.setText("중형 건조기 #" + String.valueOf(machine.getInt("machine_id"))); break;
                default : button.setText(machine.getString("machine_type")+ " #" + String.valueOf(machine.getInt("machine_id"))); break;
            }
            button.setTag(machine.getInt("machine_id"));

            if(status!=0){
                button.setBackgroundColor(Color.RED);
            }else{
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "http://"+HOST+":"+PORT+"/washteria_machines?id="+washteria_id;
                        RequestQueue requestQueue = Volley.newRequestQueue(ReserveActivity.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener(){
                            @Override
                            public void onResponse(Object response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    JSONArray machines = jsonObject.getJSONArray("result");


                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("asdf","에러: " + error.toString());
                            }
                        });

                        requestQueue.add(stringRequest);
                    }
                });
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        linearLayout.addView(button);
    }

}
