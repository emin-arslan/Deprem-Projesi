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

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;

import org.bson.Document;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class IconGeneratorDemoActivity extends BaseDemoActivity {
    public ArrayList<String> people_count=new ArrayList<>();
    public ArrayList<String> lat_numbers=new ArrayList<>();
    public ArrayList<String> long_numbers=new ArrayList<>();
    public ArrayList<String> names=new ArrayList<>();
    @Override
    protected void startDemo(boolean isRestore) {
        if (!isRestore) {
            getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.4, 27.12), 10));
        }
        Log.v("name3", people_count.toString());
        IconGenerator iconFactory = new IconGenerator(this);
        addIcon(iconFactory, "KS1:", new LatLng(-33.8696, 151.2094));
        for (int i = 0; i < people_count.size(); i++) {

            iconFactory.setRotation(90);
            iconFactory.setColor(Color.WHITE);
            addIcon(iconFactory, names.get(i)+":"+ String.valueOf(people_count.get(i)), new LatLng(Double.valueOf( lat_numbers.get(i)),Double.valueOf(long_numbers.get(i)) ));
            Log.v("namedouble",lat_numbers.get(i).toString());
        }
        Log.v("name7", "test");





    /*
        iconFactory.setRotation(90);
        iconFactory.setStyle(IconGenerator.STYLE_RED);
        addIcon(iconFactory, "Rotated 90 degrees", new LatLng(-33.8858, 151.096));

        iconFactory.setContentRotation(-90);
        iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
        addIcon(iconFactory, "Rotate=90, ContentRotate=-90", new LatLng(-33.9992, 151.098));

        iconFactory.setRotation(0);
        iconFactory.setContentRotation(90);
        iconFactory.setStyle(IconGenerator.STYLE_GREEN);
        addIcon(iconFactory, "ContentRotate=190", new LatLng(-33.7677, 151.244));

        iconFactory.setRotation(0);
        iconFactory.setContentRotation(0);
        iconFactory.setStyle(IconGenerator.STYLE_ORANGE);
        addIcon(iconFactory, makeCharSequence(), new LatLng(-33.77720, 151.12412));*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            Log.v("name2","test");
            Intent intent=getIntent();
            ArrayList<String> kisi_sayilari=intent.getStringArrayListExtra("data");
        ArrayList<String> longsayilari=intent.getStringArrayListExtra("long");
        ArrayList<String> latsayilari=intent.getStringArrayListExtra("lat");
        ArrayList<String> isimler=intent.getStringArrayListExtra("name");

        if (kisi_sayilari!= null && longsayilari!=null && latsayilari!=null && isimler!=null) {
            Log.v("name2",kisi_sayilari.toString());
            people_count=kisi_sayilari;
            lat_numbers=latsayilari;
            long_numbers=longsayilari;
            names=isimler;
            //The key argument here must match that used in the other activity
        }
        Log.v("name9","oncreate");
    }

    private void addIcon(IconGenerator iconFactory, CharSequence text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        getMap().addMarker(markerOptions);
    }

    private CharSequence makeCharSequence() {
        String prefix = "Mixing ";
        String suffix = "different fonts";
        String sequence = prefix + suffix;
        SpannableStringBuilder ssb = new SpannableStringBuilder(sequence);
        ssb.setSpan(new StyleSpan(ITALIC), 0, prefix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new StyleSpan(BOLD), prefix.length(), sequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}
