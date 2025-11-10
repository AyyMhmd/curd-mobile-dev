package com.example.crudayi

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudayi.databinding.ActivityAddMahasiswaBinding
import com.airbnb.lottie.LottieAnimationView

class AddMahasiswa : AppCompatActivity() {
    private lateinit var binding : ActivityAddMahasiswaBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var successAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddMahasiswaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        dbHelper = DBHelper(this)

        successAnimationView = findViewById(R.id.successAnimationView)

        binding.saveButton.setOnClickListener {
            val nim = binding.nimEditText.text.toString()
            val nama = binding.namaEditText.text.toString()
            val prodi = binding.prodiEditText.text.toString()
            val jenisKelamin = binding.jenisKelaminEditText.text.toString()
            val alamat = binding.AlamatEditText.text.toString()
            val semester = binding.SemesterEditText.text.toString()
            val tahunmasuk = binding.TahunMasukEditText.text.toString()



            if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty() || jenisKelamin.isEmpty()) {
                Toast.makeText(this, "Harap isi semua kolom!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mahasiswa = Mahasiswa(nim, nama, prodi, jenisKelamin, alamat, semester,tahunmasuk)
            dbHelper.InsertMahasiswa(mahasiswa)

            // Tampilkan dan mainkan animasi sukses
            successAnimationView.visibility = View.VISIBLE
            successAnimationView.playAnimation()

            successAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    successAnimationView.visibility = View.GONE
                    Toast.makeText(this@AddMahasiswa, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show()
                    finish()
                }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}