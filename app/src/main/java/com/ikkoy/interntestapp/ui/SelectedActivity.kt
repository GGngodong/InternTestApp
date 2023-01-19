package com.ikkoy.interntestapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ikkoy.interntestapp.UserPreference
import com.ikkoy.interntestapp.databinding.ActivitySelectedBinding

class SelectedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectedBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreference = UserPreference(this)

        val name = userPreference.preference.getString("name", "")
        val selectedName = userPreference.preference.getString("selected_name", "")

        binding.apply {
            tvName.text = name
            selectUserName.text = selectedName
            btnChooseUser.setOnClickListener {
                Intent(this@SelectedActivity, ListUser::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}