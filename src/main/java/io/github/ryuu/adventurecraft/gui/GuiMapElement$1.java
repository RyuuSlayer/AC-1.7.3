package io.github.ryuu.adventurecraft.gui;

import java.net.URL;
import java.net.URLConnection;

class null implements Runnable{
public void run(){
        try{
        URL url=new URL(String.format("http://www.adventurecraft.org/cgi-bin/vote.py?mapID=%d&rating=%d",new Object[]{Integer.valueOf(this.this$0.mapID),Integer.valueOf(this.this$0.voted)}));
        URLConnection urlconnection=url.openConnection();
        urlconnection.connect();
        urlconnection.getInputStream();
        }catch(Exception e){
        e.printStackTrace();
        }
        }
        }