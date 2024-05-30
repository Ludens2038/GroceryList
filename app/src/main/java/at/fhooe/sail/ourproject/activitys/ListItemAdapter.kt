package at.fhooe.sail.ourproject.activitys

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.sail.ourproject.R


    class ListItemAdapter(val mData: MutableList<ListItemData>, val mContext: Context) :
        RecyclerView.Adapter<ListDataHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.activity_main_list_element, null)
            return ListDataHolder(view)
        }

        override fun getItemCount(): Int {
            return mData.size
        }

        override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        val Item: String = mContext.getString(mData[position].mItem)
            val drawable: Drawable? = mContext.getDrawable(mData[position].mDelete)
        }
    }
