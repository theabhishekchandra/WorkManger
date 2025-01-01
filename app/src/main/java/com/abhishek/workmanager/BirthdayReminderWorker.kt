package com.abhishek.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class BirthdayReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    /**
     * Executes the task when WorkManager triggers it.
     */
    override fun doWork(): Result {
        return try {
            // Retrieve input data
            val wifeName = inputData.getString("wifeName")
            val birthdayDate = inputData.getString("birthdayDate")
            val giftName = inputData.getString("giftName")
            val message = inputData.getString("message")

            if (wifeName.isNullOrEmpty() || birthdayDate.isNullOrEmpty() || giftName.isNullOrEmpty() || message.isNullOrEmpty()) {
                Log.e("BirthdayReminder", "Invalid input data. Task cannot proceed.")
                return Result.failure() // Input data is incomplete
            }

            // Simulate sending notifications (or other tasks)
            Log.d("BirthdayReminder", "Reminder for $wifeName's birthday on $birthdayDate.")
            Log.d("BirthdayReminder", "Gifts: $giftName. Message: $message.")
            sendBirthdayNotification(wifeName, message)
            sendGiftNotification(giftName)

            Result.success() // Indicate success
        } catch (e: Exception) {
            Log.e("BirthdayReminder", "Error while processing the task: ${e.message}")
            Result.failure() // Indicate task failure
        }
    }

    /**
     * Simulates sending a birthday notification.
     */
    private fun sendBirthdayNotification(wifeName: String, message: String) {
        try {
            // Placeholder for notification logic
            Log.d("BirthdayReminder", "Notification sent: $message for $wifeName.")
        } catch (e: Exception) {
            Log.e("BirthdayReminder", "Failed to send birthday notification: ${e.message}")
        }
    }

    /**
     * Simulates sending a gift reminder notification.
     */
    private fun sendGiftNotification(giftName: String) {
        try {
            // Placeholder for notification logic
            Log.d("BirthdayReminder", "Reminder sent: Don't forget to buy $giftName.")
        } catch (e: Exception) {
            Log.e("BirthdayReminder", "Failed to send gift notification: ${e.message}")
        }
    }

    /**
     * Handles cancellation of the task.
     */
    override fun onStopped() {
        super.onStopped()
        Log.w("BirthdayReminder", "Task was cancelled by the system or user.")
        // Optional: Handle cancellation-specific logic here, such as cleaning up resources
    }
}
