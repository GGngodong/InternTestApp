package com.ikkoy.interntestapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.ikkoy.interntestapp.UserPreference
import com.ikkoy.interntestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)

        val name = binding.edtName.text
        val palindrome = binding.palindromeCheck.text

        binding.apply {
            btnCheck.setOnClickListener {
                if (palindromeChecker(palindrome)) {
                    Toast.makeText(this@MainActivity, "Palindrome", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Not Palindrome", Toast.LENGTH_SHORT).show()
                }
            }

            btnNext.setOnClickListener {
                Intent(this@MainActivity, SelectedActivity::class.java).also {
                    userPreference.setName(name.toString())
                    startActivity(it)
                }
            }
        }
    }

    private fun palindromeChecker(name: Editable): Boolean {
        val stringBuilder = StringBuilder(name.toString())
        val reserveName = stringBuilder.reverse().toString()
        return name.toString().replace("\\s".toRegex(), "").equals(reserveName.replace("\\s".toRegex(), ""), ignoreCase = true)
    }
}
