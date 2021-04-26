package co.id.kedai.kedaiapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.id.kedai.kedaiapp.R
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.databinding.ActivitySteRegistration3Binding
import co.id.kedai.kedaiapp.model.DaftarResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SteRegistrationActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivitySteRegistration3Binding
    private val itemKampus = listOf(
        "Universitas Dipa Makassar",
        "Universitas Hasanuddin",
        "Universitas Islam Negeri Alauddin Makassar",
        "Universitas Negeri Makassar",
        "Universitas Bosowa",
        "Universitas Atma Jaya",
        "Universitas Kristen Indonesia Paulus",
        "Universitas Fajar",
        "Universitas Muhammadiyah Makassar",
        "Universitas Teknologi Sulawesi",
        "Universitas Islam Makassar",
        "Universitas Megarezky",
        "Politeknik Negeri Ujung Pandang",
        "Politeknik Negeri Media Kreatif",
        "Politeknik Informatika Makassar",
        "Politeknik ATI Makassar",
        "STMIK Akba Makassar",
        "STMIK Handayani Makassar",
        "STMIK Kharisma Makassar",
        "STMIK Profesional Makassar",
        "STIM Nitro Makassar",
        "STKIP YPUP Makassar",
        "STIE Nobel Indonesia",
        "STIM LPI Makassar",
        "Kampus Lain",
        "Belum Kuliah"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySteRegistration3Binding.inflate(layoutInflater)
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
        val noTelepon = intent.getStringExtra("noTelepon")
        val email = intent.getStringExtra("email")

        val adapterkampus = ArrayAdapter(this, R.layout.list_dropdown, itemKampus)

        binding.inputKampus.setAdapter(adapterkampus)

        binding.btnDaftar.setOnClickListener {
            if (binding.inputKampus.text.toString().isEmpty()
                || binding.inputAlasan.text.toString().isEmpty()
            ) Toast.makeText(this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()

            if (binding.inputKampus.text.toString().isNotEmpty()) binding.kampus.error = null
            if (binding.inputAlasan.text.toString().length >= 15) binding.alasan.error = null

            when {
                binding.inputKampus.text.toString() == "" -> binding.kampus.error = " "
                binding.inputAlasan.text.toString() == "" -> binding.alasan.error = " "
                binding.inputAlasan.text.toString().length < 15 -> binding.alasan.error =
                    "Minimal 15 karakter"
                else -> {
                    val asalKampus = binding.inputKampus.text.toString()
                    val alasanSte = binding.inputAlasan.text.toString()

                    steRegistrationPost(
                        namaLengkap,
                        tempatLahir,
                        tanggalLahir,
                        alamat,
                        jenisKelamin,
                        golonganDarah,
                        noTelepon,
                        email,
                        asalKampus,
                        alasanSte
                    )
                }
            }
        }
    }

    private fun steRegistrationPost(
        namaLengkap: String?,
        tempatLahir: String?,
        tanggalLahir: String?,
        alamat: String?,
        jenisKelamin: String?,
        golonganDarah: String?,
        noTelepon: String?,
        email: String?,
        asalKampus: String,
        alasanSte: String
    ) {
        binding.btnDaftar.startAnimation()
        ApiClient.instancesSte.steRegistration(
            namaLengkap.toString(),
            tempatLahir.toString(),
            tanggalLahir.toString(),
            jenisKelamin.toString(),
            golonganDarah.toString(),
            noTelepon.toString(),
            email.toString(),
            alamat.toString(),
            asalKampus,
            alasanSte
        ).enqueue(object : Callback<DaftarResponse> {
            override fun onResponse(
                call: Call<DaftarResponse>,
                response: Response<DaftarResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@SteRegistrationActivity3,
                        response.body()?.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(
                        this@SteRegistrationActivity3,
                        RegistrationSuccessActivity::class.java
                    )
                    binding.btnDaftar.revertAnimation()

                    intent.putExtra("idR", response.body()?.id_peserta)
                    startActivity(intent)
                    finish()
                } else {
                    binding.btnDaftar.revertAnimation()
                    Toast.makeText(
                        this@SteRegistrationActivity3,
                        "Gagal Mendaftar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<DaftarResponse>, t: Throwable) {
                binding.btnDaftar.revertAnimation()
                Toast.makeText(
                    this@SteRegistrationActivity3,
                    "Gagal Mendaftar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}