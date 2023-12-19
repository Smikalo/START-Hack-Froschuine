import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button

package com.example.smsexample

class MainActivity : Activity() {

    // change phoneNumber variable to the data read from nfc
    private val phoneNumber = "phone_number"
    // message has to contain unique transaction ID
    private val message = "test_message"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

	// there has to be a button with id "sendSmsButton" in your res/layout/activity_main.xml
        // user validation is required for sending an sms
        val sendSmsButton: Button = findViewById(R.id.sendSmsButton)
        sendSmsButton.setOnClickListener {
            sendSms()
        }
    }

    private fun sendSms() {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            // successfully delievered
        } catch (e: Exception) {
            // exception happened and must be messaged to the user
            e.printStackTrace()
        }
    }
}
