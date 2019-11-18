package kinoplan.testapp.spacexscheduler.pojos

data class Links(
    val wikipedia : String?,
    val reddit : String?,
    val article : String?,
    val video : String?
) {

    override fun toString(): String {
        return "Links(wikipedia=$wikipedia, reddit=$reddit, article=$article, video=$video)"
    }
}