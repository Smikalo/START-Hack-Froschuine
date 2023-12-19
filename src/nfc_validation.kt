import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

package com.example.nfcvalidation

class NfcActivity : AppCompatActivity() {

    private var nfcAdapter: NfcAdapter? = null
    private var dataToSend: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        // insert here your android-android validation data
        dataToSend = "test_android_mutual_validation"

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            // NFC is not supported
            finish()
            return
        }
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    override fun onPause() {
        super.onPause()
        disableNfcForegroundDispatch()
    }

    private fun enableNfcForegroundDispatch() {
        val intent = Intent(this, javaClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED))
        val techLists = arrayOf(arrayOf(Ndef::class.java.name))

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, filters, techLists)
    }

    private fun disableNfcForegroundDispatch() {
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNfcIntent(intent)
    }

    private fun handleNfcIntent(intent: Intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)

            if (rawMessages != null) {
                val messages = arrayOfNulls<NdefMessage>(rawMessages.size)

                for (i in rawMessages.indices) {
                    messages[i] = rawMessages[i] as NdefMessage
                }

                processNdefMessages(messages)
            }
        }
    }

    private fun processNdefMessages(messages: Array<NdefMessage?>) {
        for (message in messages) {
            if (message != null) {
                val records = message.records
                for (record in records) {
                    val payload = String(record.payload)
                    // use payload String as your data from nfc 
                }
            }
        }
    }
}

// run the .NfcActivity activity from manifest when necessary with
// val intent = Intent(this, NfcActivity::class.java) 
// startActivity(intent)
// and use tools:context=".NfcActivity"> in your layout
