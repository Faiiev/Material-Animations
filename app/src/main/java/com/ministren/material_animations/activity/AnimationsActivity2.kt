package com.ministren.material_animations.activity

import android.databinding.DataBindingUtil
import android.transition.*

import com.ministren.material_animations.R
import com.ministren.material_animations.databinding.ActivityAnimations2Binding
import com.ministren.material_animations.model.Sample

import kotlinx.android.synthetic.main.activity_animations2.*

class AnimationsActivity2 : BaseDetailActivity() {

    private lateinit var mScene0: Scene
    private lateinit var mScene1: Scene
    private lateinit var mScene2: Scene
    private lateinit var mScene3: Scene
    private lateinit var mScene4: Scene

    override fun bindData() {
        val binding: ActivityAnimations2Binding = DataBindingUtil.setContentView(this, R.layout.activity_animations2)
        mSample = intent.extras.getSerializable(EXTRA_SAMPLE) as Sample
        binding.animations2Sample = mSample
    }

    override fun setupWindowAnimations() {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom)
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                window.enterTransition.removeListener(this)
                TransitionManager.go(mScene0)
            }

            override fun onTransitionResume(transition: Transition?) {}
            override fun onTransitionPause(transition: Transition?) {}
            override fun onTransitionCancel(transition: Transition?) {}
            override fun onTransitionStart(transition: Transition?) {}
        })
        window.enterTransition = transition
    }

    override fun setupLayout() {
        mScene0 = Scene.getSceneForLayout(scene_root, R.layout.animations_scene0, this)
        mScene1 = Scene.getSceneForLayout(scene_root, R.layout.animations_scene1, this)
        mScene2 = Scene.getSceneForLayout(scene_root, R.layout.animations_scene2, this)
        mScene3 = Scene.getSceneForLayout(scene_root, R.layout.animations_scene3, this)
        mScene4 = Scene.getSceneForLayout(scene_root, R.layout.animations_scene4, this)

        btn_scene_1.setOnClickListener { TransitionManager.go(mScene1, ChangeBounds()) }
        btn_scene_2.setOnClickListener {
            val transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds)
            TransitionManager.go(mScene2, transition)
        }
        btn_scene_3.setOnClickListener {
            val transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds_sequential)
            TransitionManager.go(mScene3, transition)
        }
        btn_scene_4.setOnClickListener {
            val transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds_sequential_with_interpolators)
            TransitionManager.go(mScene4, transition)
        }
    }

}
