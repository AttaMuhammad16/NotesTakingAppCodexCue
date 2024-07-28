package com.atta.notestakingapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.atta.notestakingapp.R

object Utils {
    inline fun myColors():IntArray{
        var itemColors = intArrayOf(
            R.color.color1,
            R.color.color2,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9,
            R.color.color10,
            R.color.color11,
            R.color.color12,
            R.color.color13,
            R.color.color14,
            R.color.color15,
            R.color.color16,
            R.color.color17,
        )
        return itemColors
    }

    inline fun copyContentText(data:String,context: Context){
        val text = data
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
    }

    @SuppressLint("DiscouragedApi")
    inline  fun searchViewTextClearSearchIconsColor(searchView: SearchView, context: Context, color: Int){
        (searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText).setTextColor(
            ContextCompat.getColor(context, color))
        (searchView.findViewById<View>(androidx.appcompat.R.id.search_mag_icon) as ImageView).setColorFilter(
            ContextCompat.getColor(context, color))
        (searchView.findViewById<View>(androidx.appcompat.R.id.search_close_btn) as ImageView).setColorFilter(
            ContextCompat.getColor(context, color))
    }

    inline  fun setSearchViewHintColor(context: Context, searchView: SearchView, color: Int, inputText: String? = null) {
        val queryHint = "Search"
        val hintColor = ContextCompat.getColor(context, color)
        val spannable = SpannableString(queryHint)
        spannable.setSpan(ForegroundColorSpan(hintColor), 0, queryHint.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        if (!inputText.isNullOrBlank()) {
            searchView.setQuery(inputText, false)
        }else{
            searchView.queryHint = spannable
        }
    }

    inline  fun preventShowingKeyboard(context: Context) {
        (context as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    inline  fun rippleEffect(context: Context,imageView: ImageView){
        val rippleDrawable = RippleDrawable(ColorStateList.valueOf(context.resources.getColor(R.color.rippleColor)), null, null)
        imageView.background = rippleDrawable
    }

}