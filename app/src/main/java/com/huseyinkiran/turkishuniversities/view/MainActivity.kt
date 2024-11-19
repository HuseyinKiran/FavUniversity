package com.huseyinkiran.turkishuniversities.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.huseyinkiran.turkishuniversities.adapter.ProvinceAdapter
import com.huseyinkiran.turkishuniversities.data.UniversityDatabase
import com.huseyinkiran.turkishuniversities.databinding.ActivityMainBinding
import com.huseyinkiran.turkishuniversities.repository.UniversityRepository
import com.huseyinkiran.turkishuniversities.viewmodel.ProvinceVM
import com.huseyinkiran.turkishuniversities.viewmodel.ProvinceVMFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var provinceVM: ProvinceVM
    private lateinit var provinceAdapter: ProvinceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToFavoriteActivity()

        val repository = UniversityRepository(UniversityDatabase(this))
        val viewModelProviderFactory = ProvinceVMFactory(application, repository)
        provinceVM = ViewModelProvider(this, viewModelProviderFactory)[ProvinceVM::class.java]

        
        binding.cityRv.layoutManager = LinearLayoutManager(this)
        provinceAdapter = ProvinceAdapter(listOf(), provinceVM, this) { university ->
            provinceVM.updateFavorites(university)
        }
        binding.cityRv.adapter = provinceAdapter

        binding.progressBar.visibility = View.VISIBLE

        if (!isNetworkAvailable()) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(5000)
                binding.progressBar.visibility = View.GONE
                binding.txtError.visibility = View.VISIBLE
            }
        }

        setupViewModel()

    }

    private fun navigateToFavoriteActivity() {
        binding.goToFavorite.setOnClickListener {
            val intent = Intent(this,FavoriteActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupViewModel() {

        provinceVM.provinceList.observe(this, Observer { provinces ->
            if (provinces.isEmpty()) {
                binding.txtError.visibility = View.VISIBLE
            } else {
                provinceAdapter.updateProvinces(provinces)
                binding.progressBar.visibility = View.GONE
            }
        })

        provinceVM.errorMessage.observe(this, Observer { isError ->
            if (isError) {
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.GONE
            }
        })

        provinceVM.loadProvinces()

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}