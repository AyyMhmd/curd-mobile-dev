package com.example.crudayi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudayi.databinding.ActivityAddMahasiswaBinding

class AddMahasiswa : AppCompatActivity() {
    private lateinit var binding : ActivityAddMahasiswaBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddMahasiswaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        dbHelper = DBHelper(this)

        binding.saveButton.setOnClickListener {
            val nim = binding.nimEditText.text.toString()
            val nama = binding.namaEditText.text.toString()
            val prodi = binding.prodiEditText.text.toString()
            val jenisKelamin = binding.jenisKelaminEditText.text.toString() // Ambil nilai jenis kelamin
            val mahasiswa = Mahasiswa(nim, nama, prodi, jenisKelamin) // Masukkan jenis kelamin ke objek Mahasiswa
            dbHelper.InsertMahasiswa(mahasiswa)
            finish()
            Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show() // Menggunakan LENGTH_LONG
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}