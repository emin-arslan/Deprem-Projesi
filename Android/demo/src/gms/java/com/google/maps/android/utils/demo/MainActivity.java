/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.maps.android.utils.demo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup mListView;
    String app_id="mongodbearthquake-tqtpl";
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    final ArrayList<String> data=new ArrayList<String>();
    final ArrayList<String> lat_data=new ArrayList<String>();
    final ArrayList<String> long_data=new ArrayList<String>();
    final ArrayList<String> name_data=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       /* if (BuildConfig.MAPS_API_KEY.isEmpty()) {
            Toast.makeText(this, "Add your own API key in local.properties as MAPS_API_KEY=YOUR_API_KEY", Toast.LENGTH_LONG).show();
        }*/


        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(app_id).build());
        Credentials credentials= Credentials.emailPassword("arslanemin1864@gmail.com","del123ete");
        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess())
                {
                    Log.v("User","Logged In Succesfully");


                }

                else
                {
                    Log.v("User","Failed to Login");
                }
            }
        });

        User user=app.currentUser();
        mongoClient=user.getMongoClient("mongodb-atlas");
        mongoDatabase=mongoClient.getDatabase("myFirstDatabase");
        MongoCollection<Document> mongoCollection=mongoDatabase.getCollection("Apartmanlar");
        Log.v("User","adim2");

        Log.v("User","adim2");/*
        Document queryFilter=new Document().append("Long","2345");
        mongoCollection.findOne(queryFilter).getAsync(result -> {

            if(result.isSuccess()) {
                Log.v("name", result.toString());
                Document resultdata=result.get();
                Log.v("name",resultdata.getString("Lat"));
                //deger=resultdata.getString("Lat");
                //Log.v("name",deger);


            }
        }); */

        Document queryfilter=new Document().append("Sehir","35");
        //final ArrayList<String> data=new ArrayList<String>();
        RealmResultTask<MongoCursor<Document>> findTask=mongoCollection.find(queryfilter).iterator();
        findTask.getAsync(task->{
                    if(task.isSuccess())
                    {
                        //Log.v("User","basarili");
                        MongoCursor<Document> results=task.get();
                        //Log.v("name",task.get().toString());
                        while(results.hasNext())
                        {
                            Document currentDoc=results.next();
                            //Log.v("name",currentDoc.getString("People_count"));
                            if(currentDoc.getString("People_count")!=null)
                            {
                                data.add(currentDoc.getString("People_count"));

                            }
                            if(currentDoc.getString("Lat")!=null)
                            {
                                lat_data.add(currentDoc.getString("Lat"));

                            }
                            if(currentDoc.getString("Long")!=null)
                            {
                                long_data.add(currentDoc.getString("Long"));

                            }
                            if(currentDoc.getString("name")!=null)
                            {
                                name_data.add(currentDoc.getString("name"));

                            }

                        }
                        if(!results.hasNext())
                        {
                            Log.v("Result","Couldnt find");
                        }
                        Log.v("name",data.toString());
                        mListView = findViewById(R.id.list);
                        addDemo("APARTMANLARI GETIR", IconGeneratorDemoActivity.class);

                    }
                    else
                    {
                        Log.v("Task error",task.getError().toString());
                    }
                }

        );







        /*
        Document document=new Document().append("name","123").append("Long","2345").append("Lat","12939");
        mongoCollection.insertOne(document).getAsync(result ->
        {
            Log.v("adding","baslama");
            if(result.isSuccess())
            {
                Log.v("adding","result");
                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Log.v("adding","result failed"+result.getError().toString());
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    private void addDemo(String demoName, Class<? extends Activity> activityClass) {
        Button b = new Button(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        b.setLayoutParams(layoutParams);
        b.setText(demoName);
        b.setTag(activityClass);
        b.setOnClickListener(this);

        mListView.addView(b);
    }

    @Override
    public void onClick(View view) {
        Class activityClass = (Class) view.getTag();
        Intent i = new Intent(this, activityClass);
        i.putExtra("data",data);
        Log.v("name99",lat_data.toString());
        i.putExtra("lat",lat_data);
        i.putExtra("long",long_data);
        i.putExtra("name",name_data);
        startActivity(i);
        //startActivity(new Intent(this, activityClass));
    }
}
