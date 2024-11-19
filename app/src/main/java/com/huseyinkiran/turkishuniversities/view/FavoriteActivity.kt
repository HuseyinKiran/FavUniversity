package com.huseyinkiran.turkishuniversities.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.huseyinkiran.turkishuniversities.adapter.UniversityAdapter
import com.huseyinkiran.turkishuniversities.data.UniversityDatabase
import com.huseyinkiran.turkishuniversities.databinding.ActivityFavoriteBinding
import com.huseyinkiran.turkishuniversities.repository.UniversityRepository
import com.huseyinkiran.turkishuniversities.viewmodel.ProvinceVM
import com.huseyinkiran.turkishuniversities.viewmodel.ProvinceVMFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UniversityAdapter
    private lateinit var viewModel: ProvinceVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToMainActivity()

        val repository = UniversityRepository(UniversityDatabase(this))
        val viewModelProviderFactory = ProvinceVMFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[ProvinceVM::class.java]

        binding.favoritesRv.layoutManager = LinearLayoutManager(this)
        adapter = UniversityAdapter(emptyList(), viewModel,this, onFavoriteClick = { university ->
            viewModel.updateFavorites(university)
        })
        binding.favoritesRv.adapter = adapter

        viewModel.getFavoriteUniversities().observe(this, Observer { favoriteUniversities ->
            adapter.updateData(favoriteUniversities)
        })


    }

    private fun navigateToMainActivity() {
        binding.goToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        onBackPressedDispatcher.addCallback(this,object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

}