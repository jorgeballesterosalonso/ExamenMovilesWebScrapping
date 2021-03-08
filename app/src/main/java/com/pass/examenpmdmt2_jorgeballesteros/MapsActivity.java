package com.pass.examenpmdmt2_jorgeballesteros;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    private Button btnBuscar;
    private TextInputEditText entrada;
    Handler handler = null;
    Context context = this;
    public static HashMap<String, Ciudad> ciudadFirebase;
    public static ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String[] nombres = {"Madrid", "Sevilla", "Pontevedra"};

        //Iniciamos el Handler

        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Ciudad c = (Ciudad) msg.obj;
                AccesoADatos.grabarCiudadEnFirebase(c);

                ciudadFirebase = AccesoADatos.obtenerciudadFirebase();
                Log.d("MENSAJE1", ciudadFirebase.toString());
                for (Ciudad city : ciudadFirebase.values()) {

                    LatLng marcador = new LatLng(city.getLatitud(), city.getLongitud());
                    mMap.addMarker(new MarkerOptions().position(marcador).title(city.getNombre()));

                }


            }
        };

        //Arrancamos los hilos
        for (String nombre : nombres) {
            Thread hilo = new Thread(new HiloWebScrapping(handler, nombre));
            hilo.start();
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

              //  imagen = findViewById(R.id.imagen);

                AlertDialog.Builder alertadd = new AlertDialog.Builder(context);
                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.alert_dialog, null);
                imagen=view.findViewById(R.id.imagen);
                alertadd.setView(view);
                alertadd.setNeutralButton("Here!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                    }
                });

                Glide.with(context).load(marker.getTitle()).into(imagen);

                alertadd.show();
                return false;
            }
        });
        // Add a marker in Sydney and move the camera

    }
}