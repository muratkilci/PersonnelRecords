package com.example.personelkayit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personelkayit.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var database = FirebaseDatabase.getInstance().reference




        //veri ekleme işlemi
        binding.button.setOnClickListener{
            var etno =binding.etno.text.toString().toInt()
            var etadsoyad =binding.etadsoyad.text.toString()
            var etmaas =binding.etmaas.text.toString().toInt()
            //database.setValue(Personel(etno,etadsoyad,etmaas))    bu yöntem sürekli aynı verinin üzerine yazar
            database.child(etno.toString()).setValue(Personel(etadsoyad,etmaas))
        }


        //veri okuma işlemi
        var getdata= object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()  // metin birleştirme sınıfı
                for(i in snapshot.children){
                    var namesurname = i.child("padsoyad").getValue()
                    var salary = i.child("pmaas").getValue()
                    sb.append("${i.key} $namesurname $salary \n")
                }
                binding.tvsonuc.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)

    }

}