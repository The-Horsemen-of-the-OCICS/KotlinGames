package com.ocics.kotlingames.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ocics.kotlingames.MainActivity
import com.ocics.kotlingames.R
import com.ocics.kotlingames.databinding.FragmentCountResultBinding
import com.ocics.kotlingames.viewmodel.ItemViewModel
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CountResultFragment : Fragment() {

    private lateinit var _binding: FragmentCountResultBinding
    private lateinit var mViewModel: ItemViewModel
    private var score = 0.0

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
        binding.buttonOk.setOnClickListener {
            calculate()
        }
        binding.buttonBack.setOnClickListener {
            val intent = Intent()
            intent.setClass(context!!, MainActivity::class.java)
            startActivity(intent)
        }

        binding.textviewHowMany.text = getString(R.string.how_many_animals, mViewModel.option)
    }

    private fun calculate() {
        binding.textviewHowMany.visibility = View.INVISIBLE
        binding.editTextNumber.visibility = View.INVISIBLE
        binding.buttonOk.visibility = View.INVISIBLE
        binding.buttonBack.visibility = View.VISIBLE

        val reported = Integer.parseInt(binding.editTextNumber.text.toString())
        val total = mViewModel.total
        score = if (reported > total) {
            (total - (reported - total)).toDouble() / total
        } else {
            reported.toDouble() / total
        }

        if (score < 0)
            score = 0.0

        val form = DecimalFormat("0.00")
        binding.textview.visibility = View.VISIBLE
        binding.textViewScore.text = form.format(score)
        mViewModel.score = form.format(score).toDouble()
    }
}