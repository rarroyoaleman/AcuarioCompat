package com.example.acuario.providers.googleplay

import android.app.Activity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class GooglePlayProvider {

    companion object{
        fun isGooglePlayServicesAvailable(activity: Activity?): Boolean {
            val googleApiAvailability = GoogleApiAvailability.getInstance()
            val status = googleApiAvailability.isGooglePlayServicesAvailable(activity!!)
            if (status != ConnectionResult.SUCCESS) {
                if (googleApiAvailability.isUserResolvableError(status)) {
                    googleApiAvailability.getErrorDialog(activity, status, 2404)!!.show()
                }
                return false
            }
            return true
        }
    }
}