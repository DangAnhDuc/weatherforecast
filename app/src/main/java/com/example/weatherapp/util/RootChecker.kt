package com.example.weatherapp.util

import android.os.Build
import java.io.File

class RootChecker {
    companion object {
        fun isRooted(): Boolean {
            val buildTags = Build.TAGS
            if (buildTags != null && buildTags.contains("test-keys")) {
                return true
            }
            // check if /system/app/Superuser.apk is present
            try {
                val file = File("/system/app/Superuser.apk")
                if (file.exists()) {
                    return true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (!canExecuteCommand("su")) if (findBinary("su")) return true
            return false
        }


        private fun findBinary(binaryName: String): Boolean {
            var found = false
            if (!found) {
                val places = arrayOf(
                    "/sbin/", "/system/bin/", "/system/xbin/",
                    "/data/local/xbin/", "/data/local/bin/",
                    "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"
                )
                for (where in places) {
                    if (File(where + binaryName).exists()) {
                        found = true
                        break
                    }
                }
            }
            return found
        }

        private fun canExecuteCommand(command: String): Boolean {
            val executedSuccessfully: Boolean = try {
                Runtime.getRuntime().exec(command)
                true
            } catch (e: Exception) {
                false
            }
            return executedSuccessfully
        }
    }
}