package at.fhooe.sail.ourproject

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.sail.ourproject.activitys.ActivityA

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
        val title: String = mData[position].mTitle
        val drawable: Drawable? = mContext.getDrawable(mData[position].mDelete)

        holder.mTitle.text = title
        holder.mDelete.setImageDrawable(drawable)

        holder.root.setOnClickListener {
            val intent = Intent(mContext, ActivityA::class.java)
            mContext.startActivity(intent)
        }

        holder.mDelete.setOnClickListener {
            val deletedTitle = mData[position].mTitle
            mData.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(mContext, "$deletedTitle gelöscht", Toast.LENGTH_SHORT).show()
        }
    }
}