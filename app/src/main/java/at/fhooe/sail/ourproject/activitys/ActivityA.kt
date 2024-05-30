package at.fhooe.sail.ourproject.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import at.fhooe.sail.ourproject.ProductAdapter
import at.fhooe.sail.ourproject.databinding.ActivityMainABinding

class ActivityA : AppCompatActivity() {

    private lateinit var binding: ActivityMainABinding
    private val adapter = ProductAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainABinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.buttonAdd.setOnClickListener {
            val product = binding.editText.text.toString()
            if (product.isNotEmpty()) {
                adapter.addProduct(product)
                binding.editText.text.clear()
            }
        }

        binding.buttonRemoveChecked.setOnClickListener {
            adapter.removeCheckedProducts()
        }
    }
}