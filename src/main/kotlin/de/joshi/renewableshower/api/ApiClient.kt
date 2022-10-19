package de.joshi.renewableshower.api

import de.joshi.renewableshower.model.EnergyDataSlice
import de.joshi.renewableshower.model.EnergyMeasurement
import java.util.*

interface ApiClient {
    fun getAvailableTimestamps(energyForm: EnergyForm): List<Date>
    fun getLatestAvailableTimestamp(energyForm: EnergyForm): Date

    fun getActualLatestTimestamp(): Date

    fun getEnergyProductionData(energyForm: EnergyForm, timestamp: Date): EnergyDataSlice
    fun getLatestEnergyMeasurement(energyForm: EnergyForm, timestamp: Date): EnergyMeasurement
}