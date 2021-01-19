package com.example.countrycapital

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.println
import android.widget.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintStream
import java.sql.DriverManager.println

class MainActivity : AppCompatActivity() {

    private lateinit var edcountry: EditText
    private lateinit var edcapital: EditText
    private lateinit var btnaddcountry: Button
    private lateinit var listcountry: ListView
    private var countryCapitalMap = mutableMapOf<String,String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edcapital = findViewById(R.id.edcapital)
        edcountry = findViewById(R.id.edcountry)
        listcountry = findViewById(R.id.listcountry)
        btnaddcountry = findViewById(R.id.addcountry)

        loadCountriesFromText()

        btnaddcountry.setOnClickListener {
            addCountryToText()
            loadCountriesFromText()
            edcapital.text.clear()
            edcountry.text.clear()

        }
    }

    private fun addCountryToText(){
        try {
            val country =edcountry.text.toString()
            val capital =edcapital.text.toString()
            val printStream = PrintStream(
                    openFileOutput("Country.txt", Context.MODE_APPEND)
            )
            printStream.println("$country -> $capital")
            Toast.makeText(
                    this, "$country saved", Toast.LENGTH_SHORT).show()
        } catch (e:IOException){
            Toast.makeText(this, "Error ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCountriesFromText() {
        try {
            val fileInputStream = openFileInput("Country.txt")
            val inputStream = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStream)
            for (line in bufferedReader.lines()) {

                val countryCapital = line.split(" -> ")
                val country = countryCapital[0]
                val capital = countryCapital[1]
                countryCapitalMap[country] = capital
            }
            displayCountries(countryCapitalMap)
        } catch (e:IOException) {
            Toast.makeText(this, "Error ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }

    }

    private fun displayCountries(countryCapitalMap:MutableMap<String, String>){
        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, countryCapitalMap.keys.toTypedArray()
        )
        listcountry.adapter=adapter
    }
}

