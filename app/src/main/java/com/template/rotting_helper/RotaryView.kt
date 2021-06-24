package com.template.rotting_helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import com.template.rotting_helper.RotationScroller.ScrollingListener
import com.template.interface_item.ItemRotation
import java.util.*
import kotlin.collections.ArrayList


class RotaryView : View, ScrollingListener {
    private var mWhiteBackgroundPaint: Paint? = null

    /** Upper and lower shadow colors  */
    private val SHADOWS_COLORS = intArrayOf(-0xeeeeef, 0x00AAAAAA, 0x00AAAAAA)

    /** Slot height  */
    private var itemHeight = 0

    /** Offset X, start drawing the slot element so as not to draw on the frame.  */
    private var itemX = 0
    private var mViewFullWidth = 0
    private var mViewFullHeight = 0
    private var mViewWidth = 0

    /**       List of slots.      */
    lateinit var mSlotItems: MutableList<DrawSlotItem>

    /**     Shadow Drawings   */
    private var topShadow: GradientDrawable? = null
    private var bottomShadow: GradientDrawable? = null
    private var mFullViewRect: Rect? = null

    /**    Scroller     */
    private var mReelScroller: RotationScroller? = null

    /**     visible elements of the slot.      */
    private var visibleSlotItems = 3

    /**        Scrolling direction       */
    private val scrollDown = true

    /**Allows you to check whether to put slot items in the middle after scrolling. */
    private var checkForMiddling = true

    /**
     * A listener that tells the parent the position of the slot element that
     * located in the middle in the center after the scroll.
     */
    interface OnRotationFinished {
        fun onRotationFinished(position: Int)
    }

    private var mRotationFinished: OnRotationFinished? = null

    /**
     * Constructors
     */
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context?) : super(context) {
        init(context)
    }

    private fun init(context: Context?) {
        mFullViewRect = Rect()
        mReelScroller = RotationScroller(context, this)
        mWhiteBackgroundPaint = Paint()
        mWhiteBackgroundPaint!!.color = Color.WHITE
        topShadow = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, SHADOWS_COLORS)
        bottomShadow = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, SHADOWS_COLORS)
        mSlotItems = ArrayList()
    }
    /** ------  We get an array of slots    -----     */
    fun setSlotItems(items: List<ItemRotation?>) {
        fillSlotDrawItems(items)
    }

    private fun fillSlotDrawItems(slotItems: List<ItemRotation?>) {
        mSlotItems.clear()
        var position = 1
        for (item in slotItems) {
            mSlotItems.add(DrawSlotItem(item!!, position))
            position++
        }
    }

    fun setNumberOfVisibleItems(visible: Int) {
        visibleSlotItems = visible
    }

    fun scroll(distance: Int, duration: Int) {
        if (distance != 0) {
            checkForMiddling = true
            mReelScroller!!.scroll(distance, duration)
        }
    }

    fun setRotationFinished(listener: OnRotationFinished?) {
        mRotationFinished = listener
    }

     override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /**     Full view width     */
        mViewFullWidth =
            getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        mViewFullHeight =
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        /**    Width without frames    */
        mViewWidth = mViewFullWidth - 2 * FRAME_PADDING
        val viewHeight = mViewFullHeight - 2 * FRAME_PADDING
        itemHeight = viewHeight / visibleSlotItems
        setCorrectVisibleItems()
        resetSlotItemsPositions(scrollDown)
        itemX = FRAME_PADDING
        mFullViewRect?.top ?: FRAME_PADDING
        mFullViewRect?.left ?: FRAME_PADDING
        mFullViewRect?.right  ?: mViewWidth + FRAME_PADDING
        mFullViewRect?.bottom  ?: mViewFullHeight - FRAME_PADDING
        setMeasuredDimension(mViewFullWidth, mViewFullHeight)
    }

    /**
     * Sets the correct number of visible Items.
     */
    private fun setCorrectVisibleItems() {
        if (visibleSlotItems == 0 || visibleSlotItems == mSlotItems.size) {
             visibleSlotItems = mSlotItems.size - 1
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /**   Slot elements   */
        drawSlotItems(canvas)
        /**   Shadows    */
        drawShadows(canvas)
    }

    private fun drawSlotItems(canvas: Canvas) {
        for (item in mSlotItems) {
            val view: View? = item.getView()
            val widthSpec = MeasureSpec.makeMeasureSpec(mViewWidth, MeasureSpec.EXACTLY)
            val heightSpec = MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY)
            view?.measure(widthSpec, heightSpec)
            view?.layout(
                itemX,
                item.y + FRAME_PADDING,
                mViewWidth,
                item.y + itemHeight
            )
            canvas.save()
            canvas.translate(itemX.toFloat(), (item.y + FRAME_PADDING).toFloat())
            view?.draw(canvas)
            canvas.restore()
        }
    }

    /**       Draws shadows at the top and bottom of an element    */
    private fun drawShadows(canvas: Canvas) {
        val height = (1.0 * itemHeight).toInt()
        topShadow!!.setBounds(0, 0, width, height)
        topShadow!!.draw(canvas)
        bottomShadow!!.setBounds(0, getHeight() - height, width, getHeight())
        bottomShadow!!.draw(canvas)
    }

    /**
     * Helper class for holding the Y position of each slot element.
     * Scrolling is achieved by zooming in / out.
     */
    inner class DrawSlotItem(item: ItemRotation, var slotPosition: Int) {
        var y = 0
        private var view: View? = item.view

        fun setPosY(y: Int) {
            this.y = y
        }

        fun getView(): View? {
            return view
        }

    }

    private fun scroll(delta: Int) {
        val scrollDown = delta < 0
        /**   Position update    */
        for (item in mSlotItems) {
            item.setPosY(item.y - delta)
        }
        val hiddingItemY: Int
        if (scrollDown) {
            hiddingItemY = mSlotItems[visibleSlotItems - 1].y
            if (hiddingItemY >= mViewFullHeight) {
                for ((j, i) in (1 until mSlotItems.size).withIndex()) {
                    Collections.swap(mSlotItems, j, i)
                }
                resetSlotItemsPositions(scrollDown)
            }
        } else {
            hiddingItemY = mSlotItems[0].y
            if (hiddingItemY <= -itemHeight) {
                for ((j, i) in (1 until mSlotItems.size).withIndex()) {
                    Collections.swap(mSlotItems, j, i)
                }
                resetSlotItemsPositions(scrollDown)
            }
        }
        invalidate()
    }

    private fun resetSlotItemsPositions(scrollDown: Boolean) {
        var multipler = if  (scrollDown)  -1  else 0
        for (item in mSlotItems) {
            item.setPosY(FRAME_PADDING + itemHeight * multipler)
            multipler++
        }
    }

    override fun onScroll(distance: Int) {
        scroll(distance)
    }

    override fun onFinished() {
        if (checkForMiddling) {
            positionNearestMiddleItem()
            checkForMiddling = false
        }
    }

    /**        The visible item after scrolling.      */
    private fun positionNearestMiddleItem() {
        /**      Mid-view height         */
        val viewCenter = (mViewFullHeight - 2 * FRAME_PADDING) / 2
        val distance =
            mSlotItems[visibleSlotItems].y - (viewCenter - itemHeight / 2)
        scroll(distance, 0)
        if (mRotationFinished != null) {
            mRotationFinished!!.onRotationFinished(mSlotItems[visibleSlotItems].slotPosition)
        }
    }

    companion object {
        private const val FRAME_PADDING = 6
    }
}

