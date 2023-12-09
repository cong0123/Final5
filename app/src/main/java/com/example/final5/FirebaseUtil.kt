import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseUtil {

    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("sensorData")

    fun readDataFromFirebase(listener: ValueEventListener) {
        try {
            databaseReference.addValueEventListener(listener)
        } catch (e: Exception) {
            Log.e("FirebaseUtil", "Error reading data from Firebase", e)
        }
    }
}


