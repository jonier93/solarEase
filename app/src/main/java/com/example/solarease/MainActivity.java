package com.example.solarease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {
    private EditText latitud;
    private EditText longitud;
    private EditText area;
    private EditText inclinacion;
    private Button btnCalcularEnergia;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();

        btnCalcularEnergia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = Double.parseDouble(latitud.getText().toString());
                double longit = Double.parseDouble(longitud.getText().toString());
                double are = Double.parseDouble(area.getText().toString());
                int inclin = Integer.parseInt(inclinacion.getText().toString());

                double produccionEnergia = calcularProduccionEnergia(lat, longit, are, inclin);

                result.setText(String.valueOf(produccionEnergia));


            }
        });
    }
    private void inicializar(){
        latitud = (EditText) findViewById(R.id.txtLatitud);
        longitud = (EditText) findViewById(R.id.txtLongitud);
        area = (EditText) findViewById(R.id.txtArea);
        inclinacion = (EditText) findViewById(R.id.txtInclinacion);
        btnCalcularEnergia = (Button) findViewById(R.id.btnEnergia);
        result = (TextView) findViewById(R.id.txvResult);
    }
    private double calcularProduccionEnergia(double latitud, double longitud, double area, int inclinacion) {
        // Convertir latitud, longitud e inclinación a radianes
        double latitudRad = Math.toRadians(latitud);
        double longitudRad = Math.toRadians(longitud);
        double inclinacionRad = Math.toRadians(inclinacion);

        // Obtener día del año actual
        int diaDelAnio = LocalDate.now().getDayOfYear();

        // Calcular ángulo de incidencia de la radiación solar
        double anguloIncidencia = Math.acos(Math.sin(latitudRad) * Math.sin(inclinacionRad) + Math.cos(latitudRad) * Math.cos(inclinacionRad) * Math.cos(longitudRad));

        // Calcular radiación solar incidente
        double constanteSolar = 0.1367; // kWh/m²
        double radiacion = constanteSolar * Math.cos(anguloIncidencia) * (1 + 0.033 * Math.cos(Math.toRadians(360 * diaDelAnio / 365.0)));

        // Calcular producción de energía
        double areaPanel = area / 10000.0; // convertir a m²
        double eficienciaPanel = 0.16; // 16% de eficiencia
        double factorPerdidas = 0.9; // pérdida del 10%
        double produccionEnergia = areaPanel * radiacion * eficienciaPanel * factorPerdidas;

        return produccionEnergia;
    }


}