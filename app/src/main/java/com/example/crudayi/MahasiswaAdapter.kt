package com.example.crudayi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MahasiswaAdapter(private var mahasiswaList: List<Mahasiswa>, private val context: Context) : RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>() {

    private val dbHelper = DBHelper(context) 

    class MahasiswaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nimTextView: TextView = itemView.findViewById(R.id.nimTextView)
        val namaTextView: TextView = itemView.findViewById(R.id.namaTextView)
        val prodiTextView: TextView = itemView.findViewById(R.id.prodiTextView)
        // Menghapus referensi ke jenisKelaminTextView, alamatTextView, semesterTextView, tahunMasukTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item_mahasiswa, parent, false)
        return MahasiswaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        val mahasiswa = mahasiswaList[position]
        holder.nimTextView.text = "NIM: ${mahasiswa.nim}"
        holder.namaTextView.text = "Nama: ${mahasiswa.nama}"
        holder.prodiTextView.text = "Prodi: ${mahasiswa.prodi}"
        // Menghapus binding untuk jenisKelaminTextView, alamatTextView, semesterTextView, tahunMasukTextView

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailMahasiswaActivity::class.java)
            intent.putExtra("nim", mahasiswa.nim) 
            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int = mahasiswaList.size

    fun refreshData(newMahasiswaList: List<Mahasiswa>){
        mahasiswaList = newMahasiswaList
        notifyDataSetChanged()
    }
}