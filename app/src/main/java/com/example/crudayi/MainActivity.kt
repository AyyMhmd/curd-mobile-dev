package com.example.crudayi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var mahasiswaAdapter: MahasiswaAdapter
    private lateinit var mahasiswaRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DBHelper(this)
        mahasiswaAdapter = MahasiswaAdapter(dbHelper.getAllMahasiswa(), this)

        mahasiswaRecyclerView = findViewById(R.id.mahasiswaRecycleView)
        mahasiswaRecyclerView.layoutManager = LinearLayoutManager(this)
        mahasiswaRecyclerView.adapter = mahasiswaAdapter


        val tombolTambah: FloatingActionButton = findViewById(R.id.TombolTambah)
        tombolTambah.setOnClickListener {
            val intent = Intent(this, AddMahasiswa::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        mahasiswaAdapter.refreshData(dbHelper.getAllMahasiswa())
    }
}