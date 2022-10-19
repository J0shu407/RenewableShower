package de.joshi.renewableshower.api

import com.fasterxml.jackson.databind.ObjectMapper
import de.joshi.renewableshower.model.EnergyDataSlice
import de.joshi.renewableshower.config.RenewableShowerProperties
import de.joshi.renewableshower.model.EnergyMeasurement
import org.springframework.stereotype.Component
import java.net.URI
import java.net.URL
import java.net.URLConnection
import java.net.http.HttpRequest
import java.util.*

@Component
class SmardBasedApiClient(val mapper: ObjectMapper, val properties: RenewableShowerProperties) : ApiClient {

    override fun getAvailableTimestamps(energyForm: EnergyForm): List<Date> {
        val connection: URLConnection =
            URL("https://smard.api.proxy.bund.dev/app/chart_data/${energyForm.id}/DE/index_${properties.api.data.resolution.name}.json").openConnection()
        val scanner: Scanner = Scanner(connection.getInputStream())
        scanner.useDelimiter("\\Z")
        val json: String = scanner.next()
        scanner.close()

        val values = mapper.readValue(
            json,
            Map::class.java
        )["timestamps"] as List<Long>

        val timestamps: MutableList<Date> = mutableListOf()
        for (value in values) {
            timestamps += Date(value)
        }

        return timestamps;
    }

    override fun getLatestAvailableTimestamp(energyForm: EnergyForm): Date {
        return getAvailableTimestamps(energyForm).last();
    }

    override fun getActualLatestTimestamp(): Date {
        return getLatestEnergyMeasurement(
            EnergyForm.BIOMASS,
            getLatestAvailableTimestamp(EnergyForm.BIOMASS)
        ).timestamp!!
    }

    override fun getEnergyProductionData(energyForm: EnergyForm, timestamp: Date): EnergyDataSlice {
        val connection: URLConnection =
            URL("https://smard.api.proxy.bund.dev/app/chart_data/${energyForm.id}/DE/${energyForm.id}_DE_${properties.api.data.resolution.name}_${timestamp.time}.json").openConnection()
        val scanner: Scanner = Scanner(connection.getInputStream())
        scanner.useDelimiter("\\Z");
        val json: String = scanner.next()
        scanner.close()

        return mapper.readValue(
            json,
            EnergyDataSlice::class.java
        );
    }

    override fun getLatestEnergyMeasurement(
        energyForm: EnergyForm,
        timestamp: Date
    ): EnergyMeasurement {
        val values: EnergyDataSlice = getEnergyProductionData(energyForm, timestamp)

        var i: Int = 0
        run breaking@{
            values.measurements!!.forEach { list ->
                if (list.value == null) {
                    return@breaking
                } else {
                    i++;
                }
            }
        }
        return values.measurements!![i - 1]
    }
}