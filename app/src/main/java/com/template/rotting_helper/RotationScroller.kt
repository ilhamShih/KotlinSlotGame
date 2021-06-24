package com.template.rotting_helper

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator

import android.widget.Scroller
import kotlin.math.abs


class RotationScroller(context: Context?, listener: ScrollingListener) : Runnable {

    /**--    Scrolling the listener interface      -- */
    interface ScrollingListener {

        /**  ---  Scroll callback. @ Param scroll distance ---   */
        fun onScroll(distance: Int)

        /** --- Completing the callback. ---  */
        fun onFinished()
    }

    private val mHandler: Handler = Handler(Looper.getMainLooper())
    private val mScroller: Scroller = Scroller(context, AccelerateDecelerateInterpolator())
    private val mScrollListener: ScrollingListener = listener
    var lastY = 0
    private var distance = 0
    private var previousDistance = 0
    fun scroll(distance: Int, duration: Int) {
        this.distance = distance
        mScroller.forceFinished(true)
        mScroller.startScroll(0, 0, 0, distance, duration)
        mHandler.post(this)
    }

    override fun run() {
        var delta = 0
        mScroller.computeScrollOffset()
        val currY = mScroller.currY
        delta = currY - lastY
        lastY = currY
        if (abs(delta) != previousDistance && delta != 0) {
            mScrollListener.onScroll(delta)
        }
        if (!mScroller.isFinished) {
            mHandler.post(this)
        } else {
            previousDistance = distance
            mScrollListener.onFinished()
        }
    }

}
