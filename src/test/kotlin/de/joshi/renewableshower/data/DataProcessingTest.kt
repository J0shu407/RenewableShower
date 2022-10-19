package de.joshi.renewableshower.data

import de.joshi.renewableshower.api.ApiClient
import de.joshi.renewableshower.api.DataResolution
import de.joshi.renewableshower.api.EnergyForm
import de.joshi.renewableshower.model.EnergyMeasurement
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

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}