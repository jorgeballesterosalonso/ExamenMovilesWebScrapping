package com.pass.examenpmdmt2_jorgeballesteros;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccesoADatos {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference myRef = database.getReference("Ciudad");

    public static void grabarCiudadEnFirebase(Ciudad c) {
        myRef.push().setValue(c);
    }

    public static HashMap<String,Ciudad> obtenerciudadFirebase() {
        HashMap<String,Ciudad> mapaCiudades = new HashMap<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> espanshot = dataSnapshot.getChildren();
                for (DataSnapshot hijo : espanshot) {
                    mapaCiudades.put(hijo.getValue(Ciudad.class).getNombre(), (Ciudad) hijo.getValue(Ciudad.class));
                    LatLng marcador = new LatLng(hijo.getValue(Ciudad.class).getLatitud(),hijo.getValue(Ciudad.class).getLongitud());
                    MapsActivity.mMap.addMarker(new MarkerOptions().position(marcador).title(hijo.getValue(Ciudad.class).getUrlImagen()));
                }
                Log.d("MENSAJE", "Value is: " + mapaCiudades);
                MapsActivity.ciudadFirebase = mapaCiudades;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });

        return mapaCiudades;
    }
}
