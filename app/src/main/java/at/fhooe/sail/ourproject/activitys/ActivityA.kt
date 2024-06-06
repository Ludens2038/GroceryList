package at.fhooe.sail.ourproject.activitys

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.sail.ourproject.R
import at.fhooe.sail.ourproject.databinding.ActivityABinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class ActivityA : AppCompatActivity() {

    lateinit var binding: ActivityABinding
    lateinit var listTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityABinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityAToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Den Listennamen aus dem Intent abrufen
        listTitle = intent.getStringExtra("list_title") ?: return

        // Titel der App-Bar setzen
        supportActionBar?.title = listTitle

        // Daten aus SharedPreferences abrufen
        val data: MutableList<ListItemData> = loadDataFromPreferences() ?: mutableListOf()

        with(binding.activityARecyclerview) {
            val pad = 10
            layoutManager = GridLayoutManager(this@ActivityA, 1)
            this.adapter = ListItemAdapter(data, this@ActivityA)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.left = pad
                    outRect.right = pad
                    outRect.top = pad
                    outRect.bottom = pad
                }
            })
        }

        binding.activityAAddlist.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val input = EditText(this)
            builder.setView(input)
            builder.setTitle("Bitte benennen Sie die Liste")
            builder.setPositiveButton("OK") { _, _ ->
                val title = input.text.toString()
                if (title.isNotEmpty()) {
                    // Neues Element zur Liste hinzufügen
                    data.add(ListItemData(title, R.drawable.recycle_bin))
                    binding.activityARecyclerview.adapter?.notifyDataSetChanged()

                    // Daten in SharedPreferences speichern
                    saveDataToPreferences(data)
                }
            }
            builder.setNegativeButton("Abbrechen") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
    }

    private fun saveDataToPreferences(data: List<ListItemData>) {
        val sharedPreferences = getSharedPreferences("sublist_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonData = gson.toJson(data)
        editor.putString(listTitle, jsonData)  // Verwenden Sie den Listennamen als Schlüssel
        editor.apply()
    }

    private fun loadDataFromPreferences(): MutableList<ListItemData>? {
        val sharedPreferences = getSharedPreferences("sublist_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonData = sharedPreferences.getString(listTitle, null)  // Verwenden Sie den Listennamen als Schlüssel
        val type = object : TypeToken<MutableList<ListItemData>>() {}.type
        return gson.fromJson(jsonData, type)
    }
}
