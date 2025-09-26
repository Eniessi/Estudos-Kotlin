package com.example.fundamentosandroidapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.fundamentosandroidapp.databinding.FragmentFirstBinding
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private val viewModel: DiceViewModel by activityViewModels()
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                // Update UI elements
                // id de drawable de dado
                uiState.rolledDice2ImgRes?.let {imgRes -> binding.ivRolledDice2.setImageResource(imgRes)}
            }
        }
    }
}