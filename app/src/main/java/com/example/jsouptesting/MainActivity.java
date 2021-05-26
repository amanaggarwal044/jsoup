package com.example.jsouptesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {
TextView t;
    private RequestQueue mQueue;
    private String url;
    private String pagenumber="1";
    private String uptopagenumber="10";

    private String myid="";
    private String myname="";
    private RecyclerView recyclerView;
    String test="";
    private int objcount1=0;

    String postid = "";
    String postdate = "";
    String postlastmodified ="";
    String postlink = "";
    String excerpt="";
    String aaa="";


    private String posttitle="";
    private String medialink="";
    private String postcontent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    t=(TextView)findViewById(R.id.test);


    unloaddata();

    }
    private void unloaddata()
    {


        mQueue = Volley.newRequestQueue(getApplicationContext());

            url = "https://khabrainabhitak.com/wp-json/wp/v2/posts?page=" +"1" + "&per_page="+"10"+"&categories=" + "1" +"&_embed";


            JsonArrayRequest myyrequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                objcount1 = response.length();

                                //here we download the full data and applying further before nested json is not working

                                String data=response.toString();

                                JSONArray array = new JSONArray(data);
                                for (int i = 0; i < 1; i++) {
                                    JSONObject obj = (JSONObject) array.get(i);

                                    postid = obj.getString("id");
                                    postdate = obj.getString("date");
                                    postlastmodified = obj.getString("modified");
                                    postlink = obj.getString("link");


                                    JSONObject ob=(JSONObject) obj.get("title");


                                    posttitle=ob.getString("rendered");// for this we downlaod all data in data

                                    JSONObject oc=(JSONObject) obj.get("content");
                                    String postcontent4=oc.getString("rendered");

                                    JSONObject od=(JSONObject)obj.get("excerpt");
                                    String abc=od.getString("rendered");


                                   JSONObject oe=(JSONObject)obj.get("_embedded");

                                   JSONArray of=(JSONArray)oe.get("wp:featuredmedia");
                                   for(int k=0;k<of.length();k++)
                                   {
                                       JSONObject kkk=(JSONObject)of.get(k);
                                       {
                                           aaa=kkk.getString("source_url");
                                       }


                                   }






                                    Document d=Jsoup.parse(abc);
                                    Elements e=d.getElementsByTag("p");

                                    for(Element p:e){
                                        String vas=p.text();
                                        excerpt=excerpt+vas;

                                    }





                                    String soup=postcontent4;
                                  Document document= Jsoup.parse(soup);
                                    Elements elements=document.getElementsByTag("p");
                                    for (Element paragraph : elements) {
                                        String a=paragraph.text();
                                        postcontent=postcontent+a;
                                    }









                                    /*medialink = elements.attr("href");

                                     */





                                }
                                test=aaa+"\n\n"+excerpt+"\n\n"+postlink+"\n\n"+postid+"\n\n"+posttitle+"\n\n"+postdate+"\n\n"+postlastmodified+"\n\n"+postcontent;


                                t.setText(test);
                            }


                            catch (JSONException e)
                            {e.printStackTrace();}



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueue.add(myyrequest);

    }

}
