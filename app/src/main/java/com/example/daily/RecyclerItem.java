package com.example.daily;



public class RecyclerItem {




    String title ;
    String inputtext;




    public RecyclerItem(String title, String inputtext){


        this.title=title;
        this.inputtext=inputtext;
    }



    public void setTitle(String title) {
        this.title = title ;
    }

    public String getTitle() {
        return this.title ;
    }

    public String getInputtext() {
        return inputtext;
    }

    public void setInputtext(String inputtext) {
        this.inputtext = inputtext;
    }


}
