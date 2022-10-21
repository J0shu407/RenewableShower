package de.joshi.renewableshower.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.math.BigDecimal
import java.time.Instant
import java.util.*

class EnergyDataSlice(
    @JsonProperty("meta_data")
    var metaData: EnergyMetaData?,
    @JsonProperty("series")
    var measurements: List<EnergyMeasurement>?
)

class EnergyMetaData(
    @JsonProperty("version")
    var version: Int?,
    @JsonProperty("created")
    var created: Long?
)

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder( "timestamp", "value" )
class EnergyMeasurement() {
    @JsonProperty("timestamp")
    var timestamp: Date? = null
    @JsonProperty("value")
    var value: BigDecimal? = null
}