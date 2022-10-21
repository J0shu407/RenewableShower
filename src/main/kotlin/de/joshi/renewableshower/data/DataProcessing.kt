package de.joshi.renewableshower.data

import com.google.common.collect.ImmutableBiMap
import com.google.common.collect.ImmutableMap
import de.joshi.renewableshower.api.ApiClient
import de.joshi.renewableshower.api.DataResolution
import de.joshi.renewableshower.api.EnergyForm
import de.joshi.renewableshower.config.RenewableShowerConfiguration
import de.joshi.renewableshower.model.CompleteEnergyData
import de.joshi.renewableshower.model.EnergyMeasurement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.MathContext
import java.util.Date

@Component
class DataProcessing(val apiClient: ApiClient) {
    val LOGGER: Logger = LoggerFactory.getLogger(DataProcessing::class.java)

    fun getPortionOfWholeProduction(energyForm: EnergyForm, timestamp: Date): BigDecimal {
        val possibleTimestamp: Date = apiClient.getNearestPossibleTimestamp(timestamp)

        val powerProdInfo: EnergyMeasurement =
            apiClient.getExactEnergyMeasurement(energyForm, possibleTimestamp);

        val totalPowerProduction: BigDecimal = getTotalProduction(possibleTimestamp)

        return powerProdInfo.value!! / totalPowerProduction
    }
    fun getPortionOfWholeProduction(energyForms: List<EnergyForm>, timestamp: Date): BigDecimal {
        var portion: BigDecimal = BigDecimal(0.0)

        energyForms.forEach {
            portion += getPortionOfWholeProduction(it, timestamp)
        }

        return portion
    }

    fun getTotalProduction(timestamp: Date): BigDecimal {
        var powerProduction: BigDecimal = BigDecimal(0.0)

        EnergyForm.values().forEach {

            val possibleTimestamp: Date = apiClient.getNearestPossibleTimestamp(timestamp)

            val powerProdInfo: EnergyMeasurement =
                apiClient.getExactEnergyMeasurement(it, possibleTimestamp)

            powerProduction += powerProdInfo.value!!
        }

        return powerProduction
    }

    fun getCompleteEnergyData(timestamp: Date): CompleteEnergyData {
        var energyData = CompleteEnergyData(
            apiClient.getNearestPossibleTimestamp(timestamp).time,
            getTotalProduction(timestamp),
            getPortionOfWholeProduction(EnergyForm.RENEWABLES, timestamp),
            getPortionOfWholeProduction(EnergyForm.CONVENTIONAL, timestamp),
            mutableMapOf()
            )

        val possibleTimestamp = apiClient.getNearestPossibleTimestamp(timestamp)

        for(energyForm in EnergyForm.values()) {
            energyData.energyMeasurements[energyForm.name.lowercase()] = apiClient.getEnergyProductionData(energyForm, possibleTimestamp).measurements!![0].value!!
        }

        return energyData
    }
}