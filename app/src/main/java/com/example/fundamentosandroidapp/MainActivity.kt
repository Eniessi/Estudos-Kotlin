package com.example.fundamentosandroidapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.example.fundamentosandroidapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvMainContainer) as? NavHostFragment
        navHostFragment?.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: DiceViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    // Update UI elements
                    // id de drawable de dado
                    uiState.rolledDice1ImgRes?.let {imgRes -> binding.ivRolledDice1.setImageResource(imgRes)}
                }
            }
        }

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnRollDice.setOnClickListener {
            viewModel.rollDice()
        }

        binding.btnNextFragment.setOnClickListener {
            navController?.currentDestination?.id.let {
                when (it) {
                    R.id.firstFragment -> {
                        navController?.navigate(R.id.action_firstFragment_to_secondFragment,
                            bundleOf("first_arg" to arrayOf("1","2","3")))
                        binding.btnNextFragment.text =
                            getString(R.string.voltar_para_o_primeiro_fragment)
                    }

                    R.id.secondFragment -> {
                        navController?.popBackStack()
                        binding.btnNextFragment.text = getString(R.string.ir_para_proxima_tela)
                    }
                }
            }
        }
    }
}