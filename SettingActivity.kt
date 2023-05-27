import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        // Thiết lập các giá trị ban đầu cho các editText

        etStartFare.setText("10000")

        etFarePerKm1.setText("12000")

        etFarePerKm2.setText("10000")

        etCurrency.setText("VND")

        // Xử lý sự kiện khi người dùng ấn nút lưu cài đặt

        btnSave.setOnClickListener {

            // Lưu giá trị cài đặt vào SharedPreferences

            val sharedPref = getSharedPreferences("settings", MODE_PRIVATE)

            with(sharedPref.edit()) {

                putInt("start_fare", etStartFare.text.toString().toInt())

                putInt("fare_per_km1", etFarePerKm1.text.toString().toInt())

                putInt("fare_per_km2", etFarePerKm2.text.toString().toInt())

               putString("currency", etCurrency.text.toString())

                apply()

            }

            // Hiển thị thông báo lưu thành công

            Toast.makeText(this, "Đã lưu cài đặt thành công", Toast.LENGTH_SHORT).show()

        }

        // Xử lý sự kiện khi người dùng ấn nút đặt lại cài đặt mặc định

        btnReset.setOnClickListener {

            // Đặt lại giá trị cài đặt mặc định

            etStartFare.setText("10000")

            etFarePerKm1.setText("12000")

            etFarePerKm2.setText("10000")

            etCurrency.setText("VND")

            // Lưu giá trị cài đặt vào SharedPreferences

            val sharedPref = getSharedPreferences("settings", MODE_PRIVATE)

            with(sharedPref.edit()) {

                putInt("start_fare", 10000)

                putInt("fare_per_km1", 12000)

                putInt("fare_per_km2", 10000)

                putString("currency", "VND")

                apply()

            }

            // Hiển thị thông báo đặt lại cài đặt thành công

            Toast.makeText(this, "Đã đặt lại cài đặt thành công", Toast.LENGTH_SHORT).        }

        // Load giá trị cài đặt từ SharedPreferences và hiển thị lên editText

        val sharedPref = getSharedPreferences("settings", MODE_PRIVATE)

        etStartFare.setText(sharedPref.getInt("start_fare", 10000).toString())

        etFarePerKm1.setText(sharedPref.getInt("fare_per_km1", 12000).toString())

        etFarePerKm2.setText(sharedPref.getInt("fare_per_km2", 10000).toString())

        etCurrency.setText(sharedPref.getString("currency", "VND"))

    }

}
