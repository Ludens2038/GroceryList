package at.fhooe.sail.ourproject

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import at.fhooe.sail.ourproject.activitys.ActivityA
import com.google.gson.Gson

class MainAdapter(private val mData: MutableList<MainData>, private val mContext: Context) : RecyclerView.Adapter<MainDataHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainDataHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.activity_main_list_element, parent, false)
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
            intent.putExtra("list_title", title)

            // Hintergrundbild von SharedPreferences laden und hinzufügen
            val sharedPreferences = mContext.getSharedPreferences("main_prefs", Context.MODE_PRIVATE)
            val backgroundImage = sharedPreferences.getInt("background_image", R.drawable.background1)
            intent.putExtra("background_image", backgroundImage)

            mContext.startActivity(intent)
        }

        // Titel der Liste ändern
        holder.root.setOnLongClickListener {
            val builder = AlertDialog.Builder(mContext)
            val input = EditText(mContext)
            input.setText(title)
            builder.setView(input)
            builder.setTitle("Change Title")
            builder.setPositiveButton("OK") { _, _ ->
                val newTitle = input.text.toString()
                if (newTitle.isNotEmpty()) {
                    mData[position].mTitle = newTitle
                    notifyDataSetChanged()
                    saveDataToPreferences()  // Daten in SharedPreferences speichern
                    Toast.makeText(mContext, "Title changed to $newTitle", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Abort") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
            true
        }

        holder.mDelete.setOnClickListener {
            val deletedTitle = mData[position].mTitle
            mData.removeAt(position)
            notifyDataSetChanged()
            deleteDataFromPreferences(deletedTitle)  // Daten aus SharedPreferences löschen
            Toast.makeText(mContext, "List $deletedTitle deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveDataToPreferences() {
        val sharedPreferences = mContext.getSharedPreferences("main_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonData = gson.toJson(mData)
        editor.putString("main_data", jsonData)
        editor.apply()
    }

    private fun deleteDataFromPreferences(listTitle: String) {
        val sharedPreferences = mContext.getSharedPreferences("main_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonData = gson.toJson(mData)
        editor.putString("main_data", jsonData)
        editor.apply()

        val sublistPreferences = mContext.getSharedPreferences("sublist_prefs", Context.MODE_PRIVATE)
        val sublistEditor = sublistPreferences.edit()
        sublistEditor.remove(listTitle)
        sublistEditor.apply()
    }
}
