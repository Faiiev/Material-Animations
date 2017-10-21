package com.ministren.material_animations.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity

import com.ministren.material_animations.helper.TransitionHelper
import com.ministren.material_animations.model.Sample

import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseDetailActivity : AppCompatActivity() {

    protected lateinit var mSample: Sample

    companion object {
        val EXTRA_SAMPLE = "sample"
        val EXTRA_TRANSITION_TYPE = "type"
        val TRANSITION_TYPE_PROGRAMMATICALLY = 0
        val TRANSITION_TYPE_XML = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindData()
        setupToolbar()
        setupWindowAnimations()
        setupLayout()
    }

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return true
    }

    abstract fun bindData()
    abstract fun setupWindowAnimations()
    abstract fun setupLayout()

    open fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = mSample.name
    }

    fun transitionTo(intent: Intent) {
        val pairs = TransitionHelper.createSafeTransitionParticipants(this, true)
        val transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairs)
        startActivity(intent, transitionActivityOptions.toBundle())
    }

}
