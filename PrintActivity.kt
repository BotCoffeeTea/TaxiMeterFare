import android.bluetooth.BluetoothAdapter

import android.bluetooth.BluetoothDevice

import android.bluetooth.BluetoothSocket

import android.os.Bundle

import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_print.*

import java.io.IOException

import java.io.OutputStream

import java.util.*

class PrintActivity : AppCompatActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter

    private lateinit var socket: BluetoothSocket

    private lateinit var outputStream: OutputStream

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_print)

        // Lấy địa chỉ Bluetooth của máy in từ intent

        val deviceAddress = intent.getStringExtra("device_address")

        // Thiết lập kết nối Bluetooth và gửi dữ liệu in hoá đơn

        connectToPrinter(deviceAddress)

        printReceipt()

        // Đóng kết nối Bluetooth

        closeConnection()

        // Hiển thị thông báo in hoá đơn thành công

        Toast.makeText(this, "Đã in hoá đơn thành công", Toast.LENGTH_SHORT).show()

    }

    private fun connectToPrinter(deviceAddress: String) {

        // Lấy đối tượng BluetoothAdapter để kiểm tra thiết bị Bluetooth và lấy đối tượng BluetoothDevice của máy in

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        // Kiểm tra xem Bluetooth có bật hay không

        if (!bluetoothAdapter.isEnabled) {

            // Nếu chưa bật thì yêu cầu người dùng bật Bluetooth

            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

            startActivityForResult(enableBluetoothIntent, 0)

        }

        // Lấy đối tượng BluetoothDevice của máy in từ địa chỉ Bluetooth

        val device: BluetoothDevice? = bluetoothAdapter.getRemoteDevice(deviceAddress)

        // Tạo socket Bluetooth và kết nối đến máy in

        try {

            socket = device?.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))!!

            socket.connect()

            // Lấy outputStream để gửi dữ liệu in hoá đơn

            outputStream = socket.outputStream

        } catch (e: IOException) {

            e.printStackTrace()

            Toast.makeText(this, "Không thể kết nối đến máy in", Toast.LENGTH_SHORT).show()

        }

    }

    private fun printReceipt() {

        // Lấy dữ liệu in hoá đơn từ intent

       Còn tiếp phần mã hoàn chỉnh và đầy đủ của tệp PrintActivity.kt:
