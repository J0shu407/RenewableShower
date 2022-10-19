package de.joshi.renewableshower.data

import de.joshi.renewableshower.api.ApiClient
import de.joshi.renewableshower.api.DataResolution
import de.joshi.renewableshower.api.EnergyForm
import de.joshi.renewableshower.model.EnergyMeasurement
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.MathContext
import java.util.Date

@Component
class DataProcessing(val apiClient: ApiClient) {

    fun getPortionOfWholeProduction(energyForm: EnergyForm): BigDecimal {
        val timestamp: Date = apiClient.getLatestAvailableTimestamp(energyForm)

        val powerProdInfo: EnergyMeasurement =
            apiClient.getLatestEnergyMeasurement(energyForm, timestamp);

        val totalPowerProduction: BigDecimal = getTotalProduction()

        return powerProdInfo.value!! / totalPowerProduction
    }
    fun getPortionOfWholeProduction(energyForms: List<EnergyForm>): BigDecimal {
        var portion: BigDecimal = BigDecimal(0.0)

        energyForms.forEach {
            portion += getPortionOfWholeProduction(it)
        }

        return portion
    }

    fun getTotalProduction(): BigDecimal {
        var powerProduction: BigDecimal = BigDecimal(0.0)

        EnergyForm.values().forEach {

            val timestamp: Date = apiClient.getLatestAvailableTimestamp(it)

            val powerProdInfo: EnergyMeasurement =
                apiClient.getLatestEnergyMeasurement(it, timestamp);

            powerProduction += powerProdInfo.value!!
        }

        return powerProduction
    }
}