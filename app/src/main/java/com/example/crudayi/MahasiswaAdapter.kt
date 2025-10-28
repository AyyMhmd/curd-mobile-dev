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

    private val dbHelper = DBHelper(context) // Inisialisasi DBHelper di sini

    class MahasiswaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nimTextView: TextView = itemView.findViewById(R.id.nimTextView)
        val namaTextView: TextView = itemView.findViewById(R.id.namaTextView)
        val prodiTextView: TextView = itemView.findViewById(R.id.prodiTextView)
        val jenisKelaminTextView: TextView = itemView.findViewById(R.id.jenisKelaminTextView) // Inisialisasi TextView baru
        val updateButton: TextView = itemView.findViewById(R.id.updateButton)
        val deleteButton: TextView = itemView.findViewById(R.id.deleteButton)
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
        holder.jenisKelaminTextView.text = "Jenis Kelamin: ${mahasiswa.jenisKelamin}"

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateMahasiswaActivity::class.java)
            intent.putExtra("oldNim", mahasiswa.nim)
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            dbHelper.deleteMahasiswa(mahasiswa.nim)
            refreshData(dbHelper.getAllMahasiswa())
            Toast.makeText(holder.itemView.context, "Data Mahasiswa Berhasil Dihapus", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int = mahasiswaList.size

    fun refreshData(newMahasiswaList: List<Mahasiswa>){
        mahasiswaList = newMahasiswaList
        notifyDataSetChanged()
    }
}