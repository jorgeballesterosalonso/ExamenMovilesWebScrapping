package com.pass.examenpmdmt2_jorgeballesteros;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;

public class HiloWebScrapping implements Runnable {

    private String nombre_ciudad;
    private Handler h;

    public HiloWebScrapping(android.os.Handler h, String nombre_ciudad) {
        this.h = h;
        this.nombre_ciudad = nombre_ciudad;
    }


    @Override
    public void run() {
        String lat = "";
        String longi = "";
        String latLong = "";
        String urlImagen = "";
        Document doc;
        try {
            String ruta = "https://es.wikipedia.org/wiki/";
            ruta += nombre_ciudad;
            doc = Jsoup.connect(ruta).get();
            Elements coordLat = doc.getElementsByClass("latitude");
            Elements coordLong = doc.getElementsByClass("longitude");
            Elements url = doc.getElementsByTag("img");
            urlImagen = url.get(1).absUrl("src").toString();
            lat = coordLat.get(0).text();
            longi = coordLong.get(0).text();
            latLong = lat + "," + longi;
            Log.d("WEBSCRAPPING_", latLong);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] Lat_Long = latLong.split(",");
        Log.d("RES", Arrays.toString(Lat_Long));

        lat = Lat_Long[0];
        Double segundosLat = Double.parseDouble(lat.substring(lat.length() - 4, lat.length() - 2));
        segundosLat = segundosLat / 60;
        Log.d("RES", String.valueOf(segundosLat));

        Double minutosLat = Double.parseDouble(lat.substring(lat.length() - 7, lat.length() - 5));
        minutosLat = minutosLat / 3600;
        Log.d("RES", String.valueOf(minutosLat));

        longi = Lat_Long[1];

        Double segundosLong = Double.parseDouble(longi.substring(longi.length() - 4, longi.length() - 2));
        segundosLong = segundosLong / 60;
        Log.d("RES", String.valueOf(segundosLong));

        Double minutosLong = Double.parseDouble(longi.substring(longi.length() - 7, longi.length() - 5));
        minutosLong = minutosLong / 3600;
        Log.d("RES", String.valueOf(minutosLong));


        Double latDouble;
        Double longiDouble;
        if (lat.length() == 10) {
            latDouble = Double.parseDouble(lat.substring(0, 2));
            latDouble = latDouble + segundosLat + minutosLat;
        } else {

            latDouble = Double.parseDouble(lat.substring(0, 1));
            latDouble = latDouble + segundosLat + minutosLat;
        }

        if (longi.length() == 10) {
            longiDouble = Double.parseDouble(longi.substring(0, 2));
            longiDouble = longiDouble + segundosLong + minutosLong;
        } else {

            longiDouble = Double.parseDouble(longi.substring(0, 1));
            longiDouble = longiDouble + segundosLong + minutosLong;
        }

        if (longi.charAt(longi.length() - 1) == 'O') {
            longiDouble = -longiDouble;
        }

        Ciudad ciudad = new Ciudad(nombre_ciudad, latDouble, longiDouble, urlImagen);

        Message mensaje = new Message();
        mensaje.obj = ciudad;
        Log.d("MENSAJE", latLong);
        h.sendMessage(mensaje);

    }
}
