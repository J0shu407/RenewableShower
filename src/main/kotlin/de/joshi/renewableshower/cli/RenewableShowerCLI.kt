package de.joshi.renewableshower.cli

import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.joshi.renewableshower.api.ApiClient
import de.joshi.renewableshower.api.EnergyForm
import de.joshi.renewableshower.data.DataProcessing
import de.joshi.renewableshower.model.CompleteEnergyData
import de.joshi.renewableshower.persistence.DatabaseClient
import de.joshi.renewableshower.persistence.DatabaseModel
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.Date


@Component
class RenewableShowerCLI(
    val dataProcessing: DataProcessing,
    val databaseClient: DatabaseClient,
    val apiClient: ApiClient
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        databaseClient.addInformation(
            dataProcessing.getCompleteEnergyData(Date(1662937200000))
        )
    }
}