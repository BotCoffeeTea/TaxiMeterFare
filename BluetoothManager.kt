import android.bluetooth.BluetoothAdapter

import android.bluetooth.BluetoothDevice

import android.bluetooth.BluetoothSocket

import android.content.Context

import android.os.Handler

import android.util.Log

import java.io.IOException

import java.io.InputStream

import java.io.OutputStream

import java.util.*

class BluetoothManager(private val context: Context, private val handler: Handler) {

    companion object {

        const val TAG = "BluetoothManager"

        const val MESSAGE_STATE_CHANGE = 1

        const val MESSAGE_DEVICE_NAME = 2

        const val MESSAGE_TOAST = 3

        const val STATE_NONE = 0

        const val STATE_CONNECTING = 1

        const val STATE_CONNECTED = 2

    }

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private var connectThread: ConnectThread? = null

    private var connectedThread: ConnectedThread? = null

    private var state: Int = STATE_NONE

    fun connect(device: BluetoothDevice) {

        cancel()

        connectThread = ConnectThread(device)

        connectThread!!.start()

        setState(STATE_CONNECTING)

    }

    fun connected(socket: BluetoothSocket, device: BluetoothDevice) {

        cancel()

        connectedThread = ConnectedThread(socket)

        connectedThread!!.start()

        val message = handler.obtainMessage(MESSAGE_DEVICE_NAME)

        val bundle = Bundle().apply {

            putString("device_name", device.name)

        }

        message.data = bundle

        handler.sendMessage(message)

        setState(STATE_CONNECTED)

    }

    fun cancel() {

        connectThread?.cancel()

        connectThread = null

        connectedThread?.cancel()

        connectedThread = null

        setState(STATE_NONE)

    }

    fun write(bytes: ByteArray) {

        connectedThread?.write(bytes)

    }

    private fun setState(state: Int) {

        this.state = state

        handler.obtainMessage(MESSAGE_STATE_CHANGE, state, -1).sendToTarget()

    }

    inner class ConnectThread(private val device: BluetoothDevice) : Thread() {

        private val socket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {

            device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))

        }

        override fun run() {

            BluetoothAdapter.getDefaultAdapter().cancelDiscovery()

            try {

                socket?.connect()

            } catch (e: IOException) {

                setState(STATE_NONE)

                return

            }

            synchronized(this@BluetoothManager) {

                connectThread = null

            }

            connected(socket!!, device)

        }

        fun cancel() {

            try {

                socket?.close()

            } catch (e: IOException) {

                Log.e(TAG, "Could not close the client socket", e)

            }

        }

    }

    inner class ConnectedThread(private val socket: BluetoothSocket) : Thread() {

        private val inputStream: InputStream = socket.inputStream

        private val outputStream: OutputStream = socket.outputStream

        private val buffer = ByteArray(1024)

        override fun run() {

            var bytes: Int

            while (true) {

                try {

                    bytes = inputStream.read(buffer)

                    val message = handler.obtainMessage(MESSAGE_TOAST)

                    val bundle = Bundle().apply {

                        putString("toast", String(buffer, 0, bytes))

                    }

                    message.data = bundle

                    handler.sendMessage(message)

                } catch (e: IOException) {

                    Log.e(TAG, "disconnected", e)

                    setState(STATE_NONE)

                    break

                }

            }

        }

        fun write(bytes: ByteArray) {

            try {

                outputStream.write(bytes)

                handler.obtainMessage(MESSAGE_TOAST, -1, -1, "Sent message").sendToTarget()

            } catch (e: IOException) {

                Log.e(TAG, "Error occurred when sending data", e)

                handler.obtainMessage(MESSAGE_TOAST, -1, -1, "Couldn't send data").sendToTarget()

            }

        }

        fun cancel() {

            try {

                socket.close()

            } catch (e: IOException) {

                Log.e(TAG, "Could not closethe client socket", e)

            }

        }

    }

}
