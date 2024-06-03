package at.fhooe.sail.ourproject

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.sail.ourproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityMainToolbar)

        //Titel der App-Bar gesetzt
        supportActionBar?.title = "Meine Listen"

        addMenuProvider(object : MenuProvider{
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
                        builder.setTitle("Bitte geben Sie den neuen Titel ein")
                        builder.setPositiveButton("OK") { _, _ ->
                            val newTitle = input.text.toString()
                            if (newTitle.isNotEmpty()) {
                                supportActionBar?.title = newTitle
                            }
                        }
                        builder.setNegativeButton("Abbrechen") { dialog, _ ->
                            dialog.cancel()
                        }
                        builder.show()
                        return true
                    }
                    else -> return false
                }
            }
        })

        val data: MutableList<MainData> = mutableListOf()

        with(binding.activityMainRvList) {
            val pad = 10
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
                    outRect.left = pad
                    outRect.right = pad
                    outRect.top = pad
                    outRect.bottom = pad
                }
            })

        }

        binding.activityMainBAddlist.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val input = EditText(this)
            builder.setView(input)
            builder.setTitle("Bitte bennen Sie die Liste")
            builder.setPositiveButton("OK"){ _, _ ->
                val title = input.text.toString()
                if (title.isNotEmpty()) {
                    data.add(MainData(title, R.drawable.recycle_bin))
                    binding.activityMainRvList.adapter?.notifyDataSetChanged()
                }
            }
            builder.setNegativeButton("Abbrechen") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
    }
}