package at.fhooe.sail.ourproject

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainDataHolder(val root: View) : RecyclerView.ViewHolder(root) {

    val mTitle: TextView
    var mDelete: ImageView
        init {
            mTitle = root.findViewById(R.id.activity_main_list_element_title)
            mDelete= root.findViewById(R.id.activity_main_lsit_element_b_delete)
        }
}