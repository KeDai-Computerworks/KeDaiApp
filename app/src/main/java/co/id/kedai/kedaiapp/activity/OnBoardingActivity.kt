package co.id.kedai.kedaiapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import co.id.kedai.kedaiapp.MainActivity
import co.id.kedai.kedaiapp.R
import co.id.kedai.kedaiapp.adapter.AdapterOnBoarding
import co.id.kedai.kedaiapp.databinding.ActivityOnBoardingBinding
import co.id.kedai.kedaiapp.model.DataOnBoarding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    private val adapterOnBoarding = AdapterOnBoarding(
        listOf(
            DataOnBoarding(
                "Blog",
                "Selalu update dengan berbagai macam berita " +
                        "dan materi terbaru seputar dunia teknologi",
                R.drawable.onboarding_berita
            ),
            DataOnBoarding(
                "E-Book",
                "Dapatkan berbagai macam buku elektronik yang " +
                        "akan menambah wawasanmu seputar dunia teknologi",
                R.drawable.onboarding_ebook
            ),
            DataOnBoarding(
                "Acara",
                "Temukan berbagai macam acara atau kegiatan " +
                        "menarik dari KeDai Computerworks",
                R.drawable.onboarding_acara
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        val intent = Intent(applicationContext, MainActivity::class.java)

        if (restoreData()) {
            startActivity(intent)
            finish()
        }

        binding.sliderView.adapter = adapterOnBoarding

        setupIndicator()
        setCurrentIndicator(0)

        binding.sliderView.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.sliderView.currentItem + 1 <
                adapterOnBoarding.itemCount
            ) {
                binding.sliderView.currentItem += 1
            } else {
                startActivity(intent)
                dataSave()
                finish()
            }
        }

        binding.tvSkip.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Skip")
            builder.setMessage("Lewati pengenalan ?")

            builder.setPositiveButton("Ya") { _, _ ->
                startActivity(intent)
                dataSave()
                finish()
            }

            builder.setNegativeButton("Batal") { _, _ ->

            }
            builder.show()

        }
    }

    private fun setupIndicator() {
        val indicator = arrayOfNulls<ImageView>(adapterOnBoarding.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(10, 0, 10, 0)

        for (i in indicator.indices) {
            indicator[i] = ImageView(applicationContext)
            indicator[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_off
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.indicatorContainer.addView(indicator[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_on
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_off
                    )
                )
            }
        }
    }

    private fun dataSave() {
        val sharedPreferences = applicationContext.getSharedPreferences(
            "onboarding", Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("state_onboarding", true)
        editor.apply()
    }

    private fun restoreData(): Boolean {
        val sharedPreferences = applicationContext.getSharedPreferences(
            "onboarding", Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean("state_onboarding", false)
    }
}