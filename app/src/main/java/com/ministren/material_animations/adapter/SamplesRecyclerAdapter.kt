package com.ministren.material_animations.adapter

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ministren.material_animations.R
import com.ministren.material_animations.activity.*
import com.ministren.material_animations.databinding.SampleRvItemBinding
import com.ministren.material_animations.helper.TransitionHelper
import com.ministren.material_animations.model.Sample

class SamplesRecyclerAdapter(
        private val mActivity: Activity,
        private val mSamples: List<Sample>
) : RecyclerView.Adapter<SamplesRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val binding = SampleRvItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return ItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        val sample = mSamples[position]
        holder?.binding?.sample = sample
        holder?.binding?.sampleLayout?.setOnClickListener {
            when (position) {
                0 -> transitionToActivity(TransitionActivity1::class.java, sample)
                1 -> transitionToActivity(SharedElementActivity::class.java, holder, sample)
                2 -> transitionToActivity(AnimationsActivity1::class.java, sample)
                3 -> transitionToActivity(RevealActivity::class.java, holder, sample, R.string.transition_reveal)
            }
        }
    }

    override fun getItemCount(): Int = mSamples.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: SampleRvItemBinding = DataBindingUtil.bind(itemView)
    }

    private fun transitionToActivity(target: Class<*>, sample: Sample) {
        val pairs = TransitionHelper.createSafeTransitionParticipants(mActivity, true)
        startActivity(target, pairs, sample)
    }

    private fun transitionToActivity(target: Class<*>, viewHolder: ItemViewHolder, sample: Sample) {
        val pairs = TransitionHelper.createSafeTransitionParticipants(mActivity, false,
                android.support.v4.util.Pair(viewHolder.binding.sampleIcon, mActivity.getString(R.string.transition_circle_blue)),
                android.support.v4.util.Pair(viewHolder.binding.sampleName, mActivity.getString(R.string.transition_title))
        )
        startActivity(target, pairs, sample)
    }

    private fun transitionToActivity(target: Class<*>, viewHolder: ItemViewHolder, sample: Sample, transitionName: Int) {
        val pairs = TransitionHelper.createSafeTransitionParticipants(mActivity, false,
                android.support.v4.util.Pair(viewHolder.binding.sampleIcon, mActivity.getString(transitionName))
        )
        startActivity(target, pairs, sample)
    }

    private fun startActivity(target: Class<*>, pairs: Array<android.support.v4.util.Pair<View, String>>, sample: Sample) {
        val intent = Intent(mActivity, target)
        val transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, *pairs)
        intent.putExtra(BaseDetailActivity.EXTRA_SAMPLE, sample)
        mActivity.startActivity(intent, transitionActivityOptions.toBundle())
    }

}
