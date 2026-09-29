package com.example.a24012011115_mad_practical_7

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a24012011115_mad_practical_7.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseHelper
    private lateinit var adapter: PersonAdapter
    private val persons = ArrayList<Person>()

    private val apiUrl = "https://api.json-generator.com/templates/5rDXHcbgpo93/data"
    private val apiToken = "d7wrtfqywyhu7y2bcbsz3cgjpbfisuhnmbibvgvf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        adapter = PersonAdapter(persons) { person -> db.deletePerson(person) }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.fabRefresh.setOnClickListener { fetchPersons() }

        if (db.personsCount == 0) fetchPersons() else showPersons()
    }

    private fun showPersons() {
        persons.clear()
        persons.addAll(db.allPersons)
        adapter.notifyDataSetChanged()
    }

    private fun fetchPersons() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = HttpRequest().makeServiceCall(apiUrl, apiToken)
            val list = if (data != null) parsePersons(data) else null
            if (list != null) {
                db.deleteAll()
                list.forEach { db.insertPerson(it) }
            }
            withContext(Dispatchers.Main) {
                if (list == null) {
                    Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                } else {
                    showPersons()
                }
            }
        }
    }

    private fun parsePersons(json: String): ArrayList<Person>? {
        return try {
            val array = JSONArray(json)
            val list = ArrayList<Person>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val profile = obj.getJSONObject("profile")
                list.add(
                    Person(
                        obj.getString("id"),
                        profile.getString("name"),
                        obj.getString("email"),
                        obj.getString("phone").replace("}", "").trim(),
                        profile.getString("address")
                    )
                )
            }
            list
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
