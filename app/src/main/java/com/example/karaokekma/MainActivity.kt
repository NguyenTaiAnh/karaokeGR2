package com.example.karaokekma

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    var urlGetData: String ="https://byyswag.000webhostapp.com/?keyword=a"
    var mangMS: ArrayList<String> = ArrayList()
    var adapterMS : ArrayAdapter<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    listView = findViewById(R.id.lvMusic)
        getData().execute(urlGetData)
        adapterMS=ArrayAdapter(this,android.R.layout.simple_list_item_1,mangMS)
        listView.adapter = adapterMS
    }

    inner class getData : AsyncTask<String, Void, String> (){
        override fun doInBackground(vararg params: String?): String {
            return getContentURL(params[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var song: String =""
            var songkey: String = ""
//            Toast.makeText(applicationContext, result,Toast.LENGTH_LONG).show()
            var  jsonArr : JSONArray = JSONArray(result)
            for (music in 0 ..jsonArr.length()-1){
                var objectMS: JSONObject = jsonArr.getJSONObject(music)
                song = objectMS.getString("song")
                songkey = objectMS.getString("songkey")
                mangMS.add(song+" - " + songkey)
            }
        }

    }
    private fun getContentURL(url: String?) : String{
        var content: StringBuilder = StringBuilder();
        val url: URL = URL(url)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader: InputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

        var line: String = ""
        try {
            do {
                line = bufferedReader.readLine()
                if(line != null){
                    content.append(line)
                }
            }while (line != null)
            bufferedReader.close()
        }catch (e: Exception){
            Log.d("AAA", e.toString())
        }
        return content.toString()
    }


}