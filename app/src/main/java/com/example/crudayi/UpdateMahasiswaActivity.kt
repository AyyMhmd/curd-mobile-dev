package com.example.crudayi

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView

class UpdateMahasiswaActivity : AppCompatActivity() {

    private lateinit var db: DBHelper
    private var Fnim: String = ""
    private lateinit var successAnimationView: LottieAnimationView // Deklarasi LottieAnimationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_mahasiswa)

        val editNimEditText = findViewById<EditText>(R.id.editNimEditText)
        val editNamaEditText = findViewById<EditText>(R.id.editNamaEditText)
        val editProdiEditText = findViewById<EditText>(R.id.editProdiEditText)
        val editJenisKelaminEditText = findViewById<EditText>(R.id.editJenisKelaminEditText)
        val editAlamatEditText = findViewById<EditText>(R.id.editAlamatEditText)
        val editTahunMasukEditText = findViewById<EditText>(R.id.editTahunMasukEditText)
        val editNoHpEditText = findViewById<EditText>(R.id.editNoHpEditText) 
        val editEmailEditText = findViewById<EditText>(R.id.editEmailEditText) 
        val saveButton = findViewById<ImageView>(R.id.saveButton)

        successAnimationView = findViewById(R.id.successAnimationView) 

        Fnim = intent.getStringExtra("oldNim") ?: return run {
            Toast.makeText(this, "NIM mahasiswa tidak ditemukan.", Toast.LENGTH_LONG).show()
            finish()
        }

        db = DBHelper(this)

        val mhsw = db.getMhswbyNIM(Fnim)

        if (mhsw != null) { 
            editNimEditText.setText(mhsw.nim)
            editNamaEditText.setText(mhsw.nama)
            editProdiEditText.setText(mhsw.prodi)
            editJenisKelaminEditText.setText(mhsw.jenisKelamin)
            editAlamatEditText.setText(mhsw.alamat)
            editTahunMasukEditText.setText(mhsw.tahunMasuk)
            editNoHpEditText.setText(mhsw.noHp) 
            editEmailEditText.setText(mhsw.email) 
        } else {
            Toast.makeText(this, "Data mahasiswa tidak ditemukan.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        saveButton.setOnClickListener {
            val newNIM = editNimEditText.text.toString()
            val newNAMA = editNamaEditText.text.toString()
            val newProdi = editProdiEditText.text.toString()
            val newJenisKelamin = editJenisKelaminEditText.text.toString()
            val newAlamat = editAlamatEditText.text.toString()
            val newTahunMasuk = editTahunMasukEditText.text.toString()
            val newNoHp = editNoHpEditText.text.toString() 
            val newEmail = editEmailEditText.text.toString() 

            if (newNIM.isEmpty() || newNAMA.isEmpty() || newProdi.isEmpty() || newJenisKelamin.isEmpty() || newAlamat.isEmpty() || newTahunMasuk.isEmpty() || newNoHp.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(this, "Harap isi semua kolom!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updateMhsw = Mahasiswa(
                newNIM,
                newNAMA,
                newProdi,
                newJenisKelamin,
                newAlamat,
                newTahunMasuk,
                newNoHp, 
                newEmail 
            )
            db.updateMahasiswa(Fnim, updateMhsw)

            
            successAnimationView.bringToFront()
            successAnimationView.visibility = View.VISIBLE
            successAnimationView.playAnimation()

            successAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    successAnimationView.visibility = View.GONE
                    Toast.makeText(this@UpdateMahasiswaActivity, "Data Berhasil Diupdate", Toast.LENGTH_LONG).show()
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