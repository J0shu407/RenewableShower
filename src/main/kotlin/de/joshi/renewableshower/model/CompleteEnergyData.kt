package de.joshi.renewableshower.model

import com.fasterxml.jackson.annotation.JsonProperty
import de.joshi.renewableshower.api.EnergyForm
import java.math.BigDecimal
import java.util.Date

class CompleteEnergyData(

    @JsonProperty("timestamp")
    val time: Long,
    @JsonProperty("total_production")
    val totalProduction: BigDecimal,
    @JsonProperty("portion_of_renewables")
    val portionOfRenewables: BigDecimal,
    @JsonProperty("portion_of_conventionales")
    val portionOfConventionales: BigDecimal,

    @JsonProperty("energy_measurements")
    val energyMeasurements: MutableMap<String, BigDecimal>
)