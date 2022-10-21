package de.joshi.renewableshower.data

import de.joshi.renewableshower.api.ApiClient
import de.joshi.renewableshower.api.DataResolution
import de.joshi.renewableshower.api.EnergyForm
import de.joshi.renewableshower.model.EnergyDataSlice
import de.joshi.renewableshower.model.EnergyMeasurement
import de.joshi.renewableshower.model.EnergyMetaData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import java.math.BigDecimal
import java.math.MathContext
import java.util.*

class DataProcessingTest {

    private lateinit var dataProcessing: DataProcessing

    @Mock
    private lateinit var apiClient: ApiClient

    private val energyData: EnergyDataSlice = EnergyDataSlice(
        EnergyMetaData(1, 34L), listOf(
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
        dataProcessing = DataProcessing(apiClient)

        `when`(
            apiClient.getLatestAvailableTimestamp(
                any(EnergyForm::class.java)
            )
        ).thenReturn(Date(0))
        `when`(
            apiClient.getLatestEnergyMeasurement(
                any(EnergyForm::class.java),
                any(Date::class.java)
            )
        ).thenReturn(EnergyMeasurement().apply { timestamp = Date(0); value = BigDecimal(4.0) })
        `when`(
            apiClient.getNearestPossibleTimestamp(
                Date(10)
            )
        ).thenReturn(Date(100))
        `when`(
            apiClient.getEnergyProductionData(
                any(EnergyForm::class.java),
                any(Date::class.java)
            )
        ).thenReturn(energyData)
    }

    @Test
    fun getSinglePortionOfWholeProductionSuccessfully() {

        assertEquals(
            BigDecimal(4.0) / BigDecimal(48),
            dataProcessing.getPortionOfWholeProduction(EnergyForm.HYDRO)
        )
    }

    @Test
    fun getMultiplePortionOfWholeProductionSuccessfully() {
        assertEquals(
            (BigDecimal(4.0) / BigDecimal(48)) * BigDecimal(EnergyForm.RENEWABLES.size),
            dataProcessing.getPortionOfWholeProduction(EnergyForm.RENEWABLES)
        )
    }

    @Test
    fun getTotalProductionSuccessfully() {

        assertEquals(BigDecimal(4) * BigDecimal(EnergyForm.values().size), dataProcessing.getTotalProduction())
    }

    @Test
    fun getCompleteEnergyDataSuccessfully() {
        assertEquals(Date(100), dataProcessing.getCompleteEnergyData(Date(10)).time)
        assertEquals(EnergyForm.values().size, dataProcessing.getCompleteEnergyData(Date(10)).energyMeasurements.size)
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
    private fun <T> eq(type: T): T = Mockito.eq(type)
}