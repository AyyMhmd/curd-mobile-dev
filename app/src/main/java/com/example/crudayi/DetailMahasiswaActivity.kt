package com.example.crudayi

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView

class DetailMahasiswaActivity : AppCompatActivity() {

    private lateinit var db: DBHelper
    private lateinit var successAnimationView: LottieAnimationView // Deklarasi LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_mahasiswa)

        db = DBHelper(this)

        successAnimationView = findViewById(R.id.successAnimationView) // Inisialisasi LottieAnimationView

        val nimFromIntent = intent.getStringExtra("nim")

        if (nimFromIntent != null) {
            val mahasiswa = db.getMhswbyNIM(nimFromIntent)

            if (mahasiswa != null) {
                findViewById<TextView>(R.id.detailNimTextView).text = "NIM: ${mahasiswa.nim}"
                findViewById<TextView>(R.id.detailNamaTextView).text = "Nama: ${mahasiswa.nama}"
                findViewById<TextView>(R.id.detailProdiTextView).text = "Prodi: ${mahasiswa.prodi}"
                findViewById<TextView>(R.id.detailJenisKelaminTextView).text = "Jenis Kelamin: ${mahasiswa.jenisKelamin}"
                findViewById<TextView>(R.id.detailAlamatTextView).text = "Alamat: ${mahasiswa.alamat}"
                findViewById<TextView>(R.id.detailTahunMasukTextView).text = "Tahun Masuk: ${mahasiswa.tahunMasuk}"
                findViewById<TextView>(R.id.detailNoHpTextView).text = "No. HP: ${mahasiswa.noHp}" 
                findViewById<TextView>(R.id.detailEmailTextView).text = "Email: ${mahasiswa.email}" 

                val detailEditButton = findViewById<ImageView>(R.id.detailEditButton)
                val detailDeleteButton = findViewById<ImageView>(R.id.detailDeleteButton)

                detailEditButton.setOnClickListener {
                    val intent = Intent(this, UpdateMahasiswaActivity::class.java)
                    intent.putExtra("oldNim", mahasiswa.nim) 
                    startActivity(intent)
                }

                detailDeleteButton.setOnClickListener {
                    db.deleteMahasiswa(mahasiswa.nim)

                    successAnimationView.bringToFront()
                    successAnimationView.visibility = View.VISIBLE
                    successAnimationView.playAnimation()

                    successAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {}
                        override fun onAnimationEnd(animation: Animator) {
                            successAnimationView.visibility = View.GONE
                            Toast.makeText(this@DetailMahasiswaActivity, "Data Mahasiswa Berhasil Dihapus", Toast.LENGTH_LONG).show()
                            finish() 
                        }
                        override fun onAnimationCancel(animation: Animator) {}
                        override fun onAnimationRepeat(animation: Animator) {}
                    })
                }

            } else {
                Toast.makeText(this, "Data mahasiswa tidak ditemukan.", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "NIM tidak diterima.", Toast.LENGTH_SHORT).show()
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}