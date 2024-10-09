import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techad.calltranslatorapp.R

class OTPVerificationActivity : AppCompatActivity() {

    lateinit var etOTP: EditText
    lateinit var btnVerify: Button
    lateinit var btnResend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)

        etOTP = findViewById(R.id.etOTP)
        btnVerify = findViewById(R.id.btnVerify)
        btnResend = findViewById(R.id.btnResend)

        // Handle Verify OTP button click
        btnVerify.setOnClickListener {
            val otp = etOTP.text.toString().trim()

            if (otp.isEmpty()) {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show()
            } else {
                verifyOTP(otp)
            }
        }

        // Handle Resend OTP button click
        btnResend.setOnClickListener {
            resendOTP()
        }
    }

    private fun verifyOTP(otp: String) {
        // Replace this with actual verification logic (e.g., backend API call)
        if (otp == "123456") {
            Toast.makeText(this, "OTP Verified", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resendOTP() {
        // Logic to resend OTP (e.g., API call to trigger SMS again)
        Toast.makeText(this, "OTP Resent", Toast.LENGTH_SHORT).show()
    }
}
