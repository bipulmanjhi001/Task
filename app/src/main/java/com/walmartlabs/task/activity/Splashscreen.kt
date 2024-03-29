package com.walmartlabs.task.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.walmartlabs.task.R
import com.walmartlabs.task.model.ConnectivityReceiver
import com.walmartlabs.task.model.WalmartApplication

/**
 * Created by Bipul on 25-08-2019.
 */
class Splashscreen : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkConnection()
    }

    private fun checkConnection() {
        val isConnected = ConnectivityReceiver.isConnected
        showSnack(isConnected)
    }

    private fun showSnack(isConnected: Boolean) {
        val message: String
        val color: Int
        if (isConnected) {
            val timer = object : Thread() {
                override fun run() {
                    try {
                        Thread.sleep(5000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()

                    } finally {
                        val openMain = Intent(this@Splashscreen, Screen1::class.java)
                        startActivity(openMain)
                        finish()
                    }
                }
            }
            timer.start()


        } else {
            message = "connect your internet."
            color = Color.RED
            val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onResume() {
        super.onResume()
        WalmartApplication.instance?.setConnectivityListener(this)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showSnack(isConnected)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onBackPressed() {
        backButtonHandler()
        return
    }

    fun backButtonHandler() {
        val alertDialog = AlertDialog.Builder(this@Splashscreen)
        alertDialog.setTitle("Leave application?")
        alertDialog.setMessage("Are you sure you want to leave the application?")
        alertDialog.setIcon(R.mipmap.ic_launcher_round)
        alertDialog.setPositiveButton("YES"
        ) { dialog, which -> this@Splashscreen.finish() }
        alertDialog.setNegativeButton("NO") { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }
}
