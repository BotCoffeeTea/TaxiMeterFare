import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

import com.example.taximeterfare.R

import com.example.taximeterfare.model.Trip

import kotlinx.android.synthetic.main.activity_trip_history.*

class TripHistoryActivity : AppCompatActivity() {

    private lateinit var tripHistoryAdapter: TripHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_trip_history)

        // Lấy danh sách chuyến đi từ cơ sở dữ liệu SQLite

        val dbHandler = TripDatabaseHandler(this)

        val trips = dbHandler.getTrips()

        // Khởi tạo và thiết lập Adapter cho RecyclerView

        tripHistoryAdapter = TripHistoryAdapter(this, trips)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        tripHistoryRecyclerView.layoutManager = layoutManager

        tripHistoryRecyclerView.adapter = tripHistoryAdapter

        // Hiển thị tổng số chuyến đi và tổng số tiền trên giao diện

        val totalTrips = trips.size

        val totalFare = trips.sumByDouble { it.fare }

        totalTripsTextView.text =```

        getString(R.string.total_trips, totalTrips)

        totalFareTextView.text = getString(R.string.total_fare, totalFare)

    }

}
