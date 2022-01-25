package com.ocics.kotlingames

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ocics.kotlingames.databinding.ActivityQuizBinding
import com.ocics.kotlingames.fragment.QuizCardFragment
import com.ocics.kotlingames.viewmodel.QuizViewModel
import androidx.viewpager.widget.PagerAdapter
import com.ocics.kotlingames.model.QuizCard

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class QuizActivity : FragmentActivity() {
    private val TAG = "QuizActivity"

    private lateinit var viewPager: ViewPager2
    private lateinit var mBinding: ActivityQuizBinding
    private val mQuizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mQuizViewModel.loadDefaultQuestions()

        viewPager = mBinding.quizCardPager
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.isUserInputEnabled = false
        viewPager.setPageTransformer(ZoomOutPageTransformer())

        mBinding.addChoiceButton.setOnClickListener {
            if (mBinding.quizChoiceEditText.text == null || mBinding.quizChoiceEditText.text.isEmpty()) {

            } else {
                val newRadioButton = layoutInflater.inflate(R.layout.quiz_radio_button, null) as RadioButton
                newRadioButton.id = View.generateViewId()
                newRadioButton.text = mBinding.quizChoiceEditText.text
                mBinding.quizEditRadioGroup.addView(newRadioButton)
            }
        }

        mBinding.submitQuestionButton.setOnClickListener {
            if (mBinding.quizChoiceEditText.text == null || mBinding.quizChoiceEditText.text.isEmpty() ||
                mBinding.quizQuestionEditText.text == null ||  mBinding.quizQuestionEditText.text.isEmpty()) {
                Log.d(TAG, "Edit text is null")
            } else if (mBinding.quizEditRadioGroup.size < 2) {
                Log.d(TAG, "Insufficient choice")
            } else if (mBinding.quizEditRadioGroup.checkedRadioButtonId == -1) {
                Log.d(TAG, "Edit text is null")
            } else {
                var choiceList = arrayListOf<String>()
                for (r in mBinding.quizEditRadioGroup.children) {
                    choiceList.add((r as RadioButton).text.toString())
                }

                val index = mBinding.quizEditRadioGroup.indexOfChild(mBinding.quizEditRadioGroup.findViewById(mBinding.quizEditRadioGroup.checkedRadioButtonId))

                mQuizViewModel.addCard(
                    QuizCard(
                        mBinding.quizQuestionEditText.text.toString(),
                        choiceList, index
                    ))

                mQuizViewModel.shuffleCards()
                mBinding.quizEditView.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            prevPager()
        }
    }

    fun addQuestion(v: View) {
        mBinding.quizCardPager.visibility = View.GONE
        mBinding.quizEditView.visibility = View.VISIBLE
    }


    fun startGame(v: View) {
        mBinding.quizEditView.visibility = View.GONE
        mBinding.quizCardPager.visibility = View.GONE

        mQuizViewModel.shuffleCards()
        viewPager.adapter = ScreenSlidePagerAdapter(this)

        mBinding.quizCardPager.visibility = View.VISIBLE
        viewPager.currentItem = 0
    }

    fun prevPager() {
        val currPos: Int = viewPager.currentItem
        viewPager.currentItem = currPos - 1
    }

    fun nextPager(v: View) {
        val currPos: Int = viewPager.currentItem
        if ((currPos + 1) != viewPager.adapter?.itemCount) {
            viewPager.currentItem = currPos + 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = mQuizViewModel.cards.size

        override fun createFragment(position: Int): Fragment {
            val curCard = QuizCardFragment(position)
            return curCard
        }
    }

    class ZoomOutPageTransformer : ViewPager2.PageTransformer {

        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 1 -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        // Fade the page relative to its size.
                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }

}