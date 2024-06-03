package at.fhooe.sail.ourproject

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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