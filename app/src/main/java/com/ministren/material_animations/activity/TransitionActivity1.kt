package com.ministren.material_animations.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.transition.Fade
import android.transition.Slide
import android.transition.Transition

import com.ministren.material_animations.R
import com.ministren.material_animations.databinding.ActivityTransition1Binding
import com.ministren.material_animations.model.Sample

import kotlinx.android.synthetic.main.activity_transition1.*

class TransitionActivity1 : BaseDetailActivity() {

    override fun bindData() {
        val binding: ActivityTransition1Binding = DataBindingUtil.setContentView(this, R.layout.activity_transition1)
        mSample = intent.extras.getSerializable(EXTRA_SAMPLE) as Sample
        binding.transition1Sample = mSample
    }

    override fun setupWindowAnimations() {
        val enterTransition = buildEnterTransition()
        window.enterTransition = enterTransition
    }

    override fun setupLayout() {
        btn_explode_code.setOnClickListener {
            val intent = Intent(this, TransitionActivity2::class.java)
            intent.putExtra(EXTRA_SAMPLE, mSample)
            intent.putExtra(EXTRA_TRANSITION_TYPE, TRANSITION_TYPE_PROGRAMMATICALLY)
            transitionTo(intent)
        }
        btn_explode_xml.setOnClickListener {
            val intent = Intent(this, TransitionActivity2::class.java)
            intent.putExtra(EXTRA_SAMPLE, mSample)
            intent.putExtra(EXTRA_TRANSITION_TYPE, TRANSITION_TYPE_XML)
            transitionTo(intent)
        }
        btn_slide_code.setOnClickListener {
            val intent = Intent(this, TransitionActivity3::class.java)
            intent.putExtra(EXTRA_SAMPLE, mSample)
            intent.putExtra(EXTRA_TRANSITION_TYPE, TRANSITION_TYPE_PROGRAMMATICALLY)
            transitionTo(intent)
        }
        btn_slide_xml.setOnClickListener {
            val intent = Intent(this, TransitionActivity3::class.java)
            intent.putExtra(EXTRA_SAMPLE, mSample)
            intent.putExtra(EXTRA_TRANSITION_TYPE, TRANSITION_TYPE_XML)
            transitionTo(intent)
        }
        btn_exit.setOnClickListener {
            /**
             * If no return transition is defined Android will use reversed enter transition
             * In this case, return transition will be a reversed Slide (defined in buildEnterTransition)
             */
            finishAfterTransition()
        }
        btn_exit_override.setOnClickListener {
            val returnTransition = buildReturnTransition()
            window.returnTransition = returnTransition
            finishAfterTransition()
        }
    }

    private fun buildEnterTransition(): Transition {
        val transition = Fade()
        transition.duration = resources.getInteger(R.integer.anim_duration_long).toLong()
        // This view will not be affected by enter transition animation
        transition.excludeTarget(R.id.image_circle_red, true)
        return transition
    }

    private fun buildReturnTransition(): Transition {
        val transition = Slide()
        transition.duration = resources.getInteger(R.integer.anim_duration_long).toLong()
        return transition
    }

}
