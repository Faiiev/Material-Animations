package com.ministren.material_animations.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintLayout
import android.transition.Fade
import android.transition.TransitionManager

import com.ministren.material_animations.R
import com.ministren.material_animations.databinding.ActivityAnimations1Binding
import com.ministren.material_animations.model.Sample

import kotlinx.android.synthetic.main.activity_animations1.*

class AnimationsActivity1 : BaseDetailActivity() {

    private var mSizeChanged = false
    private var mPositionChanged = false
    private var mSavedSize = 0

    override fun bindData() {
        val binding: ActivityAnimations1Binding = DataBindingUtil.setContentView(this, R.layout.activity_animations1)
        mSample = intent.extras.getSerializable(EXTRA_SAMPLE) as Sample
        binding.animations1Sample = mSample
    }

    override fun setupWindowAnimations() {
        window.reenterTransition = Fade()
    }

    override fun setupLayout() {
        mSavedSize = image_circle_green.layoutParams.width

        btn_change_size.setOnClickListener { changeSize() }
        btn_change_position.setOnClickListener { changePosition() }
        btn_next.setOnClickListener {
            val intent = Intent(this, AnimationsActivity2::class.java)
            intent.putExtra(EXTRA_SAMPLE, mSample)
            transitionTo(intent)
        }
    }

    private fun changeSize() {
        TransitionManager.beginDelayedTransition(animations1_root_view)
        val params = image_circle_green.layoutParams
        params.width = if (mSizeChanged) mSavedSize else 200
        mSizeChanged = !mSizeChanged
        image_circle_green.layoutParams = params
    }

    private fun changePosition() {
        TransitionManager.beginDelayedTransition(animations1_root_view)
        val params = image_circle_green.layoutParams as ConstraintLayout.LayoutParams
        params.endToEnd = if (mPositionChanged) ConstraintLayout.LayoutParams.PARENT_ID else ConstraintLayout.LayoutParams.UNSET
        mPositionChanged = !mPositionChanged
        image_circle_green.layoutParams = params
    }

}
