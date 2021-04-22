package co.id.kedai.kedaiapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import co.id.kedai.kedaiapp.R
import co.id.kedai.kedaiapp.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        binding.splashScreenConstraint.animation = AnimationUtils.loadAnimation(this, R.anim.load)

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(2000)
            startActivity(Intent(applicationContext, OnBoardingActivity::class.java))
            overridePendingTransition(R.anim.splashscreen_fade_in, R.anim.splashscreen_fade_out)
            finish()
        }
    }
}