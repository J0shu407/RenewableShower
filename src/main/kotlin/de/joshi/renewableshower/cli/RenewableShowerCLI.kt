package de.joshi.renewableshower.cli

import de.joshi.renewableshower.api.DataResolution
import de.joshi.renewableshower.api.FilterIds
import de.joshi.renewableshower.api.SmardBasedApiClient
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class RenewableShowerCLI(val smardBasedApiClient: SmardBasedApiClient) : CommandLineRunner{

    override fun run(vararg args: String?) {
        System.out.println(smardBasedApiClient.getAvailableLocales(FilterIds.BROWN_COAL, DataResolution.HOUR).get("timestamps"));
    }
}