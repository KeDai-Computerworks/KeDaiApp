package co.id.kedai.kedaiapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.id.kedai.kedaiapp.databinding.ActivityPdfReaderBinding
import co.id.kedai.kedaiapp.databinding.ActivityRegistrationSuccessBinding

class RegistrationSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationSuccessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val idRegistrasi = intent.getStringExtra("idR")
        binding.idR.text = idRegistrasi.toString()

    }
}