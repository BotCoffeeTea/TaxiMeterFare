import android.content.Context
import android.content.SharedPreferences

class FareSetting(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("fare_setting", Context.MODE_PRIVATE)

   fun saveFareForVehicle(vehicle: String, initialFare: Int, distanceRate: Int, longDistanceRate: Int) {

        val editor = sharedPreferences.edit()

        editor.putInt("$vehicle.initial_fare", initialFare)

        editor.putInt("$vehicle.distance_rate", distanceRate)

        editor.putInt("$vehicle.long_distance_rate", longDistanceRate)

       editor.apply()

  }

    fun removeFareForVehicle(vehicle: String) {

        val editor = sharedPreferences.edit()

        editor.remove("$vehicle.initial_fare")

        editor.remove("$vehicle.distance_rate")

        editor.remove("$vehicle.long_distance_rate")

        editor.apply()

    }

    fun getFareForVehicle(vehicle: String): Fare {

        val initialFare = sharedPreferences.getInt("$vehicle.initial_fare", 0)

        val distanceRate = sharedPreferences.getInt("$vehicle.distance_rate", 0)

        val longDistanceRate = sharedPreferences.getInt("$vehicle.long_distance_rate", 0)

        return Fare(initialFare, distanceRate, longDistanceRate)

    }

    data class Fare(val initialFare: Int, val distanceRate: Int, val longDistanceRate: Int)

}