package dev.skymansandy.gocorona.data.source.remote.brief

data class StatesDataResponse(
    val cases_time_series: List<CasesTimeSeries>,
    val statewise: List<Statewise>,
    val tested: List<Tested>
)

data class CasesTimeSeries(
    val dailyconfirmed: String,
    val dailydeceased: String,
    val dailyrecovered: String,
    val date: String,
    val totalconfirmed: String,
    val totaldeceased: String,
    val totalrecovered: String
)

data class Statewise(
    val active: String,
    val confirmed: String,
    val deaths: String,
    val deltaconfirmed: String,
    val deltadeaths: String,
    val deltarecovered: String,
    val lastupdatedtime: String,
    val migratedother: String,
    val recovered: String,
    val state: String,
    val statecode: String,
    val statenotes: String
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