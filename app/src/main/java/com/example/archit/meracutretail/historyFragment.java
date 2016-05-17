package com.example.archit.meracutretail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class historyFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ArrayList<NormalCardData> arr_nearby = new ArrayList<>() ;

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
        View rootView;
        //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        rootView = inflater.inflate(R.layout.fragment_history, container, false);


        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_history);
        RVAnormalcard adapter = new RVAnormalcard(arr_nearby);
        //rv.setAdapter(adapter);

        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        Log.d("TAG", "inside nearby");

        String[] name = {"dhruv", "archit", "vijayant", "vipul", "yash", "parth"};
        String[] desc = {"btech", "passed out", "btech", "eng honors", "school", "ece"};


        for (int i = 0; i < 6; i++) {
            NormalCardData list = new NormalCardData();

            list.setname(name[i]);
            list.setdesc(desc[i]);
            list.setclassname("nearby");

            arr_nearby.add(list);
            rv.setAdapter(adapter);
            Log.d("tag", "adding" + list.name);


        }

        return rootView;
    }
}
