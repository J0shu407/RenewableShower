package de.joshi.renewableshower.persistence

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.springframework.stereotype.Component

@Component
class DatabaseModel(val powerInformation: PowerInformation, val firebaseDatabase: FirebaseDatabase) {


    @Component
    class PowerInformation(val firebaseDatabase: FirebaseDatabase, val latest: Latest) {

        @Component
        class Latest(val firebaseDatabase: FirebaseDatabase, val totalProduction: TotalProduction) {
            @Component
            class TotalProduction(val firebaseDatabase: FirebaseDatabase) {

                var reference: DatabaseReference = firebaseDatabase.reference.child("power_information/latest/total_production")
            }
            @Component
            class PortionOfRenewables(val firebaseDatabase: FirebaseDatabase) {

                var reference: DatabaseReference = firebaseDatabase.reference.child("power_information/latest/total_production")
            }

            var reference: DatabaseReference = firebaseDatabase.reference.child("power_information/latest")
        }

        var reference: DatabaseReference = firebaseDatabase.reference.child("power_information")
    }
}