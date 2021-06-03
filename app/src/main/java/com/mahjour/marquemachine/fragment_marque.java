package com.mahjour.marquemachine;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mahjour.marquemachine.beans.marque;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_marque extends Fragment {

    private Context mContext;
    private Activity mActivity;

    private CoordinatorLayout mCLayout;
    private Button mButtonDo;
    private  Button chartButton;

    private Spinner spinner;
    ArrayList<marque> marqueArrayList;

    private String mAllMarqueUrl = "http://192.168.42.39:8090/marques/all";
    private String mAllMachineUrl = "http://192.168.42.39:8090/machines/all";
    private String mJSONURL = "http://192.168.42.39:8090/machines/findbymarque/";
    private ArrayList<String> Marqueslist;
    private ArrayList<String> Produitlist;
    private TextView text;
    public fragment_marque() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        Marqueslist=new ArrayList<>();
        Produitlist=new ArrayList<>();


        // Get the application context
        mContext = getActivity().getApplicationContext();
        mActivity = getActivity();
        final int first_spinner = 0, first_spinner_counter = 0;



        View view = inflater.inflate(R.layout.fragment_fragment_marque, container, false);
        // Get the widget reference from XML layout

//        mButtonDo = (Button) view.findViewById(R.id.button_parse);
//
//        chartButton = (Button) view.findViewById(R.id.buttonChart);

        spinner=(Spinner)view.findViewById(R.id.machine);

        text = (TextView)view.findViewById(R.id.text);

        loadAllMachines();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mAllMarqueUrl,
                null,
                new Response.Listener<JSONArray>() {
                    private Object MarqueAdapter;

                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject matrice = response.getJSONObject(i);

                                // Get the current student (json object) data

                                String code = matrice.getString("code");
                                String libelle = matrice.getString("libelle");
                                String ide =matrice.getString("id");
                                int id= Integer.valueOf(ide);
                                text.append(code);
                                text.append(libelle);
                                text.append(ide);
                                text.append("hello");



                                // Display the formatted json data in text view

                                marque mrq = new marque(id,code,libelle);

                                Marqueslist.add(mrq.getCode());
                                Produitlist.add(ide);
                               //
                               //text.setText("Toutes les machines "+"\n"+"Veuiller filtrer par marque dans le spinner SVP"+"\n");



                            }
                            spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,Marqueslist));

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
        loadAllMachines();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(i==0){
                    loadAllMachines();
                }

                else
                {
                    String country=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                    Toast.makeText(getActivity().getApplicationContext(),country,Toast.LENGTH_LONG).show();
                    String id = Produitlist.get(i);
//                String ide = "5";

                    loadMachines(id);
                }

               ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);



        return view;
    }
    public void loadMachines(String id){

        text.setText("");

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);


        String JSOOON = mJSONURL + id;
//         text.append(JSOOON + "\n");
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                JSOOON,
                null,
                new Response.Listener<JSONArray>() {
                    private Object MarqueAdapter;

                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object

                                JSONObject matrice = response.getJSONObject(i);

                                // Get the current student (json object) data

                                String reference = matrice.getString("reference");


                                text.append(reference+"\n");


                                // Display the formatted json data in text view






                            }



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
    }

    public void loadAllMachines(){

        text.setText("");

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);


        String JSOOON = mAllMachineUrl ;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                JSOOON,
                null,
                new Response.Listener<JSONArray>() {
                    private Object MarqueAdapter;

                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object

                                JSONObject matrice = response.getJSONObject(i);

                                // Get the current student (json object) data

                                String reference = matrice.getString("reference");


                                text.append(reference+"\n");









                            }



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
    }

}
