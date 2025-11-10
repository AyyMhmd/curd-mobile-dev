package com.example.crudayi

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UpdateMahasiswaActivity : AppCompatActivity() {

    private lateinit var db: DBHelper
    private var Fnim: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_mahasiswa)

        val editNimEditText = findViewById<EditText>(R.id.editNimEditText)
        val editNamaEditText = findViewById<EditText>(R.id.editNamaEditText)
        val editProdiEditText = findViewById<EditText>(R.id.editProdiEditText)
        val editJenisKelaminEditText = findViewById<EditText>(R.id.editJenisKelaminEditText) // Inisialisasi EditText baru
        val editAlamatEditText = findViewById<EditText>(R.id.editAlamatEditText)
        val editSemesterEditText = findViewById<EditText>(R.id.editSemesterEditText)
        val editTahunMasukEditText = findViewById<EditText>(R.id.editTahunMasukEditText)
        val saveButton = findViewById<ImageView>(R.id.saveButton)

        Fnim = intent.getStringExtra("oldNim") ?: return run { // Menggunakan "oldNim" dan Elvis operator
            Toast.makeText(this, "NIM mahasiswa tidak ditemukan.", Toast.LENGTH_LONG).show()
            finish()
        }

        db = DBHelper(this)

        val mhsw = db.getMhswbyNIM(Fnim)

        if (mhsw != null) { // Pengecekan null di sini
            editNimEditText.setText(mhsw.nim)
            editNamaEditText.setText(mhsw.nama)
            editProdiEditText.setText(mhsw.prodi)
            editJenisKelaminEditText.setText(mhsw.jenisKelamin) // Isi nilai jenis kelamin
            editAlamatEditText.setText(mhsw.alamat)
            editSemesterEditText.setText(mhsw.semester)
            editTahunMasukEditText.setText(mhsw.tahunmasuk)

        } else {
            Toast.makeText(this, "Data mahasiswa tidak ditemukan.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        saveButton.setOnClickListener {
            val newNIM = editNimEditText.text.toString()
            val newNAMA = editNamaEditText.text.toString()
            val newProdi = editProdiEditText.text.toString()
            val newJenisKelamin = editJenisKelaminEditText.text.toString() // Ambil nilai jenis kelamin baru
            val newAlamat = editAlamatEditText.text.toString()
            val newSemester = editSemesterEditText.text.toString()
            val newTahunMasuk =editTahunMasukEditText.text.toString()



            val updateMhsw = Mahasiswa(newNIM, newNAMA, newProdi, newJenisKelamin, newAlamat, newSemester, newTahunMasuk) // Masukkan jenis kelamin
            db.updateMahasiswa(Fnim, updateMhsw)

            Toast.makeText(this, "Data Berhasil Diupdate", Toast.LENGTH_LONG).show()
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}