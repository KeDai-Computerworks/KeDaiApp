package co.id.kedai.kedaiapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.id.kedai.kedaiapp.databinding.ActivitySteMenuBinding

class SteMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySteMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySteMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.title = "Search To Extract XVII"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.menuRegistrasi.setOnClickListener {
            startActivity(Intent(this, SteRegistrationActivity::class.java))
        }
        binding.menuCekPembayaran.setOnClickListener {
            startActivity(Intent(this, CheckPaidActivity::class.java))
        }

//        binding.menuJadwal.setOnClickListener {
//            startActivity(Intent(this, SteJadwalActivity::class.java))
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}