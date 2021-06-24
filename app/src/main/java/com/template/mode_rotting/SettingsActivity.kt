package com.template.mode_rotting


import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.template.R


class SettingsActivity : AppCompatActivity() , CompoundButton.OnCheckedChangeListener {

    /**  ----- switch1 -------    */
    //  ----------------------------------------------------------
    lateinit var switch1: Switch
    lateinit var switch2: Switch

    /**  ----- switch2 -------    */

    private var switchOnOff  = false
    private var switchOnOff2 = false
    //  ----------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        /**  ----- switch1 -------    */
        //  ----------------------------------------------------------
        switch1 = findViewById(R.id.switch1)
        switch1.setOnCheckedChangeListener(this)

        /**  ----- switch2 -------    */

        switch2 = findViewById(R.id.switch2)
        switch2.setOnCheckedChangeListener(this)
        //  ----------------------------------------------------------

        /**  -----  We get the settings  -------    */
//  -------------------------
        shouViews()
    }

        /**  -----  We save the settings  -------    */
        //  ----------------------------------------------------------
    fun saveData() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(SWITCH1, switch1.isChecked)
        editor.putBoolean(SWITCH2, switch2.isChecked)
        editor.apply()
//  -------------------------
        shouViews()
    }
    //  ----------------------------------------------------------

    /**  -----  Loading settings  -------    */
    //  ----------------------------------------------------------
    fun loadData():Boolean {
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false)
        return switchOnOff
    }
    /**  -----  Loading settings -------    */
    fun loadData2():Boolean {
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        switchOnOff2 = sharedPreferences.getBoolean(SWITCH2, false)
        return switchOnOff2
    }
    //  ----------------------------------------------------------

    /**  -----  Get settings  -------    */
    //  ----------------------------------------------------------
    fun shouViews() {
        switch1.isChecked = loadData()
        switch2.isChecked = loadData2()
        }
    //  ----------------------------------------------------------


    /**  ----- Modes -------    */
    //  ----------------------------------------------------------
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {

            /**  -----  Mode Consecutive -------    */
            R.id.switch1 -> {
                if (isChecked) {
                    /**  ----- Switched on -------    */
                    switch1.isChecked = true
                    switch2.isChecked = false
                    saveData()
                } else {
                    /**  ----- Switched off -------    */
                    switch1.isChecked = false
                    switch2.isChecked = true
                    saveData()

                }
            }
            /**  -----  Mode Invertede -------    */
 //  -----------------------------
            R.id.switch2 -> {
                if (isChecked) {
                    /**  ----- Switched on -------    */
                    switch2.isChecked = true
                    switch1.isChecked = false
                    saveData()
                } else {
                    /**  ----- Switched off -------    */
                    switch2.isChecked = false
                    switch1.isChecked = true
                    saveData()

                }
            }
        }
    }
    //  ----------------------------------------------------------

    /**  ----- Constants -------    */
    //  ----------------------------------------------------------
    companion object {
        const val SHARED_PREFS = "sharedPrefs"
        const val SWITCH1 = "switch1"
        const val SWITCH2 = "switch2"
    }
    //  ----------------------------------------------------------
}