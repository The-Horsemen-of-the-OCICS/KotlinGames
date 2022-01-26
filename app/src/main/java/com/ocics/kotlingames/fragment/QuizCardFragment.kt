package com.ocics.kotlingames.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ocics.kotlingames.databinding.FragmentQuizCardBinding
import com.ocics.kotlingames.viewmodel.QuizViewModel
import android.widget.RadioButton
import com.ocics.kotlingames.R
import java.util.*

class QuizCardFragment(private val position: Int) : Fragment() {

    private val mQuizViewModel: QuizViewModel by activityViewModels()
    private lateinit var mBinding: FragmentQuizCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentQuizCardBinding.inflate(inflater, container, false)
        mBinding.apply { viewmodel = mQuizViewModel }

        mBinding.quizRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val index = mBinding.quizRadioGroup.indexOfChild(mBinding.quizRadioGroup.findViewById(i))
            mQuizViewModel.selections[position] = index
        }

        initCard()

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mBinding.root.requestLayout()
    }

    fun initCard() {
        val card = mQuizViewModel.cards[position]
        mBinding.quizQuestionText.text = "Question ${position+1}: ${card.question}"

        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        mBinding.cardBg.setBackgroundColor(color)

        for(c in card.choice) {
            val newRadioButton = layoutInflater.inflate(R.layout.quiz_radio_button, null) as RadioButton
            newRadioButton.id = View.generateViewId()
            newRadioButton.text = c
            mBinding.quizRadioGroup.addView(newRadioButton)
        }

        if (position == mQuizViewModel.cards.size - 1) {
            mBinding.quizButtonText.text = "Submit"
        }


    }

}