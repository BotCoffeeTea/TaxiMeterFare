import android.content.Context

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.example.taximeterfare.R

import java.text.SimpleDateFormat

import java.util.*

class TripHistoryAdapter(private val context: Context, private val trips: List<Trip>) : RecyclerView.Adapter<TripHistoryAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val startTextView: TextView = view.findViewById(R.id.startTextView)

        val endTextView: TextView = view.findViewById(R.id.endTextView)

        val distanceTextView: TextView = view.findViewById(R.id.distanceTextView)

        val timeTextView: TextView = view.findViewById(R.id.timeTextView)

        val fareTextView: TextView = view.findViewById(R.id.fareTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.trip_history_item, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val trip = trips[position]

        holder.startTextView.text = context.getString(R.string.start_location, trip.start.latitude, trip.start.longitude)

        holder.endTextView.text = context.getString(R.string.end_location, trip.end.latitude, trip.end.longitude)

        holder.distanceTextView.text = context.getString(R.string.distance_km, trip.distance)

        holder.timeTextView.text = context.getString(R.string.time_minute, trip.time)

        holder.fareTextView.text = context.getString(R.string.fare_vnd, trip.fare)

    }

    override fun getItemCount(): Int {

        return trips.size

    }

}
