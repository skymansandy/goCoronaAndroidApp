package dev.skymansandy.gocorona.data.source.remote.brief

data class StatesDataResponse(
    val cases_time_series: List<CasesTimeSeries>,
    val statewise: List<Statewise>,
    val tested: List<Tested>
)

data class CasesTimeSeries(
    val date: String,
    val dailyconfirmed: Int?,
    val dailydeceased: Int?,
    val dailyrecovered: Int?,
    val totalconfirmed: Int?,
    val totaldeceased: Int?,
    val totalrecovered: Int?
)

data class Statewise(
    val state: String,
    val statecode: String,
    val statenotes: String,
    val active: Int?,
    val confirmed: Int?,
    val deaths: Int?,
    val deltaconfirmed: Int?,
    val deltadeaths: Int?,
    val deltarecovered: Int?,
    val lastupdatedtime: String,
    val migratedother: Int?,
    val recovered: Int?
)

data class Tested(
    val individualstestedperconfirmedcase: String,
    val positivecasesfromsamplesreported: String,
    val samplereportedtoday: String,
    val source: String,
    val testpositivityrate: String,
    val testsconductedbyprivatelabs: String,
    val testsperconfirmedcase: String,
    val testspermillion: String,
    val totalindividualstested: String,
    val totalpositivecases: String,
    val totalsamplestested: String,
    val updatetimestamp: String
)