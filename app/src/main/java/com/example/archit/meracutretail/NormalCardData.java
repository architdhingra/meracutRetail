package com.example.archit.meracutretail;

/**
 * Created by Dhruv on 23-03-2016.
 */
public class NormalCardData {


        String customername,number,bdate,btime,tprice,cardname,bid;

        String services;


        NormalCardData(){

        }

        public void setCustomername(String name){
            this.customername = name;
        }
        public void setServices(String servs){
            this.services = servs;
        }
        public void setbookdate(String date){
            this.bdate = date;
        }
        public void setbooktime(String time){
        this.btime = time;
    }
        public void settotalprice(String price){
        this.tprice = price;
    }
        public void setnumber(String no){
        this.number = no;
    }
        public void setcardname(String card){
        this.cardname = card;
    }
        public void setbookid(String bid){
        this.bid = bid;
    }



}
