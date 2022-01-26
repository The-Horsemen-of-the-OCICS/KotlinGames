package com.ocics.kotlingames.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.ocics.kotlingames.R
import com.ocics.kotlingames.databinding.FragmentQuizResultBinding
import com.ocics.kotlingames.viewmodel.QuizViewModel

class QuizResultFragment : Fragment() {

    private val mQuizViewModel: QuizViewModel by activityViewModels()
    private lateinit var mBinding: FragmentQuizResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentQuizResultBinding.inflate(inflater, container, false)
        mBinding.apply { viewmodel = mQuizViewModel }

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        loadResult()
        mBinding.root.requestLayout()
    }
    

    fun loadResult() {
        val score = mQuizViewModel.getScore()

        mBinding.scoreText.text = "Score: " + score.toString()

        for ((index, card) in mQuizViewModel.cards.withIndex()) {

            val newResultCard = layoutInflater.inflate(R.layout.quiz_result_card, null)
            newResultCard.id = View.generateViewId()

            val params = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0,0,0,20)
            newResultCard.layoutParams = params

            newResultCard.findViewById<TextView>(R.id.result_question).text = "Question ${index+1}: ${card.question}"
            newResultCard.findViewById<TextView>(R.id.result_correct_answer).text = card.choice[card.answer]
            newResultCard.findViewById<TextView>(R.id.result_selection).text = card.choice[mQuizViewModel.selections[index]]

            if (card.answer == mQuizViewModel.selections[index]) {
                newResultCard.setBackgroundResource(R.color.light_green)
                newResultCard.findViewById<TextView>(R.id.your_answer_text).visibility = View.GONE
                newResultCard.findViewById<TextView>(R.id.result_selection).visibility = View.GONE
            } else {
                newResultCard.setBackgroundResource(R.color.light_pink)
            }
            mBinding.resultHolder.addView(newResultCard)
        }
        saveScore(score)
    }

    fun saveScore(s: Int) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putLong(getString(R.string.quiz_score), s.toLong())
            apply()
        }
    }
}