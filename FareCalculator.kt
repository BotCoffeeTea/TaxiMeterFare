class FareCalculator(private val context: Context) {

    private var baseFare: Double = 0.0

    private var perKmFare: Double = 0.0

    private var perMinuteFare: Double = 0.0

    private var currency: String = ""

    init {

        // Khởi tạo các thông số giá vé mặc định

        baseFare = 5000.0

        perKmFare = 1000.0

        perMinuteFare = 1000.0

        currency = "VND"

    }

    fun setFare(baseFare: Double, perKmFare: Double, perMinuteFare: Double, currency: String) {

        // Cập nhật giá vé

        this.baseFare = baseFare

        this.perKmFare = perKmFare

        this.perMinuteFare = perMinuteFare

        this.currency = currency

    }

    fun calculateFare(distance: Double, time: Double): Double {

        // Tính toán giá vé dựa trên khoảng cách và thời gian

        val fare = baseFare + distance * perKmFare + time * perMinuteFare

        return roundOff(fare)

    }

    fun getFareDetails(distance: Double, time: Double): String {

        // Lấy chi tiết giá vé dựa trên khoảng cách và thời gian

        val baseFareText = getFormattedText("Base Fare", baseFare)

        val distanceFareText = getFormattedText("Distance Fare", distance * perKmFare)

        val timeFareText = getFormattedText("Time Fare", time * perMinuteFare)

        val totalFare = calculateFare(distance, time)

        val totalFareText = getFormattedText("Total Fare", totalFare)

        return "$baseFareText\n$distanceFareText\n$timeFareText\n------------------------\n$totalFareText"

    }

    private fun getFormattedText(title: String, value: Double): String {

        // Định dạng chuỗi hiển thị chi tiết giá vé

        val formattedValue = NumberFormat.getNumberInstance(Locale.getDefault()).format(value)

        return "$title : $formattedValue $currency"

    }

    private fun roundOff(value: Double): Double {

        // Làm tròn giá vé đến hai chữ số thập phân

        return (value * 100).roundToLong().toDouble() / 100

    }

}
