package at.fhooe.sail.ourproject

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(val mData: MutableList<MainData>, val mContext: Context) : RecyclerView.Adapter<MainDataHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainDataHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.activity_main_list_element, null)
        return MainDataHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MainDataHolder, position: Int) {
        val drawable: Drawable? = mContext.getDrawable(mData[position].)
        val title: String = mContext.getString(mData[position].title)

        holder.mImage.setImageDrawable(drawable)

        holder.itemView.findViewById<TextView>(R.id.activity_main_list_element_title).setOnClickListener {
            Toast.makeText(mContext, "Content Clicked in Main List", Toast.LENGTH_SHORT).show()
        }
    }
}