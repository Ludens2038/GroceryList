package at.fhooe.sail.ourproject.activitys

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
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
    private var currentBackgroundImage: Int = R.drawable.background1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityABinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityAToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listTitle = intent.getStringExtra("list_title") ?: return

        supportActionBar?.title = listTitle

        currentBackgroundImage = intent.getIntExtra("background_image", R.drawable.background1)
        binding.root.setBackgroundResource(currentBackgroundImage)

        val data: MutableList<ListItemData> = loadDataFromPreferences() ?: mutableListOf()

        with(binding.activityARecyclerview) {
            val pad = 15
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
                    outRect.top = pad
                    outRect.bottom = pad
                    outRect.left = pad
                    outRect.right = pad
                }
            })
        }

        binding.activityAAddlist.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val input = EditText(this)
            builder.setView(input)
            builder.setTitle("What do you want to add?")
            builder.setPositiveButton("OK") { _, _ ->
                val title = input.text.toString()
                if (title.isNotEmpty()) {
                    data.add(ListItemData(title, R.drawable.recycle_bin))
                    binding.activityARecyclerview.adapter?.notifyDataSetChanged()

                    saveDataToPreferences(data)
                }
            }
            builder.setNegativeButton("Abort") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveDataToPreferences(data: List<ListItemData>) {
        val sharedPreferences = getSharedPreferences("sublist_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonData = gson.toJson(data)
        editor.putString(listTitle, jsonData)
        editor.apply()
    }

    private fun loadDataFromPreferences(): MutableList<ListItemData>? {
        val sharedPreferences = getSharedPreferences("sublist_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonData = sharedPreferences.getString(listTitle, null)
        val type = object : TypeToken<MutableList<ListItemData>>() {}.type
        return gson.fromJson(jsonData, type)
    }
}
