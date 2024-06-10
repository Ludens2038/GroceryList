package at.fhooe.sail.ourproject

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.sail.ourproject.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import at.fhooe.sail.ourproject.activitys.ActivityA

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val data: MutableList<MainData> = mutableListOf()
    private var currentBackgroundImage: Int = R.drawable.niklas_gucken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityMainToolbar)

        supportActionBar?.title = loadAppBarTitle()

        loadDataFromPreferences()
        currentBackgroundImage = loadBackgroundImage()
        binding.root.setBackgroundResource(currentBackgroundImage)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(_menu: Menu, _menuInflater: MenuInflater) {
                _menuInflater.inflate(R.menu.menue, _menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.activity_main_menuitem_about -> {
                        val intent = Intent(this@MainActivity, Informations::class.java)
                        startActivity(intent)
                        return true
                    }
                    R.id.activity_main_menuitem_changetitle -> {
                        val builder = AlertDialog.Builder(this@MainActivity)
                        val input = EditText(this@MainActivity)
                        builder.setView(input)
                        builder.setTitle("What is the new title?")
                        builder.setPositiveButton("OK") { _, _ ->
                            val newTitle = input.text.toString()
                            if (newTitle.isNotEmpty()) {
                                supportActionBar?.title = newTitle
                                saveAppBarTitle(newTitle)
                            }
                        }
                        builder.setNegativeButton("Abort") { dialog, _ ->
                            dialog.cancel()
                        }
                        builder.show()
                        return true
                    }
                    R.id.activity_main_menuitem_changebackground -> {
                        showBackgroundPickerDialog()
                        return true
                    }
                    else -> return false
                }
            }
        })

        with(binding.activityMainRvList) {
            val pad = 15
            layoutManager = GridLayoutManager(this@MainActivity, 1)
            this.adapter = MainAdapter(data, this@MainActivity)
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

        binding.activityMainBAddlist.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val input = EditText(this)
            builder.setView(input)
            builder.setTitle("Please name your list")
            builder.setPositiveButton("OK") { _, _ ->
                val title = input.text.toString()
                if (title.isNotEmpty()) {
                    data.add(MainData(title, R.drawable.recycle_bin))
                    binding.activityMainRvList.adapter?.notifyDataSetChanged()
                    saveDataToPreferences()
                }
            }
            builder.setNegativeButton("Abort") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
    }

    private fun showBackgroundPickerDialog() {
        val backgrounds = arrayOf(
            R.drawable.background0,
            R.drawable.background1,
            R.drawable.background2,
            R.drawable.background3,
            R.drawable.background4
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Background")

        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.activity_main_backgroud_picker, null)
        builder.setView(dialogView)

        val backgroundImageView0 = dialogView.findViewById<ImageView>(R.id.backgroundImageView0)
        val backgroundImageView1 = dialogView.findViewById<ImageView>(R.id.backgroundImageView1)
        val backgroundImageView2 = dialogView.findViewById<ImageView>(R.id.backgroundImageView2)
        val backgroundImageView3 = dialogView.findViewById<ImageView>(R.id.backgroundImageView3)
        val backgroundImageView4 = dialogView.findViewById<ImageView>(R.id.backgroundImageView4)

        backgroundImageView0.setOnClickListener { setAndSaveBackground(backgrounds[0]) }
        backgroundImageView1.setOnClickListener { setAndSaveBackground(backgrounds[1]) }
        backgroundImageView2.setOnClickListener { setAndSaveBackground(backgrounds[2]) }
        backgroundImageView3.setOnClickListener { setAndSaveBackground(backgrounds[3]) }
        backgroundImageView4.setOnClickListener { setAndSaveBackground(backgrounds[4]) }

        builder.show()
    }

    private fun setAndSaveBackground(backgroundResId: Int) {
        currentBackgroundImage = backgroundResId
        binding.root.setBackgroundResource(currentBackgroundImage)
        saveBackgroundImage(currentBackgroundImage)
    }

    private fun saveBackgroundImage(backgroundResId: Int) {
        val sharedPreferences = getSharedPreferences("main_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("background_image", backgroundResId)
        editor.apply()
    }

    private fun loadBackgroundImage(): Int {
        val sharedPreferences = getSharedPreferences("main_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("background_image", R.drawable.background1)
    }

    private fun saveDataToPreferences() {
        val sharedPreferences = getSharedPreferences("main_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonData = gson.toJson(data)
        editor.putString("main_data", jsonData)
        editor.apply()
    }

    private fun loadDataFromPreferences() {
        val sharedPreferences = getSharedPreferences("main_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonData = sharedPreferences.getString("main_data", null)
        val type = object : TypeToken<MutableList<MainData>>() {}.type
        val savedData: MutableList<MainData> = gson.fromJson(jsonData, type) ?: mutableListOf()
        data.addAll(savedData)
    }

    private fun saveAppBarTitle(title: String) {
        val sharedPreferences = getSharedPreferences("main_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("app_bar_title", title)
        editor.apply()
    }

    private fun loadAppBarTitle(): String {
        val sharedPreferences = getSharedPreferences("main_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("app_bar_title", "My lists") ?: "My lists"
    }

    override fun onResume() {
        super.onResume()
        binding.root.setBackgroundResource(loadBackgroundImage())
    }

    override fun onPause() {
        super.onPause()
        saveDataToPreferences()
    }
}
