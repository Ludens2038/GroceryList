package at.fhooe.sail.ourproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import at.fhooe.sail.ourproject.databinding.ActivityInformationsBinding

class Informations : AppCompatActivity() {

    lateinit var binding: ActivityInformationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.informationTextviewHomepage.setOnClickListener {
            val youtubeUrl = "https://youtube.com/shorts/SXHMnicI6Pg?si=wxpcu050PM4cCmGv"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
            startActivity(intent)
        }
    }
}