package com.ministren.material_animations.activity

import android.databinding.DataBindingUtil
import android.transition.Explode
import android.transition.Transition
import android.transition.TransitionInflater

import com.ministren.material_animations.R
import com.ministren.material_animations.databinding.ActivityTransition2Binding
import com.ministren.material_animations.model.Sample

import kotlinx.android.synthetic.main.activity_transition2.*

class TransitionActivity2 : BaseDetailActivity() {

    override fun bindData() {
        val binding: ActivityTransition2Binding = DataBindingUtil.setContentView(this, R.layout.activity_transition2)
        mSample = intent.extras.getSerializable(EXTRA_SAMPLE) as Sample
        binding.transition2Sample = mSample
    }

    override fun setupWindowAnimations() {
        val transitionType = intent.extras.getInt(EXTRA_TRANSITION_TYPE)
        val transition = when (transitionType) {
            TRANSITION_TYPE_PROGRAMMATICALLY -> buildEnterTransition()
            TRANSITION_TYPE_XML -> TransitionInflater.from(this).inflateTransition(R.transition.explode)
            else -> null
        }
        window.enterTransition = transition
    }

    override fun setupLayout() {
        btn_exit.setOnClickListener { finishAfterTransition() }
    }

    private fun buildEnterTransition(): Transition {
        val transition = Explode()
        transition.duration = resources.getInteger(R.integer.anim_duration_long).toLong()
        return transition
    }

}
