
package com.satellite.broadband

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

 lateinit var auth: FirebaseAuth

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContentView(R.layout.activity_login)

  auth = FirebaseAuth.getInstance()

  val phone = findViewById<EditText>(R.id.phone)
  val btn = findViewById<Button>(R.id.sendOtp)

  btn.setOnClickListener {
   val number = phone.text.toString()

   val options = PhoneAuthOptions.newBuilder(auth)
    .setPhoneNumber("+91$number")
    .setTimeout(60L, TimeUnit.SECONDS)
    .setActivity(this)
    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

     override fun onVerificationCompleted(credential: PhoneAuthCredential) {
      auth.signInWithCredential(credential)
     }

     override fun onVerificationFailed(e: FirebaseException) {
      Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
     }

     override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
      Toast.makeText(this@LoginActivity, "OTP Sent", Toast.LENGTH_SHORT).show()
     }
    }).build()

   PhoneAuthProvider.verifyPhoneNumber(options)
  }
 }
}
