package dev.skymansandy.gocorona.data.source.remote.statewise

data class DistrictDataResponse(
    val state: String,
    val statecode: String,
    val districtData: List<DistrictDataItem>
)

data class DistrictDataItem(
    val district: String,
    val notes: String,
    val recovered: Int?,
    val deceased: Int?,
    val delta: Delta,
    val active: Int,
    val confirmed: Int
)

data class Delta(
    val recovered: Int?,
    val deceased: Int?,
    val confirmed: Int?
)