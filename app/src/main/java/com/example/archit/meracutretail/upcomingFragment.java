package com.example.archit.meracutretail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import meracutretail.R;

public class upcomingFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ArrayList<NormalCardData> arr_upcoming = new ArrayList<>() ;
    ServerRequest serverhistory;
    JSONObject jsonob;
    Loadhistory lh;
    public ProgressDialog mProgressDialog;
    View rootView;
    //SharedPreferences sharedPref;

    public upcomingFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static upcomingFragment newInstance() {
        upcomingFragment fragment;
        fragment = new upcomingFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        rootView = inflater.inflate(R.layout.fragment_upcoming, container, false);
        //sharedPref = getActivity().getSharedPreferences("meracutretail", MODE_PRIVATE);

        Log.d("oncreate","upcoming");

        String[] para = {MainActivity.sharedPrefs.getString("username","abc"),MainActivity.sharedPrefs.getString("passxyz","abc")};
        serverhistory = new ServerRequest(para, "http://www.meracut.com/app/clientBookings.php");
        serverhistory.execute();

        if (jsonobject.getjson() == null) {
            lh = new Loadhistory();
            lh.start();
        }else{
            jsonob = jsonobject.getjson();
        }

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
                        Log.d("history","History Loaded");
                        Log.d("jsonob",jsonob.toString());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                createcards();
                            }
                        });

                        stop = false;

                    }else if(serverhistory!=null) {
                        if (serverhistory.getstopflag().equals("failed")) {
                            stop = false;
                            Toast.makeText(getActivity(),"Oops, Something went wrong!!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    Thread.sleep(500);

                    Log.d("history","Loading history:: "+serverhistory.getstopflag());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void createcards(){

        rootView.findViewById(R.id.progress_upcoming).setVisibility(View.GONE);


        try {
            if(!jsonob.isNull("name")) {
                rootView.findViewById(R.id.rv_upcoming).setVisibility(View.VISIBLE);

                RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_upcoming);
                RVAnormalcard adapter = new RVAnormalcard(arr_upcoming);
                rv.setAdapter(adapter);
                rv.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(llm);


                JSONArray jsonArr = jsonob.getJSONArray("services");    //services
                Log.d("history object ", jsonArr.toString());
                String[] ser = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    ser[i] = jsonArr.getString(i).substring(2);
                    Log.d("serup", ser[i]);
                }

                jsonArr = jsonob.getJSONArray("number");                  // customer number
                Log.d("history object ", jsonArr.toString());
                String[] number = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    number[i] = jsonArr.getString(i);
                    Log.d("history", number[i]);
                }

                jsonArr = jsonob.getJSONArray("name");                  // customer name
                Log.d("history object ", jsonArr.toString());
                String[] name = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    name[i] = jsonArr.getString(i);
                    Log.d("history", name[i]);
                }

                jsonArr = jsonob.getJSONArray("bid");                  // booking ID
                Log.d("history object ", jsonArr.toString());
                String[] bid = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    bid[i] = jsonArr.getString(i);
                    Log.d("history", bid[i]);
                }

                jsonArr = jsonob.getJSONArray("bdate");                 // booking date
                Log.d("history object ", jsonArr.toString());
                String[] bdate = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    bdate[i] = jsonArr.getString(i);
                    Log.d("history", bdate[i]);
                }

                jsonArr = jsonob.getJSONArray("btime");                 // booking time
                Log.d("history object ", jsonArr.toString());
                String[] btime = new String[jsonArr.length()];
                for (int i = 0; i < jsonArr.length(); i++) {
                    btime[i] = jsonArr.getString(i);
                    Log.d("history", btime[i]);
                }
                jsonArr = jsonob.getJSONArray("tprice");            // booking price
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
                    list.setcardname("upcoming");
                    list.setbookid(bid[i]);
                    list.settotalprice(bprice[i]);
                    list.setnumber(number[i]);

                    arr_upcoming.add(list);
                }
            }else{

                rootView.findViewById(R.id.noups).setVisibility(View.VISIBLE);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

    }
    /*public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Getting Your Location, Please Wait");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }
*/
}
