package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.source.db.dao.DistrictDataDao
import javax.inject.Inject

class GetDistrictWiseDataUseCase @Inject constructor(
    private val districtDataDao: DistrictDataDao
) {

}