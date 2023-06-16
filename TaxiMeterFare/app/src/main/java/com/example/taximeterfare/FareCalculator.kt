import android.content.Context
import com.example.taximeterfare.R

class FareCalculator(private val context: Context) {

    private val initialFare = context.resources.getInteger(R.integer.initial_fare) // Giá mở cửa ban đầu

    private val distanceRate = context.resources.getInteger(R.integer.distance_rate) // Giá vé/km trong khoảng quãng đường từ 0.3km đến 25km

    private val longDistanceRate = context.resources.getInteger(R.integer.long_distance_rate) // Giá vé/km khi quãng đường lớn hơn 25km

    fun calculateFare(distance: Double, duration: Long): Int {

        val fare = initialFare + (distance * distanceRate).toInt()

        return if (distance > 25) {

            fare + ((distance - 25) * longDistanceRate).toInt()

        } else {

            fare

        }

    }

}