package com.fatihduyar.timetimer

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

lateinit var sharedPreferences : SharedPreferences
var sonKayitlar : String? = ""
var runnable1 :Runnable = Runnable{}
var handler : Handler = Handler()

var saat= 0
var dk = 0
var sn = 0
var milisaniye = 0
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = this.getSharedPreferences("com.fatihduyar.timetimer", MODE_PRIVATE)
        sonKayitlar = sharedPreferences.getString("sonKayitlar", "Kayıt Bulunamadı.")
        sonisler.text = sonKayitlar
        harfKontrol()
    }

    fun baslat(view:View){
        runnable1 = object : Runnable {
            override fun run() {
                milisaniye += 30
                zamantxt.text = "${saat}:${dk}:${sn}:${milisaniye}"
                handler.postDelayed(runnable1, 30)
                if(milisaniye > 999){
                    milisaniye -= 1000
                    sn += 1
                }
                if(sn > 59){
                    sn -= 60
                    dk +=1
                }
                if(dk > 59){
                    dk -= 60
                    saat += 1
                }
            }

        }
        handler.post(runnable1)

        baslat.isEnabled = false
        durdurbtn.isEnabled = true
        kaydetbtn.isEnabled = true
        sifirlabtn.isEnabled = true
    }

    fun durdur(view:View){
        handler.removeCallbacks(runnable1)

        baslat.isEnabled = true
    }
    fun kaydet(view:View){
        sonKayitlar = sharedPreferences.getString("sonKayitlar", "Kayıt Bulunamadı.")
        sonKayitlar = "${txtGelen.text.toString()} - ${saat}:${dk}:${sn}:${milisaniye}\n" + sonKayitlar
        sharedPreferences.edit().putString("sonKayitlar", sonKayitlar).apply()
        sonisler.text = sonKayitlar

    }
    fun sifirla(view: View){
         saat= 0
         dk = 0
         sn = 0
         milisaniye = 0
        zamantxt.text = "${saat}:${dk}:${sn}:${milisaniye}"
    }

    fun harfKontrol (){

        if(sonKayitlar!!.length > 500){
            sonKayitlar = sonKayitlar!!.substring(0,500)
            sharedPreferences.edit().putString("sonKayitlar", sonKayitlar).apply()
            sonisler.text = sonKayitlar
        }
    }
}