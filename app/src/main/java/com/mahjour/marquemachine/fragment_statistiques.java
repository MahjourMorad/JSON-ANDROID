package com.mahjour.marquemachine;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_statistiques extends Fragment {

    private Context mContext;
    private Activity mActivity;
    private TextView text;
    private String mAllMarqueUrl = "http://192.168.42.39:8090/marques/count";
    public static   int bgf;

    public  static int toufa7a;
    public  static int deeeee;


    public int getBgf() {
        return bgf;
    }

    public int getToufa7a() {
        return toufa7a;
    }

    public int getDeeeee() {
        return deeeee;
    }


    public fragment_statistiques() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_statistiques, container, false);
        mContext = getActivity().getApplicationContext();
        mActivity = getActivity();
        text = (TextView)view.findViewById(R.id.text);


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        // Initialize a new JsonArrayRequest instance
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                mAllMarqueUrl,
                null,
                new Response.Listener<JSONObject>() {
                    private Object MarqueAdapter;

                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            Iterator<String> keys = response.keys();
                            while(keys.hasNext()) {
                                String key = keys.next();
                                if (response.get(key) instanceof JSONObject) {
                                    // do something with jsonObject here
                                    text.append(""+"test"+(response.get(key)).toString());
                                    text.append(""+"test"+(response.get(key)).toString());
                                }
                            }



                                // Get current json object
                               // JSONObject matrice = response.getJSONObject(i);

                                // Get the current student (json object) data

//                                int bgf = response.getInt("bgf");
//                                int toufa7a = response.getInt("toufa7a");
//                                int deeeee = response.getInt("deeeee");


//
//                                text.append(bgf+""+deeeee);
//                                text.append("yes");













                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        error.printStackTrace();
                    }
                }


        );

        requestQueue.add(jsonArrayRequest);
        ArrayList<Integer> values  = new ArrayList<>();
        values.add(2);
        values.add(2);
        values.add(1);



        BarChart barChart = (BarChart) view.findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0 ;i<values.size();i++){
            entries.add(new BarEntry(values.get(i),i));
        }


        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("bgf");
        labels.add("toufa7a");
        labels.add("deeeee");


        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart



        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);

        return view;
    }






    }


