package com.ocics.kotlingames.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ocics.kotlingames.R
import com.ocics.kotlingames.databinding.FragmentCountBinding
import com.ocics.kotlingames.viewmodel.ItemViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CountFragment : Fragment() {
    private lateinit var _binding: FragmentCountBinding
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mViewModel: ItemViewModel
    // The actual number of animals
    private var total = 0
    private var timeLeft = 60000
    // The time(seconds) until images disappear
    private var showTime = 0
    // The position of the animal image
    private var pos = 0
    private var option = "Sheep"
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
        binding.buttonStart.setOnClickListener {
            startCount()
        }
    }

    // Start showing animal images with a timer
    private fun startCount() {
        if (binding.radioSheep.isChecked) {
            option = "Sheep"
        } else if (binding.radioCat.isChecked) {
            option = "Cats"
        }
        mViewModel.option = option

        binding.buttonStart.visibility = View.INVISIBLE
        binding.textviewFirst.visibility = View.INVISIBLE
        binding.radioGroup.visibility = View.INVISIBLE
        binding.timer.visibility = View.VISIBLE

        updateAnimal()

        countDownTimer = object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt()
                updateTimer()
                when {
                    timeLeft == 0 -> {
                        showTime = 0
                        pos = 0
                        hideAnimal()
                        mViewModel
                    }
                    showTime == 0 -> {
                        hideAnimal()
                        // Random time 1-3 seconds
                        showTime = (1..3).random()
                        total += 1
                        pos = 0
                    }
                    pos == 0 -> {
                        // Randomly select a position
                        pos = (1..5).random()
                        showTime -= 1
                        when (pos) {
                            1 -> _binding.animalImage1.visibility = View.VISIBLE
                            2 -> _binding.animalImage2.visibility = View.VISIBLE
                            3 -> _binding.animalImage3.visibility = View.VISIBLE
                            4 -> _binding.animalImage4.visibility = View.VISIBLE
                            5 -> _binding.animalImage5.visibility = View.VISIBLE
                        }
                    }
                    else -> {
                        showTime -= 1
                    }
                }
            }

            override fun onFinish() {
                mViewModel.total = total
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }.start()


    }

    // Change animal images to sheep or cat
    private fun updateAnimal() {
        if (option == "Sheep") {
            binding.animalImage1.setImageResource(R.drawable.wooloo)
            binding.animalImage2.setImageResource(R.drawable.wooloo)
            binding.animalImage3.setImageResource(R.drawable.wooloo)
            binding.animalImage4.setImageResource(R.drawable.wooloo)
            binding.animalImage5.setImageResource(R.drawable.wooloo)
        } else if (option == "Cats"){
            binding.animalImage1.setImageResource(R.drawable.litten)
            binding.animalImage2.setImageResource(R.drawable.litten)
            binding.animalImage3.setImageResource(R.drawable.litten)
            binding.animalImage4.setImageResource(R.drawable.litten)
            binding.animalImage5.setImageResource(R.drawable.litten)
        }
    }

    // Hide animal images
    private fun hideAnimal() {
        binding.animalImage1.visibility = View.INVISIBLE
        binding.animalImage2.visibility = View.INVISIBLE
        binding.animalImage3.visibility = View.INVISIBLE
        binding.animalImage4.visibility = View.INVISIBLE
        binding.animalImage5.visibility = View.INVISIBLE
    }

    fun updateTimer() {
        binding.timer.text = (timeLeft / 1000).toString()
    }
}