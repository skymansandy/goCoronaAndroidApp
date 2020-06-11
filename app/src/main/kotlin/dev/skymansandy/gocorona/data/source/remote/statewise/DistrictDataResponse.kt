package dev.skymansandy.gocorona.data.source.remote.statewise

data class DistrictDataResponse(
    val state: String,
    val statecode: String,
    val districtData: List<DistrictDataItem>
)

data class DistrictDataItem(
    val recovered: String,
    val notes: String,
    val deceased: String,
    val district: String,
    val delta: Delta,
    val active: String,
    val confirmed: String
)

data class Delta(
    val recovered: String,
    val deceased: String,
    val confirmed: String
)