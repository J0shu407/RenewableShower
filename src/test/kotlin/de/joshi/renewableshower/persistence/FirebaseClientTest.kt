package de.joshi.renewableshower.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.DatabaseConfig
import de.joshi.renewableshower.model.CompleteEnergyData
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.openMocks
import java.math.BigDecimal
import java.util.Date

class FirebaseClientTest {
    private lateinit var firebaseClient: FirebaseClient

    @Mock
    private lateinit var firebaseDatabase: FirebaseDatabase

    @Mock
    private lateinit var mapper: ObjectMapper

    @Mock
    private lateinit var ref: DatabaseReference

    @Mock
    private lateinit var ref2: DatabaseReference

    @BeforeEach
    fun setUp() {
        openMocks(this)
        firebaseClient = FirebaseClient(firebaseDatabase, mapper)
    }

    @Test
    fun setLatestTotalProductionSuccessfully() {
        `when`(firebaseDatabase.reference).thenReturn(ref)
        `when`(firebaseDatabase.reference.child("power_information/latest/total_production")).thenReturn(ref2)
        firebaseClient.setLatestTotalProduction(BigDecimal(4))
        verify(ref2).setValue(eq(4), any())
    }

    @Test
    fun setLatestPortionOfRenewablesSuccessfully() {
        `when`(firebaseDatabase.reference).thenReturn(ref)
        `when`(firebaseDatabase.reference.child("power_information/latest/portion_of_renewables")).thenReturn(ref2)
        firebaseClient.setLatestPortionOfRenewables(BigDecimal(0.4))
        verify(ref2).setValue(eq(0.4), any())
    }

    @Test
    fun setLatestPortionOfConventionalesSuccessfully() {
        `when`(firebaseDatabase.reference).thenReturn(ref)
        `when`(firebaseDatabase.reference.child("power_information/latest/portion_of_conventionales")).thenReturn(ref2)
        firebaseClient.setLatestPortionOfConventionales(BigDecimal(0.3))
        verify(ref2).setValue(eq(0.3), any())
    }

    @Test
    fun setLatestTimestampSuccessfully() {
        `when`(firebaseDatabase.reference).thenReturn(ref)
        `when`(firebaseDatabase.reference.child("power_information/latest/timestamp")).thenReturn(ref2)
        firebaseClient.setLatestTimestamp(Date(15))
        verify(ref2).setValue(eq(15L), any())
    }

    @Test
    fun addInformationSuccessfully() {
        val completeEnergyData: CompleteEnergyData = CompleteEnergyData(
            0L,
            BigDecimal(1),
            BigDecimal(2),
            BigDecimal(3),
            mutableMapOf("test" to BigDecimal(1))
        )

        `when`(firebaseDatabase.reference).thenReturn(ref)
        `when`(firebaseDatabase.reference.child("power_information/all_time/${completeEnergyData.time}")).thenReturn(ref2)
        `when`(mapper.writeValueAsString(completeEnergyData)).thenReturn("{'json':'toll'}")
        firebaseClient.addInformation(completeEnergyData)
        verify(ref2).setValue(eq("{'json':'toll'}"), any())
    }
}