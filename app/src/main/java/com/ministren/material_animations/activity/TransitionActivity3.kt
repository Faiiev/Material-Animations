package com.ministren.material_animations.activity

import android.databinding.DataBindingUtil
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.Gravity

import com.ministren.material_animations.R
import com.ministren.material_animations.databinding.ActivityTransition3Binding
import com.ministren.material_animations.model.Sample

import kotlinx.android.synthetic.main.activity_transition3.*

class TransitionActivity3 : BaseDetailActivity() {

    override fun bindData() {
        val binding: ActivityTransition3Binding = DataBindingUtil.setContentView(this, R.layout.activity_transition3)
        mSample = intent.extras.getSerializable(EXTRA_SAMPLE) as Sample
        binding.transition3Sample = mSample
    }

    override fun setupWindowAnimations() {
        val transitionType = intent.extras.getInt(EXTRA_TRANSITION_TYPE)
        val transition = when (transitionType) {
            TRANSITION_TYPE_PROGRAMMATICALLY -> buildEnterTransition()
            TRANSITION_TYPE_XML -> TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom)
            else -> null
        }
        window.enterTransition = transition
    }

    override fun setupLayout() {
        btn_exit.setOnClickListener { finishAfterTransition() }
    }

    private fun buildEnterTransition(): Transition {
        val transition = Slide()
        transition.duration = resources.getInteger(R.integer.anim_duration_long).toLong()
        transition.slideEdge = Gravity.END
        return transition
    }

}
