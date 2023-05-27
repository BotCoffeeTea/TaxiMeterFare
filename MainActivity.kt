import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Hiển thị bản đồ

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->

            // Thiết lập các tùy chọn bản đồ

            val mapOptions = GoogleMapOptions().apply {

                mapType(GoogleMap.MAP_TYPE_NORMAL)

                zoomControlsEnabled(true)

                compassEnabled(true)

            }

            googleMap.uiSettings.isZoomControlsEnabled = true

            googleMap.uiSettings.isCompassEnabled = true

            // Thiết lập địa điểm hiện tại

            val currentLocation = LatLng(37.4221, -122.0841)

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14f))

            // Thiết lập icon cho vị trí hiện tại

            val markerOptions = MarkerOptions().position(currentLocation)

                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxi))

                // Thêm marker vào bản đồ

                googleMap.addMarker(markerOptions)

        }

        // Xử lý sự kiện khi người dùng ấn nút bắt đầu

        btnStart.setOnClickListener {

            // Tính toán giá cước

            val fare = FareCalculator.calculate(10.5, 25, 1.5, 1.0, 0.5)

            // Hiển thị kết quả và chuyển đến màn hình khác

            val intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("fare", fare)

            startActivity(intent)

        }

    }

}
