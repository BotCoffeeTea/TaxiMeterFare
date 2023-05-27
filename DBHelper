import android.content.Context

import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "taximeter.db"

        private const val DATABASE_VERSION = 1

        private const val TABLE_TRIP = "trip"

        private const val COLUMN_ID = "id"

        private const val COLUMN_START = "start"

        private const val COLUMN_END = "end"

        private const val COLUMN_DISTANCE = "distance"

        private const val COLUMN_TIME = "time"

        private const val COLUMN_FARE = "fare"

    }

    override fun onCreate(db: SQLiteDatabase) {

        // Tạo bảng trip để lưu trữ chuyến đi

        val CREATE_TRIP_TABLE = ("CREATE TABLE $TABLE_TRIP("

                + "$COLUMN_ID INTEGER PRIMARY KEY,"

                + "$COLUMN_START TEXT,"

                + "$COLUMN_END TEXT,"

                + "$COLUMN_DISTANCE REAL,"

                + "$COLUMN_TIME REAL,"

                + "$COLUMN_FARE REAL)")

        db.execSQL(CREATE_TRIP_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        // Xóa bảng trip cũ nếu tồn tại và tạo lại bảng mới

        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRIP")

        onCreate(db)

    }

    fun addTrip(trip: Trip): Long {

        // Thêm chuyến đi vào cơ sở dữ liệu và trả về id của chuyến đi vừa được thêm

        val db = this.writableDatabase

        val values = ContentValues()

        values.put(COLUMN_START, trip.start)

        values.put(COLUMN_END, trip.end)

        values.put(COLUMN_DISTANCE, trip.distance)

        values.put(COLUMN_TIME, trip.time)

        values.put(COLUMN_FARE, trip.fare)

        val id = db.insert(TABLE_TRIP, null, values)

        db.close()

        return id

    }

    fun getAllTrips(): ArrayList<Trip> {

        // Lấy tất cả các chuyến đi từ cơ sở dữ liệu và trả về dưới dạng danh sách các đối tượng Trip

        val tripList = ArrayList<Trip>()

        val query = "SELECT * FROM $TABLE_TRIP ORDER BY $COLUMN_ID DESC"

        val db= this.readableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {

            do {

                val trip = Trip(

                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),

                    cursor.getString(cursor.getColumnIndex(COLUMN_START)),

                    cursor.getString(cursor.getColumnIndex(COLUMN_END)),

                    cursor.getDouble(cursor.getColumnIndex(COLUMN_DISTANCE)),

                    cursor.getDouble(cursor.getColumnIndex(COLUMN_TIME)),

                    cursor.getDouble(cursor.getColumnIndex(COLUMN_FARE))

                )

                tripList.add(trip)

            } while (cursor.moveToNext())

        }

        cursor.close()

        db.close()

        return tripList

    }

    fun deleteTrip(id: Int): Boolean {

        // Xóa chuyến đi khỏi cơ sở dữ liệu theo id

        val db = this.writableDatabase

        val result = db.delete(TABLE_TRIP, "$COLUMN_ID=?", arrayOf(id.toString()))

        db.close()

        return result > 0

    }

}
