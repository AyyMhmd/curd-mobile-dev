package com.example.crudayi

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val QueryTabel ="CREATE TABLE $TABLE_NAME ($COLUMN_NIM TEXT PRIMARY KEY, $COLUMN_NAMA TEXT, $COLUMN_PRODI TEXT, $COLUMN_JENIS_KELAMIN TEXT, $COLUMN_ALAMAT TEXT, $COLUMN_SEMESTER TEXT,$COLUMN_TAHUNMASUK)"
        db?.execSQL(QueryTabel)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        val QueryHapus = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(QueryHapus)
        onCreate(db)
    }

    fun InsertMahasiswa(mahasiswa: Mahasiswa){
        val db = writableDatabase
        val dataMahasiswa = ContentValues().apply {
            put(COLUMN_NIM, mahasiswa.nim)
            put(COLUMN_NAMA, mahasiswa.nama)
            put(COLUMN_PRODI, mahasiswa.prodi)
            put(COLUMN_JENIS_KELAMIN, mahasiswa.jenisKelamin)
            put(COLUMN_ALAMAT, mahasiswa.alamat)
            put(COLUMN_SEMESTER, mahasiswa.semester)
            put(COLUMN_TAHUNMASUK,mahasiswa.tahunmasuk)
        }
        db.insert(TABLE_NAME, null, dataMahasiswa)
        db.close()
    }

    fun getAllMahasiswa(): List<Mahasiswa> {
        val mahasiswaList = mutableListOf<Mahasiswa>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
                val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
                val prodi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODI))
                val jenisKelamin = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JENIS_KELAMIN))
                val alamat = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ALAMAT))
                val semester = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEMESTER))
                val tahunmasuk = cursor.getString(cursor.getColumnIndexOrThrow( COLUMN_TAHUNMASUK))
                val mahasiswa = Mahasiswa(nim, nama, prodi, jenisKelamin, alamat, semester, tahunmasuk)
                mahasiswaList.add(mahasiswa)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return mahasiswaList
    }

    companion object{
        private const val DATABASE_NAME = "mahasiswa.db"
        private const val DATABASE_VERSION = 3 // <-- VERSI INI DINAINKAN KE 3
        private const val TABLE_NAME = "Mahasiswa"
        private const val COLUMN_NIM = "nim"
        private const val COLUMN_NAMA = "nama"
        private const val COLUMN_PRODI = "prodi"
        private const val COLUMN_JENIS_KELAMIN = "jenisKelamin"
        private const val COLUMN_ALAMAT = "alamat"
        private const val COLUMN_SEMESTER = "semester"
        private const val COLUMN_TAHUNMASUK = "tahunmasuk"



    }
    fun getMhswbyNIM (nim: String): Mahasiswa? { // Mengembalikan Mahasiswa? (nullable)
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NIM = ?" // Menggunakan placeholder
        val cursor = db.rawQuery(query, arrayOf(nim)) // Menggunakan selectionArgs
        
        var mahasiswa: Mahasiswa? = null
        if (cursor.moveToFirst()) {
            val foundNim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
            val foundNama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
            val foundProdi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODI))
            val foundJenisKelamin = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JENIS_KELAMIN))
            val foundAlamat = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ALAMAT))
            val foundSemester = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEMESTER))
            val foundTahunMasuk = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAHUNMASUK))

            mahasiswa = Mahasiswa(foundNim, foundNama, foundProdi, foundJenisKelamin, foundAlamat, foundSemester, foundTahunMasuk)
        }
        cursor.close()
        db.close()
        return mahasiswa
    }
    fun updateMahasiswa(oldNIM: String, mahasiswa: Mahasiswa){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NIM, mahasiswa.nim)
            put(COLUMN_NAMA, mahasiswa.nama)
            put(COLUMN_PRODI, mahasiswa.prodi)
            put(COLUMN_JENIS_KELAMIN, mahasiswa.jenisKelamin)
            put(COLUMN_ALAMAT, mahasiswa.alamat)
            put(COLUMN_SEMESTER, mahasiswa.semester)
            put(COLUMN_TAHUNMASUK, mahasiswa.tahunmasuk)
        }
        val where = "$COLUMN_NIM = ?"
        val arg = arrayOf(oldNIM) 
        db.update(TABLE_NAME, values, where, arg)
        db.close()
    }
    fun deleteMahasiswa(nim: String){
        val db = writableDatabase
        val where = "$COLUMN_NIM = ?"
        val arg = arrayOf(nim) 
        db.delete(TABLE_NAME, where, arg)

        db.close()
    }
}