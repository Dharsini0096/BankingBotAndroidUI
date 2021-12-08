package com.example.bankingbot

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.android.synthetic.main.bank_selection.*
import java.util.*
import kotlin.collections.ArrayList

class ChoiceActivity:AppCompatActivity(){

    lateinit var bankname:String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.bank_selection)

        val imageview = findViewById<ImageSlider>(R.id.imageView)

        val imagelist = ArrayList<SlideModel>()

        imagelist.add(SlideModel(R.drawable.meeting1,"Image 1"))
        imagelist.add(SlideModel(R.drawable.meeting2,"Image 2"))
        imagelist.add(SlideModel(R.drawable.meeting3,"Image 3"))

        imageview.setImageList(imagelist,ScaleTypes.FIT)
        spinnerfun()

        chatselectionbtn.setOnClickListener {

            if(bankname.equals("Choose bank",true))
            {
                Toast.makeText(this,"Select bank from drop down menu",Toast.LENGTH_SHORT).show()
            }
            else {
                val message = bankname.toLowerCase(Locale.ROOT)

                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("bankname", message)

                startActivity(intent)
            }
        }


    }

    private fun spinnerfun() {

        val adapter = ArrayAdapter.createFromResource(this,R.array.banks,android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bankname = parent!!.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

                Toast.makeText(this@ChoiceActivity,"Select bank from drop down menu",Toast.LENGTH_SHORT).show()
            }
        }

    }
}
