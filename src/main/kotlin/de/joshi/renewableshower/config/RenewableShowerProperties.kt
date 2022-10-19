package de.joshi.renewableshower.config

import de.joshi.renewableshower.api.DataResolution
import de.joshi.renewableshower.api.EnergyForm
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "shower")
open class RenewableShowerProperties {
    var api: Api = Api()
    class Api {
        var data: Data = Data()
        class Data {
            var resolution: DataResolution = DataResolution.QUARTERHOUR
        }
    }
}