package co.id.kedai.kedaiapp.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.id.kedai.kedaiapp.R
import co.id.kedai.kedaiapp.databinding.ActivitySteRegistrationBinding
import java.text.SimpleDateFormat
import java.util.*

class SteRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySteRegistrationBinding
    private lateinit var datePickerDialog: DatePickerDialog
    private val itemGoldar = listOf("A", "B", "AB", "O", "Tidak tahu")
    private val itemKelamin = listOf("Laki-laki", "Perempuan")

    @SuppressLint("ClickableViewAccessibility", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySteRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.title = "Pendaftaran STE"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapterGoldar = ArrayAdapter(this, R.layout.list_dropdown, itemGoldar)
        val adapterKelamin = ArrayAdapter(this, R.layout.list_dropdown, itemKelamin)

        binding.inputGolDar.setAdapter(adapterGoldar)
        binding.inputKelamin.setAdapter(adapterKelamin)

        setDateTimeField()
        binding.inputTglLahir.setOnTouchListener { _, _ ->
            datePickerDialog.show()
            false
        }

        binding.btnlanjut.setOnClickListener {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val awal = SimpleDateFormat("dd MMMM yyyy")

            if (binding.inputNama.text.toString().isEmpty()
                || binding.inputTempatLahir.text.toString().isEmpty()
                || binding.inputTglLahir.text.toString().isEmpty()
                || binding.inputAlamat.text.toString().isEmpty()
                || binding.inputKelamin.text.toString().isEmpty()
                || binding.inputGolDar.text.toString().isEmpty()
            ) Toast.makeText(this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()

            when {
                binding.inputNama.text.toString() == "" -> binding.nama.error = " "
                binding.inputTempatLahir.text.toString() == "" -> binding.tempatLahir.error = " "
                binding.inputTglLahir.text.toString() == "" -> binding.tglLahir.error = " "
                binding.inputAlamat.text.toString() == "" -> binding.alamat.error = " "
                binding.inputKelamin.text.toString() == "" -> binding.kelamin.error = " "
                binding.inputGolDar.text.toString() == "" -> binding.golDar.error = " "
                else -> {
                    val date: Date? = awal.parse(binding.inputTglLahir.text.toString())
                    val tanggalConversi: String = simpleDateFormat.format(date!!)
                    var kelamin: String? = null
                    when {
                        binding.inputKelamin.text.toString() == "Laki-laki" -> kelamin = "male"
                        binding.inputKelamin.text.toString() == "Perempuan" -> kelamin = "female"
                    }
                    val intent = Intent(this, SteRegistrationActivity2::class.java)
                    intent.putExtra("namaLengkap", binding.inputNama.text.toString())
                    intent.putExtra("tempatLahir", binding.inputTempatLahir.text.toString())
                    intent.putExtra("tanggalLahir", tanggalConversi)
                    intent.putExtra("alamat", binding.inputAlamat.text.toString())
                    intent.putExtra("jenisKelamin", kelamin)
                    intent.putExtra("golonganDarah", binding.inputGolDar.text.toString())
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("SimpleDateFormat")
    private fun setDateTimeField() {
        val newCalendar = Calendar.getInstance()
        newCalendar.set(2002, 0, 1)
        datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                val sd = SimpleDateFormat("dd MMMM yyyy")
                val startDate = newDate.time
                val fdate = sd.format(startDate)
                binding.inputTglLahir.setText(fdate)
            }, newCalendar[Calendar.YEAR], newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    }
}



