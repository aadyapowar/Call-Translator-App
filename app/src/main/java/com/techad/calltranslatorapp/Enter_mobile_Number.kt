import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.techad.calltranslatorapp.R
import java.util.concurrent.TimeUnit

class EnterMobileNumberActivity : AppCompatActivity() {

    private lateinit var phoneEditText: EditText
    private lateinit var sendOtpButton: Button
    private lateinit var auth: FirebaseAuth
    private var verificationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_mobile_number)

        phoneEditText = findViewById(R.id.editTextPhone)
        sendOtpButton = findViewById(R.id.sendotpbutton)

        // Initialize FirebaseAuth instance
        auth = FirebaseAuth.getInstance()

        sendOtpButton.setOnClickListener {
            val phoneNumber = phoneEditText.text.toString().trim()

            if (phoneNumber.isNotEmpty()) {
                sendVerificationCode(phoneNumber)
            } else {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to send OTP
    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout duration
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Auto-verification or instant verification
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // Handle error, could be due to invalid phone number, etc.
                    Toast.makeText(this@EnterMobileNumberActivity, "Verification Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    // The verification ID and token are saved for future use
                    this@EnterMobileNumberActivity.verificationId = verificationId
                            // Display a Toast message or navigate to the OTP input screen
                            Toast.makeText(this@EnterMobileNumberActivity, "OTP Sent", Toast.LENGTH_SHORT).show()

                    // You can navigate to an OTP verification screen here
                    // For example: startActivity(Intent(this@EnterMobileNumberActivity, OTPVerificationActivity::class.java))
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // Function to sign in with the PhoneAuthCredential
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in success, navigate to the next activity
                    Toast.makeText(this, "Authentication successful", Toast.LENGTH_SHORT).show()
                    // Navigate to a new activity (e.g., a home screen)
                } else {
                    // Sign-in failed
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
