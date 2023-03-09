package id.heycoding.sholehapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// TODO 6 Buat class extends Application yang nantinya bisa dipakai untuk inisialisasi DI dll, jangan lupa daftarin di manifest

@HiltAndroidApp
class MyApp : Application()