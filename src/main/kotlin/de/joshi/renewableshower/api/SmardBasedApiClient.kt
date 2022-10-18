package de.joshi.renewableshower.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.net.URL

@Component
class SmardBasedApiClient(val mapper: ObjectMapper) : ApiClient {

    override fun getAvailableLocales(filterIds: FilterIds, resolution: DataResolution): Map<*, *> {
        return mapper.readValue(
            URL("https://smard.api.proxy.bund.dev/app/chart_data/${filterIds.id}/DE/index_${resolution.name.lowercase()}.json"),
            Map::class.java
        );
    }

    override fun getPowerProductionInformation(filterIds: FilterIds, resolution: DataResolution): String {
        TODO("Not yet implemented")
    }
}