package com.ministren.material_animations.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Slide
import android.view.Gravity

import com.ministren.material_animations.R
import com.ministren.material_animations.model.Sample
import com.ministren.material_animations.adapter.SamplesRecyclerAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    private lateinit var mSamples: List<Sample>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupWindowAnimations()
        setupSamples()
        setupLayout()
    }

    private fun setupWindowAnimations() {
        val slideTransition = Slide()
        slideTransition.slideEdge = Gravity.START
        slideTransition.duration = resources.getInteger(R.integer.anim_duration_long).toLong()
        window.reenterTransition = slideTransition
        window.exitTransition = slideTransition
    }

    private fun setupSamples() {
        mSamples = listOf(
                Sample(ContextCompat.getColor(this, R.color.sample_red), "Transitions"),
                Sample(ContextCompat.getColor(this, R.color.sample_blue), "Shared Elements"),
                Sample(ContextCompat.getColor(this, R.color.sample_green), "View Animations"),
                Sample(ContextCompat.getColor(this, R.color.sample_yellow), "Circular Reveal Animation")
        )
    }

    private fun setupLayout() {
        rv_samples.setHasFixedSize(true)
        rv_samples.layoutManager = LinearLayoutManager(this)
        rv_samples.adapter = SamplesRecyclerAdapter(this, mSamples)
    }

}
