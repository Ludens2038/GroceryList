package at.fhooe.sail.ourproject.activitys

//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import at.fhooe.sail.ourproject.databinding.ActivityMainABinding
//
//class ActivityA : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainABinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainABinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setSupportActionBar(binding.activityAToolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(true)
//
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter
//
//        binding.buttonAdd.setOnClickListener {
//            val product = binding.editText.text.toString()
//            if (product.isNotEmpty()) {
//                adapter.addProduct(product)
//                binding.editText.text.clear()
//            }
//        }
//
//        binding.buttonRemoveChecked.setOnClickListener {
//            adapter.removeCheckedProducts()
//        }
//    }
//}