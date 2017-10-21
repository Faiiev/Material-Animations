package com.ministren.material_animations.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.ChangeBounds
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.ministren.material_animations.R
import com.ministren.material_animations.databinding.FragmentSharedElement1Binding
import com.ministren.material_animations.model.Sample

class SharedElementFragment1 : Fragment() {

    companion object {
        val EXTRA_SAMPLE = "sample"

        fun newInstance(sample: Sample): SharedElementFragment1 {
            val fragment = SharedElementFragment1()
            val args = Bundle()
            args.putSerializable(EXTRA_SAMPLE, sample)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mSample: Sample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSample = arguments.getSerializable(EXTRA_SAMPLE) as Sample
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSharedElement1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shared_element1, container, false)
        val view = binding.root
        binding.sample = mSample
        binding.btnNextOverlapFalse.setOnClickListener { addNextFragment(binding.imageCircleBlue, false) }
        binding.btnNextOverlapTrue.setOnClickListener { addNextFragment(binding.imageCircleBlue, true) }
        return view
    }

    private fun addNextFragment(imageCircle: ImageView?, overlap: Boolean) {
        val sharedElementFragment2 = SharedElementFragment2.newInstance(mSample)

        val slide = Slide(Gravity.END)
        slide.duration = resources.getInteger(R.integer.anim_duration_medium).toLong()

        val changeBoundsTransition = ChangeBounds()
        changeBoundsTransition.duration = resources.getInteger(R.integer.anim_duration_medium).toLong()

        sharedElementFragment2.enterTransition = slide
        sharedElementFragment2.allowEnterTransitionOverlap = overlap
        sharedElementFragment2.allowReturnTransitionOverlap = overlap
        sharedElementFragment2.sharedElementEnterTransition = changeBoundsTransition

        fragmentManager.beginTransaction()
                .replace(R.id.sample_content, sharedElementFragment2)
                .addToBackStack(null)
                .addSharedElement(imageCircle, getString(R.string.transition_circle_blue))
                .commit()
    }

}
