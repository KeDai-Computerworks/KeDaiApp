package co.id.kedai.kedaiapp.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import co.id.kedai.kedaiapp.R
import co.id.kedai.kedaiapp.adapter.AdapterCaraMendaftar
import co.id.kedai.kedaiapp.databinding.ActivityCaraMendaftarBinding
import co.id.kedai.kedaiapp.model.DataCaraMendaftar

class CaraMendaftarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCaraMendaftarBinding

    private val adapterCaraMendaftar = AdapterCaraMendaftar(
        listOf(
            DataCaraMendaftar("Pilih Daftar", R.drawable.cara1_6),
            DataCaraMendaftar("Isi data diri, lalu lanjut", R.drawable.cara2),
            DataCaraMendaftar("Isi no. telepon dan email, lalu lanjut", R.drawable.cara3),
            DataCaraMendaftar(
                "Isi asal kampus dan alasan mendaftar, lalu daftar",
                R.drawable.cara4
            ),
            DataCaraMendaftar(
                "Mendapatkan no. registrasi, lalu lanjutkan pembayaran, setelah melakukan membayar konfirmasi ke panitia STE XVII",
                R.drawable.cara5
            ),
            DataCaraMendaftar("lalu cek pembayaran", R.drawable.cara1_6),
            DataCaraMendaftar(
                "Setelah melakukan pembayaran, masukkan no. registrasi yang telah didapatkan, lalu download no registrasi",
                R.drawable.cara7
            ),
            DataCaraMendaftar("No. registrasi telah didownload", R.drawable.cara8),
            DataCaraMendaftar("Tampilan no. registrasi", R.drawable.cara9)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaraMendaftarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = "Cara Mendaftar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.sliderView.adapter = adapterCaraMendaftar

        setupIndicator()
        setCurrentIndicator(0)

        binding.sliderView.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
    }

    private fun setupIndicator() {
        val indicator = arrayOfNulls<ImageView>(adapterCaraMendaftar.itemCount)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
