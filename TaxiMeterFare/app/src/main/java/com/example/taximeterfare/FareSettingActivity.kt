import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.taximeterfare.R

class FareSettingActivity : AppCompatActivity() {

    private lateinit var vehicleNameEditText: EditText

    private lateinit var baseFareEditText: EditText

    private lateinit var perKmFareEditText: EditText

    private lateinit var longDistanceFareEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.fare_setting)

        vehicleNameEditText = findViewById(R.id.vehicle_edit_text)

        baseFareEditText = findViewById(R.id.initial_fare_edit_text)

        perKmFareEditText = findViewById(R.id.distance_rate_edit_text)

        longDistanceFareEditText = findViewById(R.id.long_distance_rate_edit_text)

        val saveButton = findViewById<Button>(R.id.save_button)

        saveButton.setOnClickListener {

            saveFareSettings()

            finish()

        }

        val deleteButton = findViewById<Button>(R.id.delete_button)

        deleteButton.setOnClickListener {

            deleteFareSettings()

            finish()

        }

        loadFareSettings()

    }

    private fun loadFareSettings() {

        val sharedPreferences = getSharedPreferences("fare_setting", Context.MODE_PRIVATE)

        val vehicleName = sharedPreferences.getString("vehicle_name", "") ?: ""

        val baseFare = sharedPreferences.getInt("base_fare", 0)

        val perKmFare = sharedPreferences.getInt("distance_rate", 0)

        val longDistanceFare = sharedPreferences.getInt("long_distance_rate", 0)

        vehicleNameEditText.setText(vehicleName)

        baseFareEditText.setText(baseFare.toString())

        perKmFareEditText.setText(perKmFare.toString())

        longDistanceFareEditText.setText(longDistanceFare.toString())

    }

    private fun saveFareSettings() {
        val vehicleName = vehicleNameEditText.text.toString()
        val baseFare = baseFareEditText.text.toString().toInt()
        val perKmFare = perKmFareEditText.text.toString().toInt()
        val longDistanceFare = longDistanceFareEditText.text.toString().toInt()

        val fareSetting = FareSetting(this)
        fareSetting.saveFareForVehicle(vehicleName, baseFare, perKmFare, longDistanceFare)

        finish()
    }

    private fun deleteFareSettings() {
        val vehicleName = vehicleNameEditText.text.toString()
        val fareSetting = FareSetting(this)
        fareSetting.removeFareForVehicle(vehicleName)
        finish()
    }

}