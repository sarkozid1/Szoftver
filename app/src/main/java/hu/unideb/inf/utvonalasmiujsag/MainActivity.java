package hu.unideb.inf.utvonalasmiujsag;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etStartLocation, etEndLocation, etDistance, etFuelConsumption;
    private Button btnSave;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EditText mezők és gomb inicializálása
        etStartLocation = findViewById(R.id.etStartLocation);
        etEndLocation = findViewById(R.id.etEndLocation);
        etDistance = findViewById(R.id.etDistance);
        etFuelConsumption = findViewById(R.id.etFuelConsumption);
        btnSave = findViewById(R.id.btnSave);

        // Adatbázis inicializálása Room segítségével
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "route-database").build();

        // Mentés gomb eseménykezelője
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoute();
            }
        });
    }

    // Útvonal mentése a Room adatbázisba
    private void saveRoute() {
        // Adatok lekérése a mezőkből
        String startLocation = etStartLocation.getText().toString();
        String endLocation = etEndLocation.getText().toString();
        String distanceStr = etDistance.getText().toString();
        String fuelConsumptionStr = etFuelConsumption.getText().toString();

        // Ellenőrzés, hogy minden mezőt kitöltöttek-e
        if (startLocation.isEmpty() || endLocation.isEmpty() || distanceStr.isEmpty() || fuelConsumptionStr.isEmpty()) {
            Toast.makeText(this, "Minden mezőt ki kell tölteni!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Számértékek konvertálása
        double distance = Double.parseDouble(distanceStr);
        double fuelConsumption = Double.parseDouble(fuelConsumptionStr);

        // Új Route objektum létrehozása
        Route newRoute = new Route();
        newRoute.setStartLocation(startLocation);
        newRoute.setEndLocation(endLocation);
        newRoute.setDistance(distance);
        newRoute.setFuelConsumption(fuelConsumption);

        // Útvonal mentése egy új szálon az adatbázisba
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.routeDao().insertRoute(newRoute);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Útvonal mentve!", Toast.LENGTH_SHORT).show();
                        clearFields();
                    }
                });
            }
        }).start();
    }

    // Mezők ürítése a mentés után
    private void clearFields() {
        etStartLocation.setText("");
        etEndLocation.setText("");
        etDistance.setText("");
        etFuelConsumption.setText("");
    }
}
