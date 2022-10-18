package de.joshi.renewableshower.api

interface ApiClient {
    fun getAvailableLocales(filterIds: FilterIds, resolution: DataResolution): Map<*, *>

    fun getPowerProductionInformation(filterIds: FilterIds, resolution: DataResolution): String
}