package com.ministren.material_animations.helper

import android.app.Activity
import android.view.View

/**
 * Helper class for creating content transitions used with {@link android.app.ActivityOptions}.
 */
object TransitionHelper {

    /**
     * Create the transition participants required during a activity transition
     * while avoiding glitches with the system UI.
     *
     * @param activity The activity used as start for the transition.
     * @param includeStatusBar If false, the status bar will not be added as the transition participant.
     * @return All transition participants.
     */
    fun createSafeTransitionParticipants(
            activity: Activity, includeStatusBar: Boolean,
            vararg otherParticipants: android.support.v4.util.Pair<View, String>
    ): Array<android.support.v4.util.Pair<View, String>> {
        // Avoid system UI glitches as described here:
        // https://plus.google.com/+AlexLockwood/posts/RPtwZ5nNebb
        val decor = activity.window.decorView
        val statusBar: View? = if (includeStatusBar) decor.findViewById(android.R.id.statusBarBackground) else null
        val navBar: View? = decor.findViewById(android.R.id.navigationBarBackground)

        // Create pair of transition participants.
        val participants = mutableListOf<android.support.v4.util.Pair<View, String>>()
        if (statusBar != null) participants.add(android.support.v4.util.Pair(statusBar, statusBar.transitionName))
        if (navBar != null) participants.add(android.support.v4.util.Pair(navBar, navBar.transitionName))
        participants.addAll(otherParticipants)
        return participants.toTypedArray()
    }

}
