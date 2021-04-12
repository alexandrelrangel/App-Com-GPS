package com.example.appcomgps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            try {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        TextView txtLatitude = findViewById(R.id.txtLatitude);
                        TextView txtLongitude = findViewById(R.id.txtLongitude);
                        TextView txtAltitude = findViewById(R.id.txtAltitude);
                        TextView txtVelocidade = findViewById(R.id.txtVelocidade);
                        TextView txtHorario = findViewById(R.id.txtHorario);

                        txtLatitude.setText("Latitude: " + location.getLatitude());
                        txtLongitude.setText("Longitude: " + location.getLongitude());
                        txtAltitude.setText("Altitude: " + location.getAltitude());
                        txtVelocidade.setText("Velocidade m/s: " + location.getSpeed());

                        // Pegando a data e horas locais. Precisa converter de UTC para data
                        String sData = String.valueOf(location.getTime())+ " UTC";
                        Date date = new Date();
                        txtHorario.setText("Horário: " + date.toString());
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };
                // Determina a atualização do GPS:
                // minTimeMs: intervalo de tempo mínimo entre atualizações de localização em milissegundos
                // minDistanceM: distância mínima entre atualizações de localização em metros
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            } catch (SecurityException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}