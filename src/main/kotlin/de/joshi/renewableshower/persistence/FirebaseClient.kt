package de.joshi.renewableshower.persistence

import com.google.firebase.database.FirebaseDatabase
import de.joshi.renewableshower.model.CompleteEnergyData
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class FirebaseClient(val firebaseDatabase: FirebaseDatabase): DatabaseClient {

    override fun setLatestTotalProduction(totalProduction: BigDecimal) {
        val value = firebaseDatabase.reference.child("power_information/latest/total_production")
        value.setValue(totalProduction.toInt())
        {err, ref ->
            if(err != null) throw err.toException()
        }
    }

    override fun setLatestPortionOfRenewables(portion: BigDecimal) {
        val value = firebaseDatabase.reference.child("power_information/latest/portion_of_renewables")
        value.setValue(portion.toDouble())
        {err, ref ->
            if(err != null) throw err.toException()
        }
    }

    override fun setLatestPortionOfConventionales(portion: BigDecimal) {
        val value = firebaseDatabase.reference.child("power_information/latest/portion_of_conventionales")
        value.setValue(portion.toDouble())
        {err, ref ->
            if(err != null) throw err.toException()
        }
    }

    override fun setLatestTimestamp(date: Date) {
        val value = firebaseDatabase.reference.child("power_information/latest/timestamp")
        value.setValue(date.time)
        {err, ref ->
            if(err != null) throw err.toException()
        }
    }

    override fun setLatestEnergyData(completeEnergyData: CompleteEnergyData) {
        setLatestTotalProduction(completeEnergyData.totalProduction)
        setLatestPortionOfRenewables(completeEnergyData.portionOfRenewables)
        setLatestPortionOfConventionales(completeEnergyData.portionOfConventionales)
        setLatestTimestamp(completeEnergyData.time)
    }

    override fun addInformation(completeEnergyData: CompleteEnergyData) {
        val value = firebaseDatabase.reference.child("power_information/latest/timestamp")
        //value.setValue(date.time)
        //{err, ref ->
         //   if(err != null) throw err.toException()
        //}
    }
}