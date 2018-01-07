package com.example.archit.meracutretail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import meracutretail.R;

public class historyFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ArrayList<NormalCardData> arr_history = new ArrayList<>() ;
    JSONObject jsonob;
    ServerRequest serverhistory;
    Loadhistory lh;
    View rootView;

    public historyFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static historyFragment newInstance() {
        historyFragment fragment;
        fragment = new historyFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        rootView = inflater.inflate(R.layout.fragment_history, container, false);

        if (jsonobject.getjson() == null) {
            lh = new Loadhistory();
            lh.start();
        }else{
            jsonob = jsonobject.getjson();
        }


        Log.d("history","history fragment");


        return rootView;
    }

    public class Loadhistory extends Thread {
        boolean stop = true;

        public void stopthread(boolean flag){
            this.stop = flag;
        }

        @Override
        public void run() {
            while (stop) {
                jsonob = jsonobject.getjson();
                try {
                    if (jsonob != null) {


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                createcards();
                            }
                        });


                        stop = false;

                    }

                    Thread.sleep(500);
                    Log.d("history","Loading history");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void createcards(){


        rootView.findViewById(R.id.progress_history).setVisibility(View.GONE);

        try {
            if(!jsonob.isNull("hist_name")) {
                rootView.findViewById(R.id.rv_history).setVisibility(View.VISIBLE);

                RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_history);
                RVAnormalcard adapter = new RVAnormalcard(arr_history);
                rv.setAdapter(adapter);
                rv.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(llm);


                JSONArray jsonArr = jsonob.getJSONArray("hist_services");    //services
                Log.d("history object ", jsonArr.toString());
                String[] ser = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    ser[i] = jsonArr.getString(i).substring(2);
                    Log.d("history_serv", ser[i]);
                }

                jsonArr = jsonob.getJSONArray("hist_number");                  // customer number
                Log.d("history object ", jsonArr.toString());
                String[] number = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    number[i] = jsonArr.getString(i);
                    Log.d("history", number[i]);
                }

                jsonArr = jsonob.getJSONArray("hist_bid");                  // booking ID
                Log.d("history object ", jsonArr.toString());
                String[] bidhist = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    bidhist[i] = jsonArr.getString(i);
                    Log.d("history", bidhist[i]);
                }

                jsonArr = jsonob.getJSONArray("hist_name");                  // customer name
                Log.d("history object ", jsonArr.toString());
                String[] name = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    name[i] = jsonArr.getString(i);
                    Log.d("history", name[i]);
                }

                jsonArr = jsonob.getJSONArray("hist_bdate");                 // booking date
                Log.d("history object ", jsonArr.toString());
                String[] bdate = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    bdate[i] = jsonArr.getString(i);
                    Log.d("history", bdate[i]);
                }

                jsonArr = jsonob.getJSONArray("hist_btime");                 // booking time
                Log.d("history object ", jsonArr.toString());
                String[] btime = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    btime[i] = jsonArr.getString(i);
                    Log.d("history", btime[i]);
                }
                jsonArr = jsonob.getJSONArray("hist_tprice");            // booking price
                Log.d("history object ", jsonArr.toString());
                String[] bprice = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    bprice[i] = jsonArr.getString(i);
                    Log.d("history", bprice[i]);
                }


                for (int i = 0; i < ser.length; i++) {
                    NormalCardData list = new NormalCardData();

                    list.setCustomername(name[i]);
                    list.setServices(ser[i]);
                    list.setbookdate(bdate[i]);
                    list.setbooktime(btime[i]);
                    list.settotalprice(bprice[i]);
                    list.setnumber(number[i]);
                    list.setcardname("history");
                    list.setbookid(bidhist[i]);

                    arr_history.add(list);
                }
            }else{
                rootView.findViewById(R.id.nohist).setVisibility(View.VISIBLE);
            }


        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
