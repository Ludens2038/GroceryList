package at.fhooe.sail.ourproject

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainDataHolder(val root: View) : RecyclerView.ViewHolder(root) {

    var mContent: TextView
        init {
            mContent = root.findViewById(R.id.activity_main_list_element_title)
            mContent = root.findViewById(R.id.activity_main_lsit_element_b_delete)
        }
}