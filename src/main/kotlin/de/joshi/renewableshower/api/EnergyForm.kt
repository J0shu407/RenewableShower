package de.joshi.renewableshower.api

enum class EnergyForm(val id: Int) {

    BROWN_COAL(1223),
    NUCLEAR(1224),
    NATURAL_GAS(4071),
    HARD_COAL(4069),
    OTHER_CONVENTIONAL(1227),

    WIND_OFFSHORE(1225),
    HYDRO(1226),
    BIOMASS(4066),
    WIND_ONSHORE(4067),
    PHOTOVOLTAIC(4068),
    OTHER_RENEWABLE(1228),

    PUMP_STORAGE(4070);

    companion object {
        val RENEWABLES: List<EnergyForm> = listOf(
            WIND_OFFSHORE,
            HYDRO,
            BIOMASS,
            WIND_ONSHORE,
            PHOTOVOLTAIC,
            OTHER_RENEWABLE
        )

        val CONVENTIONAL: List<EnergyForm> = listOf(
            BROWN_COAL,
            NUCLEAR,
            NATURAL_GAS,
            HARD_COAL,
            OTHER_CONVENTIONAL
        )

        val OTHER: List<EnergyForm> = listOf(
            PUMP_STORAGE
        )
    }
}