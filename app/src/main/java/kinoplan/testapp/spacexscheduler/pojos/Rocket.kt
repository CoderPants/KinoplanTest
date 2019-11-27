package kinoplan.testapp.spacexscheduler.pojos

data class Rocket (
    val name : String,
    val type : String,
    val firstStage: FirstStage,
    val secondStage: SecondStage
)