import android.content.Context

import android.graphics.Bitmap

import android.graphics.Canvas

import android.graphics.Paint

import android.graphics.Typeface

import android.view.LayoutInflater

import android.view.View

import android.widget.TextView

import androidx.core.content.ContextCompat

import com.google.android.gms.maps.model.BitmapDescriptor

import com.google.android.gms.maps.model.BitmapDescriptorFactory

import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.MarkerOptions

class CustomMarker(private val context: Context) {

    fun getMarkerOptions(position: LatLng, title: String, isImageMarker: Boolean): MarkerOptions {

        val markerOptions = MarkerOptions().position(position)

        if (isImageMarker) {

            // Tạo marker từ icon hình ảnh

            val bitmapDescriptor = bitmapDescriptorFromVector(context, R.drawable.ic_car)

            markerOptions.icon(bitmapDescriptor)

        } else {

            // Tạo marker từ biển số xe

            val view = LayoutInflater.from(context).inflate(R.layout.custom_marker, null)

            val textView = view.findViewById<TextView>(R.id.tv_marker)

            textView.text = title

            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(getBitmapFromView(view))

            markerOptions.icon(bitmapDescriptor)

        }

        return markerOptions

    }

    fun getPolylineOptions(points: List<LatLng>): PolylineOptions {

        val polylineOptions = PolylineOptions()

        polylineOptions.addAll(points)

        polylineOptions.width(10f)

        polylineOptions.color(ContextCompat.getColor(context, R.color.colorPrimary))

        return polylineOptions

    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {

        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        vectorDrawable?.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

        val bitmap = Bitmap.createBitmap(vectorDrawable!!.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)

        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)

    }

    private fun getBitmapFromView(view: View): Bitmap {

        val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

        view.measure(measureSpec, measureSpec)

        val measuredWidth = view.measuredWidth

        val measuredHeight = view.measuredHeight

        view.layout(0, 0, measuredWidth, measuredHeight)

        val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)

       val canvas = Canvas(bitmap)

        val backgroundPaint = Paint()

        backgroundPaint.color = ContextCompat.getColor(context, R.color.colorAccent)

        canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), backgroundPaint)

        view.draw(canvas)

        return bitmap

    }

}
