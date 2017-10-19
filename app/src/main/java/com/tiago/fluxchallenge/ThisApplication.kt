package com.tiago.fluxchallenge

import android.app.Application
import android.os.StrictMode
import com.squareup.leakcanary.LeakCanary

/**
 * Created by tiago on 19/10/17.
 */
class ThisApplication : Application(){

	override fun onCreate() {

		if (BuildConfig.DEBUG) {

			StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
					.detectDiskReads()
					.detectDiskWrites()
					.detectAll()
					.penaltyLog()
					.build())

			StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects()
					.detectLeakedClosableObjects()
					.penaltyLog()
					.penaltyDeath()
					.build())

			if (LeakCanary.isInAnalyzerProcess(this)) {
				return
			}
			LeakCanary.install(this)
		}
		super.onCreate()
	}
}