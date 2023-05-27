import android.content.ContentValues

import android.content.Context

import android.database.Cursor

import android.database.sqlite.SQLiteDatabase

import com.google.android.gms.maps.model.LatLng

import java.text.SimpleDateFormat

import java.util.*

class Trip(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    var id: Long = 0

    var start: LatLng = LatLng(0.0, 0.0)

    var end: LatLng = LatLng(0.0, 0.0)

    var distance: Double = 0.0

    var time: Double = 0.0

    var fare: Double = 0.0

    var startTime: Date = Date()

    var endTime: Date = Date()

    fun save() {

        // Lưu thông tin chuyến đi vào cơ sở dữ liệu

        val db = dbHelper.writableDatabase

        val values = ContentValues()

        values.put(DatabaseContract.TripEntry.COLUMN_START_LATITUDE, start.latitude)

        values.put(DatabaseContract.TripEntry.COLUMN_START_LONGITUDE, start.longitude)

        values.put(DatabaseContract.TripEntry.COLUMN_END_LATITUDE, end.latitude)

        values.put(DatabaseContract.TripEntry.COLUMN_END_LONGITUDE, end.longitude)

        values.put(DatabaseContract.TripEntry.COLUMN_DISTANCE, distance)

        values.put(DatabaseContract.TripEntry.COLUMN_TIME, time)

        values.put(DatabaseContract.TripEntry.COLUMN_FARE, fare)

        values.put(DatabaseContract.TripEntry.COLUMN_START_TIME, dateFormat.format(startTime))

        values.put(DatabaseContract.TripEntry.COLUMN_END_TIME, dateFormat.format(endTime))

        id = db.insert(DatabaseContract.TripEntry.TABLE_NAME, null, values)

        db.close()

    }

    fun update() {

        // Cập nhật thông tin chuyến đi trong cơ sở dữ liệu

        val db = dbHelper.writableDatabase

        val values = ContentValues()

        values.put(DatabaseContract.TripEntry.COLUMN_START_LATITUDE, start.latitude)

        values.put(DatabaseContract.TripEntry.COLUMN_START_LONGITUDE, start.longitude)

        values.put(DatabaseContract.TripEntry.COLUMN_END_LATITUDE, end.latitude)

        values.put(DatabaseContract.TripEntry.COLUMN_END_LONGITUDE, end.longitude)

        values.put(DatabaseContract.TripEntry.COLUMN_DISTANCE, distance)

        values.put(DatabaseContract.TripEntry.COLUMN_TIME, time)

        values.put(DatabaseContract.TripEntry.COLUMN_FARE, fare)

        values.put(DatabaseContract.TripEntry.COLUMN_START_TIME, dateFormat.format(startTime))

        values.put(DatabaseContract.TripEntry.COLUMN_END_TIME, dateFormat.format(endTime))

        db.update(DatabaseContract.TripEntry.TABLE_NAME, values, "${DatabaseContract.TripEntry._ID} = ?", arrayOf(id.toString()))

        db.close()

    }

    fun delete() {

        // Xoá thông tin chuyến đi khỏi cơ sở dữ liệu

        val db = dbHelper.writableDatabase

        db.delete(DatabaseContract.TripEntry.TABLE_NAME, "${DatabaseContract.TripEntry._ID} = ?", arrayOf(id.toString()))

        db.close()

    }

    companion object {

        fun getAllTrips(context: Context): List<Trip> {

            // Lấy danh sách tất cả các chuyến đi từ cơ sở dữ liệu

            val dbHelper = DatabaseHelper(context)

            val db = dbHelper.readableDatabase

            val columns = arrayOf(DatabaseContract.TripEntry._ID, DatabaseContract.TripEntry.COLUMN_START_LATITUDE, DatabaseContract.TripEntry.COLUMN_START_LONGITUDE, DatabaseContract.TripEntry.COLUMN_END_LATITUDE, DatabaseContract.TripEntry.COLUMN_END_LONGITUDE, DatabaseContract.TripEntry.COLUMN_DISTANCE, DatabaseContract.TripEntry.COLUMN_TIME, DatabaseContract.TripEntry.COLUMN_FARE, DatabaseContract.TripEntry.COLUMN_START_TIME, DatabaseContract.TripEntry.COLUMN_END_TIME)

            val cursor = db.query(DatabaseContract.TripEntry.TABLE_NAME, columns, null, null, null, null, null)

            val trips = mutableListOf<Trip>()

            while (cursor.moveToNext()) {

                // Lấy thông tin của từng chuyến đi từ cursor

                val trip = Trip(context)

                trip.id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.TripEntry._ID))

                val startLatitude = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TripEntry.COLUMN_START_LATITUDE))

                val startLongitude = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TripEntry.COLUMN_START_LONGITUDE))

                trip.start = LatLng(startLatitude, startLongitude)

                val endLatitude = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TripEntry.COLUMN_END_LATITUDE))

                val endLongitude = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TripEntry.COLUMN_END_LONGITUDE))

                trip.end = LatLng(endLatitude, endLongitude)

                trip.distance = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TripEntry.COLUMN_DISTANCE))

                trip.time = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TripEntry.COLUMN_TIME))

                trip.fare = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TripEntry.COLUMN_FARE))

                val startTimeString = cursor.getString(cursor.getColumnIndex(DatabaseContract.TripEntry.COLUMN_START_TIME))

                trip.startTime = dateFormat.parse(startTimeString)

                val endTimeString = cursor.getString(cursor.getColumnIndex(DatabaseContract.TripEntry.COLUMN_END_TIME))

                trip.endTime = dateFormat.parse(endTimeString)

                trips.add(trip)

            }

            cursor.close()

            db.close()

            return trips

        }

    }

}
