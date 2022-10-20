package de.joshi.renewableshower.model

import com.fasterxml.jackson.annotation.JsonProperty
import de.joshi.renewableshower.api.EnergyForm
import java.math.BigDecimal
import java.util.Date

class CompleteEnergyData(
    @JsonProperty("timestamp")
    val time: Date,
    @JsonProperty("total_production")
    val totalProduction: BigDecimal,
    @JsonProperty("protion_of_renewables")
    val portionOfRenewables: BigDecimal,
    @JsonProperty("protion_of_conventionales")
    val portionOfConventionales: BigDecimal,

    val energyMeasurements: Map<EnergyForm, BigDecimal>
) {
}