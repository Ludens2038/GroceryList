package at.fhooe.sail.ourproject.activitys

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.sail.ourproject.MainAdapter
import at.fhooe.sail.ourproject.MainData
import at.fhooe.sail.ourproject.R
import at.fhooe.sail.ourproject.databinding.ActivityABinding

class ActivityA : AppCompatActivity() {

    lateinit var binding: ActivityABinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityABinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityAToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data: MutableList<ListItemData> = mutableListOf(
            ListItemData(R.id.list_item_textView, R.drawable.recycle_bin),
        )

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

        binding.activityAAdd.setOnClickListener {
            data.add(ListItemData(R.id.list_item_textView, R.drawable.recycle_bin))
            binding.activityARecyclerview.adapter?.notifyDataSetChanged()
        }


    }
}

