package kinoplan.testapp.spacexscheduler.pojos

data class SecondStage(
    val nationality : String?,
    val manufacturer : String?,
    val payloadType : String?,
    val customers : String?,
    val orbit : String?
) {
    override fun toString(): String {
        return "SecondStage(nationality='$nationality', manufacturer='$manufacturer', payloadType='$payloadType', customers='$customers', orbit='$orbit')"
    }
}