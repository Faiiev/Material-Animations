package com.ministren.material_animations.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ministren.material_animations.R
import com.ministren.material_animations.activity.BaseDetailActivity.Companion.EXTRA_SAMPLE
import com.ministren.material_animations.databinding.FragmentSharedElement2Binding
import com.ministren.material_animations.model.Sample

class SharedElementFragment2 : Fragment() {

    companion object {
        fun newInstance(sample: Sample): SharedElementFragment2 {
            val fragment = SharedElementFragment2()
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
        val binding: FragmentSharedElement2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shared_element2, container, false)
        val view = binding.root
        binding.sample = mSample
        return view
    }

}
