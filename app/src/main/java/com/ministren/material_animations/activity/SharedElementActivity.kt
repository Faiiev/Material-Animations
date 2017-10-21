package com.ministren.material_animations.activity

import android.databinding.DataBindingUtil
import android.transition.ChangeBounds
import android.transition.Slide
import android.view.Gravity

import com.ministren.material_animations.R
import com.ministren.material_animations.databinding.ActivitySharedElementBinding
import com.ministren.material_animations.fragment.SharedElementFragment1
import com.ministren.material_animations.model.Sample

import kotlinx.android.synthetic.main.activity_shared_element.*

class SharedElementActivity : BaseDetailActivity() {

    override fun setupToolbar() {
        super.setupToolbar()
        setSupportActionBar(toolbar_shared)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun bindData() {
        val binding: ActivitySharedElementBinding = DataBindingUtil.setContentView(this, R.layout.activity_shared_element)
        mSample = intent.extras.getSerializable(EXTRA_SAMPLE) as Sample
        binding.sharedSample = mSample
    }

    override fun setupWindowAnimations() {
        // We are not interested in defining a new Enter Transition. Instead we change default transition duration
        window.enterTransition.duration = resources.getInteger(R.integer.anim_duration_long).toLong()
    }

    override fun setupLayout() {
        // Transition for fragment1
        val slide = Slide(Gravity.START)
        slide.duration = resources.getInteger(R.integer.anim_duration_long).toLong()
        // Create fragment and define some of it transitions
        val sharedElementFragment1 = SharedElementFragment1.newInstance(mSample)
        sharedElementFragment1.reenterTransition = slide
        sharedElementFragment1.exitTransition = slide
        sharedElementFragment1.sharedElementEnterTransition = ChangeBounds()

        supportFragmentManager.beginTransaction()
                .replace(R.id.sample_content, sharedElementFragment1)
                .commit()
    }

}
