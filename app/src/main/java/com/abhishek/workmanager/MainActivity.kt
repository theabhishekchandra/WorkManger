package com.abhishek.workmanager

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Views
        val scheduleTextView = findViewById<TextView>(R.id.scheduleTextView)
        val scheduleButton = findViewById<Button>(R.id.scheduleButton)

        // Set click listener on button to schedule task
        scheduleButton.setOnClickListener {
            createScheduleTask(scheduleTextView)
        }
    }

    /**
     * Creates and schedules a task using WorkManager.
     * @param textView TextView to display task status.
     */
    private fun createScheduleTask(textView: TextView) {
        // Define constraints: Requires network and non-low battery.
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        // Input data for the WorkManager task
        val inputData = workDataOf(
            "wifeName" to "Pooja",
            "birthdayDate" to "12/12/2023",
            "giftName" to "Chocolate, Perfume",
            "message" to "Happy Birthday Pooja!"
        )

        // Create a one-time work request with a delay of 15 minutes
        val reminderRequest = OneTimeWorkRequestBuilder<BirthdayReminderWorker>()
            .setInitialDelay(15, TimeUnit.MINUTES)
            .addTag("birthday_reminder")
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()
        // Create a periodic work request with a repeat interval of 15 minutes
        val reminderNewRequest = PeriodicWorkRequestBuilder<BirthdayReminderWorker>(15, TimeUnit.MINUTES)
            .addTag("birthday_reminder")
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()

        // Enqueue the work request
        WorkManager.getInstance(applicationContext).enqueue(reminderRequest)

        // Observe the work status and update the UI
        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(reminderRequest.id)
            .observe(this) { workInfo ->
                when (workInfo.state) {
                    WorkInfo.State.ENQUEUED -> textView.text = "Task is scheduled successfully!"
                    WorkInfo.State.RUNNING -> textView.text = "Task is running..."
                    WorkInfo.State.SUCCEEDED -> textView.text = "Task completed successfully!"
                    WorkInfo.State.FAILED -> textView.text = "Task failed. Try again!"
                    WorkInfo.State.BLOCKED -> textView.text = "Task is blocked!"
                    WorkInfo.State.CANCELLED -> textView.text = "Task was cancelled!"
                }
            }
    }
}
