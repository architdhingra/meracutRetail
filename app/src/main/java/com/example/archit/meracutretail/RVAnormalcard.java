package com.example.archit.meracutretail;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RVAnormalcard extends RecyclerView.Adapter<RVAnormalcard.docViewHolder> {

    private ArrayList<NormalCardData> arrList = new ArrayList<>();

    RVAnormalcard(ArrayList<NormalCardData> arrList){

        this.arrList = arrList;
        notifyItemChanged(0,arrList.size());
    }

    public static class docViewHolder extends RecyclerView.ViewHolder{
        public View itemview;
        CardView cv;
        TextView personName;
        TextView persondesc;




        public docViewHolder(final View itemView) {
            super(itemView);
            this.itemview = itemView;
            cv = (CardView)itemView.findViewById(R.id.cv_nearby);
            personName = (TextView)itemView.findViewById(R.id.name);
            persondesc = (TextView)itemView.findViewById(R.id.desc);





            }

    }


    @Override
    public docViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v;

         v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_nearby, viewGroup, false);
        docViewHolder pvh = new docViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(docViewHolder DocViewHolder, final int i) {
        //DocViewHolder.personName.setText(arrList.get(i).name);
        //DocViewHolder.persondesc.setText(arrList.get(i).desc);



        DocViewHolder.itemview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //v.getContext().startActivity(new Intent(v.getContext() , Salon.class));


            }

        });



    }

    @Override
    public int getItemCount() {

        return arrList.size();
    }


}





