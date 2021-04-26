package co.id.kedai.kedaiapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.id.kedai.kedaiapp.databinding.ActivitySteJadwalBinding

class SteJadwalActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySteJadwalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySteJadwalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.title = "Jadwal STE XVII"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnAlurPendaftaran.setOnClickListener {
            startActivity(Intent(this, CaraMendaftarActivity::class.java))
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}