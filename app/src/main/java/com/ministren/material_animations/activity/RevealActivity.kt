package com.ministren.material_animations.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator

import com.ministren.material_animations.R
import com.ministren.material_animations.databinding.ActivityRevealBinding
import com.ministren.material_animations.model.Sample

import kotlinx.android.synthetic.main.activity_reveal.*

class RevealActivity : BaseDetailActivity() {

    private val DELAY = 100L

    private lateinit var mInterpolator: Interpolator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in)
    }

    override fun setupToolbar() {
        setSupportActionBar(toolbar_reveal)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun bindData() {
        val binding: ActivityRevealBinding = DataBindingUtil.setContentView(this, R.layout.activity_reveal)
        mSample = intent.extras.getSerializable(EXTRA_SAMPLE) as Sample
        binding.revealSample = mSample
    }

    override fun setupWindowAnimations() {
        setupEnterAnimations()
        setupExitAnimations()
    }

    override fun setupLayout() {
        image_circle_red.setOnClickListener { revealRed() }
        image_circle_blue.setOnClickListener { revealBlue() }
        image_circle_green.setOnClickListener { revealGreen() }
        image_circle_yellow.setOnTouchListener { v, event ->
            if (event?.action == MotionEvent.ACTION_DOWN && v == image_circle_yellow) {
                revealYellow(event.rawX, event.rawY - image_circle_yellow.height)
            }
            false
        }
    }

    private fun revealRed() {
        val savedConstraints = ConstraintSet()
        savedConstraints.clone(reveal_buttons_view)

        val transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion)
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                animateRevealColor(reveal_root_view, R.color.sample_red)
                tv_description.setText(R.string.reveal_body1)
                savedConstraints.applyTo(reveal_buttons_view)
            }

            override fun onTransitionResume(transition: Transition?) {}
            override fun onTransitionPause(transition: Transition?) {}
            override fun onTransitionCancel(transition: Transition?) {}
            override fun onTransitionStart(transition: Transition?) {}
        })
        TransitionManager.beginDelayedTransition(reveal_root_view, transition)

        val constraintSet = ConstraintSet()
        constraintSet.clone(savedConstraints)
        constraintSet.centerVertically(R.id.image_circle_red, R.id.reveal_buttons_view)
        constraintSet.applyTo(reveal_buttons_view)
    }

    private fun revealBlue() {
        animateButtonsOut()
        val anim = animateRevealColorFromCoordinates(reveal_root_view, R.color.sample_blue, reveal_root_view.width / 2, 0)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                animateButtonsIn()
            }
        })
        tv_description.setText(R.string.reveal_body2)
    }

    private fun revealGreen() {
        animateRevealColor(reveal_root_view, R.color.sample_green)
        tv_description.setText(R.string.reveal_body3)
    }

    private fun revealYellow(x: Float, y: Float) {
        animateRevealColorFromCoordinates(reveal_root_view, R.color.sample_yellow, x.toInt(), y.toInt())
        tv_description.setText(R.string.reveal_body4)
    }

    private fun setupEnterAnimations() {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion)
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                // Removing listener here is very important because shared element transition is executed again backwards on exit.
                // If we don't remove the listener this code will be triggered again.
                transition?.removeListener(this)
                animateRevealShow(toolbar_reveal)
                animateButtonsIn()
            }

            override fun onTransitionResume(transition: Transition?) {}
            override fun onTransitionPause(transition: Transition?) {}
            override fun onTransitionCancel(transition: Transition?) {}
            override fun onTransitionStart(transition: Transition?) {}
        })
        window.sharedElementEnterTransition = transition
    }

    private fun animateRevealShow(view: View) {
        val cx = view.width / 2
        val cy = view.height / 2
        val endRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, endRadius)
        anim.duration = resources.getInteger(R.integer.anim_duration_long).toLong()
        anim.interpolator = AccelerateInterpolator()
        view.visibility = View.VISIBLE
        anim.start()
    }

    private fun animateButtonsIn() {
        for (i in 0 until reveal_buttons_view.childCount) {
            val child = reveal_buttons_view.getChildAt(i)
            child.animate()
                    .setStartDelay(100 + i * DELAY)
                    .setInterpolator(mInterpolator)
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
        }
    }

    private fun setupExitAnimations() {
        val transition = Fade()
        transition.duration = resources.getInteger(R.integer.anim_duration_medium).toLong()
        transition.startDelay = resources.getInteger(R.integer.anim_duration_medium).toLong()
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition?) {
                transition?.removeListener(this)
                animateButtonsOut()
                animateRevealHide(reveal_root_view)
            }

            override fun onTransitionEnd(transition: Transition?) {}
            override fun onTransitionResume(transition: Transition?) {}
            override fun onTransitionPause(transition: Transition?) {}
            override fun onTransitionCancel(transition: Transition?) {}
        })
        window.returnTransition = transition
    }

    private fun animateButtonsOut() {
        for (i in 0 until reveal_buttons_view.childCount) {
            val child = reveal_buttons_view.getChildAt(i)
            child.animate()
                    .setStartDelay(i.toLong())
                    .setInterpolator(mInterpolator)
                    .alpha(0f)
                    .scaleX(0f)
                    .scaleY(0f)
        }
    }

    private fun animateRevealHide(view: View) {
        val cx = view.width / 2
        val cy = view.height / 2
        val startRadius = view.width.toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, 0f)
        anim.duration = resources.getInteger(R.integer.anim_duration_medium).toLong()
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                view.visibility = View.INVISIBLE
            }
        })
        anim.start()
    }

    private fun animateRevealColor(viewGroup: ViewGroup, @ColorRes color: Int) {
        val cx = viewGroup.width / 2
        val cy = viewGroup.height / 2
        animateRevealColorFromCoordinates(viewGroup, color, cx, cy)
    }

    private fun animateRevealColorFromCoordinates(viewGroup: ViewGroup, @ColorRes color: Int, x: Int, y: Int): Animator {
        val endRadius = Math.hypot(x.toDouble(), y.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(viewGroup, x, y, 0f, endRadius)
        anim.duration = resources.getInteger(R.integer.anim_duration_long).toLong()
        anim.interpolator = AccelerateDecelerateInterpolator()
        viewGroup.setBackgroundColor(ContextCompat.getColor(this, color))
        anim.start()
        return anim
    }

}
