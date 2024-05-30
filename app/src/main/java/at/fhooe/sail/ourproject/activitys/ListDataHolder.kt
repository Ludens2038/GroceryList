package at.fhooe.sail.ourproject.activitys

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.sail.ourproject.R


class ListDataHolder(val root: View) : RecyclerView.ViewHolder(root) {

    var mItem : TextView
    val mDelete: ImageView

    init {
        mDelete = root.findViewById(R.id.list_item_delete)
        mItem= root.findViewById(R.id.list_item_textView)

    }
}
