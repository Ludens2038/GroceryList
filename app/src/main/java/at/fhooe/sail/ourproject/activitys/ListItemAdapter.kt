package at.fhooe.sail.ourproject.activitys

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.sail.ourproject.R
import com.google.gson.Gson

class ListItemAdapter(private val mData: MutableList<ListItemData>, private val mContext: Context) :
    RecyclerView.Adapter<ListDataHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.activity_a_list_element, parent, false)
        return ListDataHolder(view)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        val item: String = mData[position].mItem
        val drawable: Drawable? = mContext.getDrawable(mData[position].mDelete)

        holder.mItem.text = item
        holder.mDelete.setImageDrawable(drawable)

        holder.mDelete.setOnClickListener {
            val deletedEntry = mData[position].mItem
            mData.removeAt(position)
            notifyDataSetChanged()
            saveDataToPreferences(mData)  // Speichern der aktualisierten Liste
            Toast.makeText(mContext, "Entry '$deletedEntry' deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveDataToPreferences(data: List<ListItemData>) {
        val sharedPreferences = mContext.getSharedPreferences("sublist_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonData = gson.toJson(data)
        editor.putString((mContext as ActivityA).listTitle, jsonData)
        editor.apply()
    }
}
