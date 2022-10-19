package de.joshi.renewableshower.cli

import de.joshi.renewableshower.api.EnergyForm
import de.joshi.renewableshower.api.SmardBasedApiClient
import de.joshi.renewableshower.config.RenewableShowerProperties
import de.joshi.renewableshower.data.DataProcessing
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class RenewableShowerCLI(val dataProcessing: DataProcessing) : CommandLineRunner{

    override fun run(vararg args: String?) {
        println(dataProcessing.getTotalProduction())
    }
}