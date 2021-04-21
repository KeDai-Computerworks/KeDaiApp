package co.id.kedai.kedaiapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.id.kedai.kedaiapp.databinding.ActivitySteRegistration2Binding
import java.util.regex.Matcher
import java.util.regex.Pattern

class SteRegistrationActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivitySteRegistration2Binding
    private val emailPattern: String =
        "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySteRegistration2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.title = "Pendaftaran STE"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val namaLengkap = intent.getStringExtra("namaLengkap")
        val tempatLahir = intent.getStringExtra("tempatLahir")
        val tanggalLahir = intent.getStringExtra("tanggalLahir")
        val alamat = intent.getStringExtra("alamat")
        val jenisKelamin = intent.getStringExtra("jenisKelamin")
        val golonganDarah = intent.getStringExtra("golonganDarah")

        binding.btnlanjut.setOnClickListener {

            if (binding.inputEmail.text.toString().isEmpty()
                || binding.inputTelepon.text.toString().isEmpty()
            ) Toast.makeText(this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()

            if (binding.inputTelepon.text.toString().length >= 11) binding.telepon.error = null

            when {
                binding.inputTelepon.text.toString() == "" -> binding.telepon.error = " "
                binding.inputTelepon.text.toString().length < 11 -> binding.telepon.error =
                    "Minimal 11 angka"
                binding.inputEmail.text.toString() == "" -> binding.email.error = " "
                !validateEmail(binding.inputEmail.text.toString()) -> binding.email.error =
                    "Email tidak valid"
                else -> {
                    val intent = Intent(this, SteRegistrationActivity3::class.java)
                    intent.putExtra("namaLengkap", namaLengkap)
                    intent.putExtra("tempatLahir", tempatLahir)
                    intent.putExtra("tanggalLahir", tanggalLahir)
                    intent.putExtra("alamat", alamat)
                    intent.putExtra("jenisKelamin", jenisKelamin)
                    intent.putExtra("golonganDarah", golonganDarah)
                    intent.putExtra("noTelepon", binding.inputTelepon.text.toString())
                    intent.putExtra("email", binding.inputEmail.text.toString())
                    startActivity(intent)
                    finish()

                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        val pattern: Pattern = Pattern.compile(emailPattern)
        val matcher: Matcher = pattern.matcher(email)

        return matcher.matches()
    }

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return true
    }

}