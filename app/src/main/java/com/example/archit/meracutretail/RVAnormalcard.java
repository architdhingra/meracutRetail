package com.example.archit.meracutretail;


import android.app.ProgressDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import meracutretail.R;


public class RVAnormalcard extends RecyclerView.Adapter<RVAnormalcard.docViewHolder> {

    private ArrayList<NormalCardData> arrList = new ArrayList<>();
    JSONObject jsonob;
    ServerRequest bookstatus;
    private ProgressDialog mProgressDialog;
     static int j=0;

    RVAnormalcard(ArrayList<NormalCardData> arrList){

        this.arrList = arrList;
        notifyItemChanged(0,arrList.size());
    }

    public static class docViewHolder extends RecyclerView.ViewHolder{
        public View itemview;
        CardView cv;
        TextView customerName;
        TextView number,bdate,btime,tprice,services,bookid,status;
        ImageButton yes,no;


        public docViewHolder(final View itemView) {
            super(itemView);
            this.itemview = itemView;
            cv = (CardView)itemView.findViewById(R.id.cv_nearby);
            customerName = (TextView)itemView.findViewById(R.id.name);
            bdate = (TextView)itemView.findViewById(R.id.serv_bdate_text);
            btime = (TextView)itemView.findViewById(R.id.serv_btime_text);
            tprice = (TextView)itemView.findViewById(R.id.serv_price_text);
            number = (TextView)itemView.findViewById(R.id.serv_number_text);
            services = (TextView)itemView.findViewById(R.id.serv_availed_text);
            bookid = (TextView)itemView.findViewById(R.id.serv_bookid_text);
            yes = (ImageButton) itemView.findViewById(R.id.yes);
            no = (ImageButton) itemView.findViewById(R.id.no);

            }

    }


    @Override
    public docViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = null;
        if (arrList.get(i).cardname.equals("history")) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_history, viewGroup, false);
        }
        if (arrList.get(i).cardname.equals("upcoming")) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_upcoming, viewGroup, false);
        }
        docViewHolder pvh = new docViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(docViewHolder DocViewHolder, final int i) {
        DocViewHolder.customerName.setText(arrList.get(i).customername);
        DocViewHolder.services.setText(arrList.get(i).services);
        Log.d("rvaservices","::"+arrList.get(i).services);
        DocViewHolder.number.setText(arrList.get(i).number);
        DocViewHolder.bdate.setText(arrList.get(i).bdate);
        DocViewHolder.btime.setText(arrList.get(i).btime);
        DocViewHolder.tprice.setText(arrList.get(i).tprice);
        DocViewHolder.bookid.setText(arrList.get(i).bid);



        if(arrList.get(i).cardname.equals("upcoming")){

            DocViewHolder.yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*String[] para= {"y",arrList.get(i).bid};
                    bookstatus = new ServerRequest(para,"http://meracut/url");
                    bookstatus.execute();*/

                    setstatus st = new setstatus(MainActivity.sharedPrefs.getString("username","abc"),i,"confirmed",arrList.get(i).bid);
                    st.start();

                }
            });

            DocViewHolder.no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setstatus st = new setstatus(MainActivity.sharedPrefs.getString("username","abc"),i,"canceled",arrList.get(i).bid);
                    st.start();

                    /*String[] para= {"n",arrList.get(i).bid};
                    bookstatus = new ServerRequest(para,"http://meracut/url");
                    bookstatus.execute();*/

                }
            });

        }



        DocViewHolder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {

        return arrList.size();
    }


    /*-------------------------------LOADING CLASS-------------------------------------------------*/

    public class setstatus extends Thread {
        boolean stop = true;
        ServerRequest sq_thread;
        int pos;
        String status,bid,user;


        setstatus(String user, int i, String stat, String b){

            this.pos = i;
            this.status = stat;
            this.bid = b;
            this.user = user;

        }

        public void stopthread(boolean flag){
            this.stop = flag;
        }

        @Override
        public void run() {
            String[] para = {user,bid,status};
            sq_thread = new ServerRequest(para,"http://www.meracut.com/app/updateStatus.php");
            sq_thread.execute();

            while (stop) {
                jsonob = sq_thread.js;
                try {
                    if (jsonob != null) {

                        Log.d("status", "status has been set");
                        Log.d("jsonob", jsonob.toString());

                        arrList.remove(pos);
                        notifyItemRemoved(pos);

                        /*getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });*/

                        stop = false;

                    }else if(sq_thread.stopflag.equals("failed")){
                        stop = false;
                        Log.d("Failed","stopping the thread");
                    }

                    Thread.sleep(500);
                    Log.d("status","still trying to set status");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


}





