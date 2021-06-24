package com.template


import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.template.data_array.Drawable_Icon
import com.template.interface_item.ItemRotation
import com.template.mode_rotting.SettingsActivity
import com.template.rotting_helper.RotaryView
import java.util.*
import kotlin.collections.ArrayList


public open class BazeActivity : AppCompatActivity() , View.OnClickListener, View.OnTouchListener {

    val SPIN_TIME = 800
    var random: Random? = null
    var randoms = 0
    lateinit var mResultsText: TextView
    val handlers = Handler(Looper.getMainLooper())

   
    /**    Sum   BET     */
    //  ----------------------------------------------------------
    var data_list = 0
    var data_list2 = 0
    var temp = 1
    var lastClicked = -1
    var ADD = 1
    var REMOVE = 2
    //  ----------------------------------------------------------

    //  ----------------------------------------------------------
    lateinit var rotaryView1: RotaryView
    lateinit var rotaryView2: RotaryView
    lateinit var rotaryView3: RotaryView
    lateinit var rotaryView4: RotaryView
    lateinit var rotaryView5: RotaryView
    //  ----------------------------------------------------------

    //  ----------------------------------------------------------
    lateinit var items1: ArrayList<ItemRotation>
    lateinit var items2: ArrayList<ItemRotation>
    lateinit var items3: ArrayList<ItemRotation>
    lateinit var items4: ArrayList<ItemRotation>
    lateinit var items5: ArrayList<ItemRotation>
    //  ----------------------------------------------------------

    /**    Pictures        */
    //  ----------------------------------------------------------
    var drawable_Icon: Drawable_Icon = Drawable_Icon()
    var Imag1: IntArray = drawable_Icon.imagesItem1
    var Imag2: IntArray = drawable_Icon.imagesItem2
    var Imag3: IntArray = drawable_Icon.imagesItem3
    var Imag4: IntArray = drawable_Icon.imagesItem4
    var Imag5: IntArray = drawable_Icon.imagesItem5
    //  ----------------------------------------------------------

    /**    Output        */
    protected val MESSAGE_CHECK_MATCH = 0
    //  ----------------------------------------------------------
    private var SlotSelect1 = 0
    private var SlotSelect2 = 0
    private var SlotSelect3 = 0
    private var SlotSelect4 = 0
    private var SlotSelect5 = 0
    //  ----------------------------------------------------------
    var robotoBoldFont: Typeface? = null

    /**    Кнопки    */
    //  ----------------------------------------------------------
    lateinit var spin: ImageView
    lateinit var profil: ImageView
    lateinit var value: TextView
    lateinit var adds: ImageView
    //  ----------------------------------------------------------

    //  ----------------------------------------------------------
    lateinit var params0: LinearLayout.LayoutParams
    lateinit var params2: LinearLayout.LayoutParams
    lateinit var params3: LinearLayout.LayoutParams
    lateinit var params4: LinearLayout.LayoutParams
    lateinit var params5: LinearLayout.LayoutParams
    //  ----------------------------------------------------------

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        random = Random()
        randoms = random!!.nextInt(200)

        //  ----------------------------------------------------------
        /**  -----  SPIN -------    */
        spin = findViewById(R.id.spin)
        spin.setOnClickListener(this)

        /**  ----- To Modes -------    */
        profil = findViewById(R.id.profil)
        profil.setOnClickListener(this)

        /**  ---  Bet button  ---- */
        adds = findViewById(R.id.imageView)
        adds.setOnTouchListener(this)

        /**   ----  Bet summ----  */
        value = findViewById(R.id.text_value)

        /**   ----  Bet result  ----  */
        mResultsText = findViewById(R.id.summ)
        mResultsText.setTypeface(robotoBoldFont)
        //  ----------------------------------------------------------

        /**  ----- Slots ----  */
        //  ----------------------------------------------------------
        rotaryView1 = findViewById(R.id.slot1)
        rotaryView2 = findViewById(R.id.slot2)
        rotaryView3 = findViewById(R.id.slot3)
        rotaryView4 = findViewById(R.id.slot4)
        rotaryView5 = findViewById(R.id.slot5)
        //  ----------------------------------------------------------

        /**  ----- Array for each wheel. ----  */
        //  ----------------------------------------------------------
        items1 = ArrayList<ItemRotation>()
        items2 = ArrayList<ItemRotation>()
        items3 = ArrayList<ItemRotation>()
        items4 = ArrayList<ItemRotation>()
        items5 = ArrayList<ItemRotation>()
        //  ----------------------------------------------------------

        /**  ------ Loop adding an array  -----  */
        //  ----------------------------------------------------------
        for (i in 0..9) {
            items1.add(ItemsPosition(1, i))
            items2.add(ItemsPosition(2, i))
            items3.add(ItemsPosition(3, i))
            items4.add(ItemsPosition(4, i))
            items5.add(ItemsPosition(5, i))
        }
        //  ----------------------------------------------------------

        /** ------  Installing slots    -----     */
        //  ----------------------------------------------------------
        /**  ------ First slot -----  */
        rotaryView1.setSlotItems(items1)
        /** ------  Second slot ------  */
        rotaryView2.setSlotItems(items2)
        /**  ------ Third slot -----  */
        rotaryView3.setSlotItems(items3)
        /**  ------ Fourth slot -------  */
        rotaryView4.setSlotItems(items4)
        /**  ------ Fifth slot  -------    */
        rotaryView5.setSlotItems(items5)
        //  ----------------------------------------------------------

        /** ------  Number of icons in the slot    -----     */
        //  ----------------------------------------------------------
        rotaryView1.setNumberOfVisibleItems(3)
        rotaryView2.setNumberOfVisibleItems(3)
        rotaryView3.setNumberOfVisibleItems(3)
        rotaryView4.setNumberOfVisibleItems(3)
        rotaryView5.setNumberOfVisibleItems(3)
        //  ----------------------------------------------------------


        /**  ---  Scrolling complete   ----    */
        //  ----------------------------------------------------------
        rotaryView1.setRotationFinished(object : RotaryView.OnRotationFinished {
            override fun onRotationFinished(position: Int) {
                SlotSelect1 = position
            }
        })

        rotaryView2.setRotationFinished(object : RotaryView.OnRotationFinished {
            override fun onRotationFinished(position: Int) {
                SlotSelect1 = position
            }
        })

        rotaryView3.setRotationFinished(object : RotaryView.OnRotationFinished {
            override fun onRotationFinished(position: Int) {
                SlotSelect1 = position
            }
        })

        rotaryView4.setRotationFinished(object : RotaryView.OnRotationFinished {
            override fun onRotationFinished(position: Int) {
                SlotSelect1 = position
            }
        })

        rotaryView5.setRotationFinished(object : RotaryView.OnRotationFinished {
            override fun onRotationFinished(position: Int) {
                SlotSelect1 = position
            }
        })
        //  ----------------------------------------------------------

        /**  ----- Layout Parameters -------    */
        //  ----------------------------------------------------------
        params0 = rotaryView1.getLayoutParams() as LinearLayout.LayoutParams
        params2 = rotaryView2.getLayoutParams() as LinearLayout.LayoutParams
        params3 = rotaryView3.getLayoutParams() as LinearLayout.LayoutParams
        params4 = rotaryView4.getLayoutParams() as LinearLayout.LayoutParams
        params5 = rotaryView5.getLayoutParams() as LinearLayout.LayoutParams

        params0.width = Dislay(1)
        params0.height = Dislay(2)
        params0.leftMargin = Dislay(3)
        params2.width = Dislay(1)
        params2.height = Dislay(2)
        params2.leftMargin = Dislay(3)
        params3.width = Dislay(1)
        params3.height = Dislay(2)
        params3.leftMargin = Dislay(3)
        params4.width = Dislay(1)
        params4.height = Dislay(2)
        params4.leftMargin = Dislay(3)
        params5.width = Dislay(1)
        params5.height = Dislay(2)
        params5.leftMargin = Dislay(3)

        val thickLine1 = findViewById(R.id.thickLine1) as View
        val thickLine2 = findViewById(R.id.thickLine2) as View
        var params1 = thickLine1.layoutParams as RelativeLayout.LayoutParams
        params1.width = Dislay(4) + 2 * Dislay(3)
        params1 = thickLine2.layoutParams as RelativeLayout.LayoutParams
        params1.width = Dislay(4) + 2 * Dislay(3)
        params1 = (findViewById(R.id.slotSpinLayout) as View).layoutParams as RelativeLayout.LayoutParams
        params1.width = Dislay(1) + Dislay(3) / 2

        //  ----------------------------------------------------------
    }



    //  ------------------------------------------------------------------------------------------------------
    protected val detectAnyMatchHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what === MESSAGE_CHECK_MATCH) {
                spin.setEnabled(true)
                spin.setVisibility(View.VISIBLE)
//-------------------------------------- icon1
                if (SlotSelect1 == 1 && SlotSelect2 == 2 && SlotSelect3 == 4 && SlotSelect4 == 3 && SlotSelect5 == 3) {
//-------------------------------------- icon2
                } else if (SlotSelect1 == 2 && SlotSelect2 == 5 && SlotSelect3 == 6 && SlotSelect4 == 5 && SlotSelect5 == 2) {
//-------------------------------------- icon3
                } else if (SlotSelect1 == 3 && SlotSelect2 == 6 && SlotSelect3 == 3 && SlotSelect4 == 4 && SlotSelect5 == 4) {
//-------------------------------------- icon4
                } else if (SlotSelect1 == 4 && SlotSelect2 == 1 && SlotSelect3 == 5 && SlotSelect4 == 6 && SlotSelect5 == 5) {
//-------------------------------------- icon5
                } else if (SlotSelect1 == 5 && SlotSelect2 == 7 && SlotSelect3 == 2 && SlotSelect4 == 1 && SlotSelect5 == 6) {
//-------------------------------------- icon6
                } else if (SlotSelect1 == 6 && SlotSelect2 == 4 && SlotSelect3 == 7 && SlotSelect4 == 2 && SlotSelect5 == 1) {
//-------------------------------------- icon7
                } else if (SlotSelect1 == 7 && SlotSelect2 == 3 && SlotSelect3 == 1 && SlotSelect4 == 2 && SlotSelect5 == 7) {
//-------------------------------------- icon8
                } else if (SlotSelect1 == 8 && SlotSelect2 == 10 && SlotSelect3 == 9 && SlotSelect4 == 9 && SlotSelect5 == 6) {
//-------------------------------------- icon9
                } else if (SlotSelect1 == 9 && SlotSelect2 == 9 && SlotSelect3 == 8 && SlotSelect4 == 8 && SlotSelect5 == 8) {
//-------------------------------------- icon10
                } else if (SlotSelect1 == 10 && SlotSelect2 == 8 && SlotSelect3 == 10 && SlotSelect4 == 10 && SlotSelect5 == 9) {
                } else {
                    mResultsText.visibility = View.VISIBLE
                    val m = data_list2.toString()
                    mResultsText.text = m
                }
            }
        }
    }
    //  ------------------------------------------------------------------------------------------------------

    /**    --------- Interface  ItemRotation -----------        */
    //  ------------------------------------------------------------------------------------------------------
    internal inner class ItemsPosition(var slotItPos: Int, var slotItemPos: Int) : ItemRotation {
        override val view: View
            get() {
                val view = layoutInflater.inflate(R.layout.item_rotation, null, false) as View
                val itemImageView: ImageView = view.findViewById(R.id.itemImage) as ImageView

                if (slotItPos == 1) {
                    itemImageView.setImageResource(Imag1[slotItemPos])
                } else if (slotItPos == 2) {
                    itemImageView.setImageResource(Imag2[slotItemPos])
                } else if (slotItPos == 3) {
                    itemImageView.setImageResource(Imag3[slotItemPos])
                } else if (slotItPos == 4) {
                    itemImageView.setImageResource(Imag4[slotItemPos])
                } else if (slotItPos == 5) {
                    itemImageView.setImageResource(Imag5[slotItemPos])
                }
                return view
            }
    }
    //  ------------------------------------------------------------------------------------------------------


    /**  ----- Calculating the width of the screen and wheel -------   */
    //  ----------------------------------------------------------
    private fun Dislay(i: Int): Int {
        var retur = 0
        val size = Point()
        getWindowManager().getDefaultDisplay().getSize(size)
        var width: Int = size.x
        val height: Int = size.y
        val density = resources.displayMetrics.density
        val Indent_slots = (3 * density + 0.5f).toInt()
        val slotWidth: Int
        val slotHeight: Int
        width = width * 63 / 100
        slotHeight = height * 43 / 100
        width -= 2 * Indent_slots
        /**  ----- Number of slots  5-------    */
        slotWidth = width / 5
        if (i == 1) {
            retur = slotWidth
        } else if (i == 2) {
            retur = slotHeight
        } else if (i == 3) {
            retur = Indent_slots
        } else if (i == 4) {
            retur = width
        }
        return retur
    }

    //  ----------------------------------------------------------


    /** ----  Bet handler ----       */
    //  ----------------------------------------------------------
    fun clickBet(i: Int) {
        /** ----  to enlarge ----       */
        if (i == 1) {
            if (lastClicked == REMOVE) {
                lastClicked = ADD
                return
            }
            temp++
            //value = findViewById(R.id.text_value)
            lastClicked = ADD
            value.text = "" + temp
            data_list = temp
            /** ----  Reduce ----       */
        } else if (i == 2) {
            if (lastClicked == ADD) {
                lastClicked = REMOVE
                return
            }
            if (temp > 1) {
                temp--
            } else {
                temp = 1
            }

            lastClicked = REMOVE
            value.text = "" + temp
            data_list = temp
        }
    }
    //  ----------------------------------------------------------



    /**  ----- From 1  -> 5
     * Rotation order of slots-------    */
    //  ----------------------------------------------------------

    fun modeConsecutive() {

        /**  ----- Scrolling time and distance -------    */
        spin.setEnabled(false)
        spin.setVisibility(View.GONE)
        rotaryView1.scroll(7000 * randoms, SPIN_TIME)
        handlers.postDelayed({
            rotaryView2.scroll(7000 * randoms, SPIN_TIME)
        }, 500)
        handlers.postDelayed({
            rotaryView3.scroll(7000 * randoms, SPIN_TIME)
        }, 1000)
        handlers.postDelayed({
            rotaryView4.scroll(7000 * randoms, SPIN_TIME)
        }, 1500)
        handlers.postDelayed({
            rotaryView5.scroll(7000 * randoms, SPIN_TIME)
            val msg: Message = Message.obtain()
            msg.what = MESSAGE_CHECK_MATCH
            detectAnyMatchHandler.sendMessageDelayed(msg, (SPIN_TIME + 500).toLong())
            /**  ----- Add to the sum -------    */
            val d = data_list
            val d2 = data_list2
            val d3 = d2 + d
            data_list2 = d3
        }, 2000)
    }
    //  ----------------------------------------------------------


    /**  ----- From 5  -> 1
     * Rotation order of slots -------    */
    //  ----------------------------------------------------------
    fun modeInvertede() {
        /**  ----- Scrolling time and distance -------    */
        spin.setEnabled(false)
        spin.setVisibility(View.GONE)
        rotaryView5.scroll(7000 * randoms, SPIN_TIME)
        handlers.postDelayed({
            rotaryView4.scroll(7000 * randoms, SPIN_TIME)
        }, 500)
        handlers.postDelayed({
            rotaryView3.scroll(7000 * randoms, SPIN_TIME)
        }, 1000)
        handlers.postDelayed({
            rotaryView2.scroll(7000 * randoms, SPIN_TIME)
        }, 1500)
        handlers.postDelayed({
            rotaryView1.scroll(7000 * randoms, SPIN_TIME)
            val msg: Message = Message.obtain()
            msg.what = MESSAGE_CHECK_MATCH
            detectAnyMatchHandler.sendMessageDelayed(msg, (SPIN_TIME + 500).toLong())
            /**  ----- Add to the sum -------    */
            val d = data_list
            val d2 = data_list2
            val d3 = d2 + d
            data_list2 = d3
        }, 2000)
    }
    //  ----------------------------------------------------------


    /**  ----- Buttons  -------    */
    //  ----------------------------------------------------------
    override fun onClick(v: View?) {
        when (v?.id) {

            /**  -----  SPIN -------    */
            R.id.spin -> {
                if (loadData() == true) {
                    modeConsecutive()
                } else if (loadData() == false) {
                    modeInvertede()

                }
            }
            /**  -----  customization -------    */
            R.id.profil -> {
                val intent = Intent(applicationContext, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
    }

//  --------------------------------

    override fun onTouch(v: View?,  event: MotionEvent?): Boolean {
        when (v?.id) {

            /**  ----- bet -------    */
            R.id.imageView -> {
                val x = event!!.x.toInt()
                val y = event.y.toInt()
                if (x > 550 && x < 710 && y > 0 && y < 170) {
                    /** --- Increase bet -----   */
                    clickBet(1)
                } else if (x > 225 && x < 380 && y > 0 && y < 170) {
                    /**  ----- Reduce bet -----    */
                    clickBet(2)
                }


            }
        }
        return false

    }

    //  ----------------------------------------------------------

    //  ----------------------------------------------------------

    public override fun onDestroy() {
        super.onDestroy()
    }
    /**  -----  Loading settings -------    */
    fun loadData(): Boolean {
        val sharedPreferences = getSharedPreferences(SettingsActivity.SHARED_PREFS, Context.MODE_PRIVATE)
        var switchOnOff = sharedPreferences.getBoolean(SettingsActivity.SWITCH1, false)
        return switchOnOff
    }

    //  ----------------------------------------------------------
}