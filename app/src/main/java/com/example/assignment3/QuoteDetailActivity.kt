package com.example.assignment3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import android.widget.Button
import androidx.appcompat.widget.Toolbar


class QuoteDetailActivity : AppCompatActivity() {

    private val CHANNEL_ID = "quote_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_detail)

        // Initialize the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Receive the quote from intent
        val quote = intent.getStringExtra("QUOTE_TEXT")

        // Display the quote in a TextView
        val textView: TextView = findViewById(R.id.quoteTextView)
        textView.text = quote

        // Create a notification with the quote
        createNotificationChannel()
        showNotification(quote ?: "No Quote")

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish() // End this activity and return to the previous one
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Quote Notifications"
            val descriptionText = "Channel for Quote Notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun showNotification(quote: String) {
        // Check if the POST_NOTIFICATIONS permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, show the notification
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Your Quote")
                .setContentText(quote)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(1, builder.build())
            }
        } else {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_CODE_POST_NOTIFICATIONS
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_POST_NOTIFICATIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, you can post the notification
                    // You might want to call showNotification again if necessary
                } else {
                    // Permission denied, handle it accordingly
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_POST_NOTIFICATIONS = 1
    }



}
