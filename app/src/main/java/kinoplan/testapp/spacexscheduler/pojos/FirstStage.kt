package kinoplan.testapp.spacexscheduler.pojos

data class FirstStage (
    val coreSerial : String?,
    val gridFins : Boolean?,
    val legs : Boolean?,
    val landingPlace : String?
){
    override fun toString(): String {
        return "FirstStage(coreSerial=$coreSerial, gridFins=$gridFins, legs=$legs, landingPlace=$landingPlace)"
    }
}