package de.joshi.renewableshower.api

import com.fasterxml.jackson.databind.ObjectMapper
import de.joshi.renewableshower.config.RenewableShowerProperties
import de.joshi.renewableshower.model.EnergyDataSlice
import de.joshi.renewableshower.model.EnergyMeasurement
import de.joshi.renewableshower.model.EnergyMetaData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import java.math.BigDecimal
import java.net.URL
import java.util.*
import kotlin.collections.HashMap
import kotlin.test.assertContentEquals

class SmardBasedApiClientTest {
    private lateinit var apiClient: SmardBasedApiClient

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var properties: RenewableShowerProperties
    private val energyData: EnergyDataSlice = EnergyDataSlice(EnergyMetaData(1, 34L), listOf(
        EnergyMeasurement().apply {
            value = BigDecimal(1.0)
            timestamp = Date(1234)
        },
        EnergyMeasurement().apply {
            value = BigDecimal(2.0)
            timestamp = Date(1234)
        },
        EnergyMeasurement().apply {
            value = null
            timestamp = Date(1234)
        }
    ))

    @BeforeEach
    fun setUp() {
        openMocks(this)
        apiClient = SmardBasedApiClient(objectMapper, properties)
        properties.api = RenewableShowerProperties.Api()
        properties.api.data = RenewableShowerProperties.Api.Data()
        properties.api.data.resolution = DataResolution.QUARTERHOUR

        `when`(
            objectMapper.readValue(
                anyString(),
                eq(EnergyDataSlice::class.java)
            )
        ).thenReturn(energyData)

        `when`(
            objectMapper.readValue(
                anyString(),
                eq(Map::class.java)
            )
        ).thenReturn(HashMap<String, List<Long>>().apply { put("timestamps", listOf(0, 1, 3)) })
    }

    @Test
    fun getAvailableTimestampsSuccessfully() {

        assertContentEquals(listOf(Date(0), Date(1), Date(3)), apiClient.getAvailableTimestamps(EnergyForm.HYDRO))
    }

    @Test
    fun getLatestAvailableTimestampSuccessfully() {

        assertEquals(Date(3), apiClient.getLatestAvailableTimestamp(EnergyForm.HYDRO))
    }

    @Test
    fun getNearestPossibleTimestamp() {
        val client: ApiClient = SmardBasedApiClient(ObjectMapper(), properties)
        properties.api = RenewableShowerProperties.Api()
        properties.api.data = RenewableShowerProperties.Api.Data()
        properties.api.data.resolution = DataResolution.QUARTERHOUR

        assertEquals(Date(1665957600000), client.getNearestPossibleTimestamp(Date(1665957600000)))
        assertEquals(Date(1665957600000), client.getNearestPossibleTimestamp(Date(1665957000000)))
        assertEquals(Date(1470002400000), client.getNearestPossibleTimestamp(Date(1470200000000)))
    }

    @Test
    fun getEnergyProductionDataSuccessfully() {

        assertEquals(
            energyData,
            apiClient.getEnergyProductionData(EnergyForm.HYDRO, Date(1649023200000))
        )
    }

    @Test
    fun getLatestEnergyMeasurementSuccessfully() {

        assertEquals(Date(1234L), apiClient.getLatestEnergyMeasurement(EnergyForm.HYDRO, Date(1649023200000)).timestamp)
        assertEquals(BigDecimal(2), apiClient.getLatestEnergyMeasurement(EnergyForm.HYDRO, Date(1649023200000)).value)
    }
}