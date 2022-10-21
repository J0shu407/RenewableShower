package de.joshi.renewableshower.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.database.FirebaseDatabase
import de.joshi.renewableshower.data.DataProcessing
import de.joshi.renewableshower.model.CompleteEnergyData
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class FirebaseClient(val firebaseDatabase: FirebaseDatabase, val mapper: ObjectMapper): DatabaseClient {
    val LOGGER: Logger = LoggerFactory.getLogger(DataProcessing::class.java)

    override fun setLatestTotalProduction(totalProduction: BigDecimal) {
        val value = firebaseDatabase.reference.child("power_information/latest/total_production")
        value.setValue(totalProduction.toInt())
        {err, ref ->
            if(err != null) throw err.toException()
        }
        LOGGER.info("Pushed $totalProduction as the latest total production")
    }

    override fun setLatestPortionOfRenewables(portion: BigDecimal) {
        val value = firebaseDatabase.reference.child("power_information/latest/portion_of_renewables")
        value.setValue(portion.toDouble())
        {err, ref ->
            if(err != null) throw err.toException()
        }
        LOGGER.info("Pushed $portion as the latest portion of renewables")
    }

    override fun setLatestPortionOfConventionales(portion: BigDecimal) {
        val value = firebaseDatabase.reference.child("power_information/latest/portion_of_conventionales")
        value.setValue(portion.toDouble())
        {err, ref ->
            if(err != null) throw err.toException()
        }
        LOGGER.info("Pushed $portion as the latest portion of conventionales")
    }

    override fun setLatestTimestamp(date: Date) {
        val value = firebaseDatabase.reference.child("power_information/latest/timestamp")
        value.setValue(date.time)
        {err, ref ->
            if(err != null) throw err.toException()
        }
        LOGGER.info("Pushed ${date.time} as the latest timestamp")
    }

    override fun addInformation(completeEnergyData: CompleteEnergyData) {
        val value = firebaseDatabase.reference.child("power_information/all_time/${completeEnergyData.time}")
        value.setValue(mapper.writeValueAsString(completeEnergyData))
        {err, ref ->
            if(err != null) throw err.toException()
        }
        LOGGER.info("Pushed ${mapper.writeValueAsString(completeEnergyData)} to the database")
    }
}