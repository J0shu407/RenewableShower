package de.joshi.renewableshower.persistence

import de.joshi.renewableshower.model.CompleteEnergyData
import java.math.BigDecimal
import java.util.Date

interface DatabaseClient {
    fun setLatestTotalProduction(totalProduction: BigDecimal)
    fun setLatestPortionOfRenewables(portion: BigDecimal)
    fun setLatestPortionOfConventionales(portion: BigDecimal)
    fun setLatestTimestamp(date: Date)
    fun setLatestEnergyData(completeEnergyData: CompleteEnergyData)

    fun addInformation(completeEnergyData: CompleteEnergyData)
}