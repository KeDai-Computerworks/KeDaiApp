package co.id.kedai.kedaiapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.id.kedai.kedaiapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(2000)
            startActivity(Intent(applicationContext, OnBoardingActivity::class.java))
            finish()
        }

    }
}