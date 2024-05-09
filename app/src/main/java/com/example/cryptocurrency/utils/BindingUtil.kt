package com.example.cryptocurrency.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@BindingAdapter("doubleToString")
fun doubleExchange(view: TextView, text: Double) {
    if (text > 0.000) {
        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
        view.text = String.format(" %.3f %%", text)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("formatDouble")
fun formatDouble(view: TextView, text: Double) {
    if (text > 0.000) {
        view.setTextColor(ContextCompat.getColor(view.context, R.color.tangerine))
        view.text = String.format(" %.1f", text) + "ETH"
    }
}

@BindingAdapter("formatString")
fun formatString(view: TextView, text: Int?) {
    if ((text?:0) > 0) {
        view.setTextColor(ContextCompat.getColor(view.context, R.color.tangerine))
        val sub = text.toString().substring(0, text.toString().length - 2)
        view.text = sub
    }
}

@BindingAdapter("loadImage")
fun loadImageUrl(view: ImageView, imgUrl: String?) {
    if (!imgUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/$imgUrl.png")
            .into(view)
    }
}

@BindingAdapter("loadImageId")
fun loadImageId(view: ImageView, imgId: Int?) {
    if (imgId != null){
        Glide.with(view.context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/$imgId.png")
            .into(view)
    }
}

@BindingAdapter("formatText")
fun formatText(view: TextView, text: Double?) {
    view.text = String.format("$%.4f", text?:0.0)
}

@BindingAdapter("formatTransaction")
fun formatTransaction(view: TextView, text: Double?) {
    view.text = String.format("%.2f", text?:0.0)
}

@BindingAdapter("formatTransDouble")
fun formatTransDouble(view: TextView, text: Double?) {
    view.text = String.format("$%.2f", text?:0.0)
}

@BindingAdapter("transPercent")
fun formatTransPercent(view: TextView, text: Double?) {
    view.text = String.format("%.2f%%", text?:0.0)
}

@BindingAdapter("formatHash")
fun formatHash(view: TextView, text: Int?) {
    view.text = String.format("#%.0f", text)
}

@SuppressLint("SimpleDateFormat", "SetTextI18n")
@BindingAdapter("parseDate")
fun parseDate(view: TextView, text: String?) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy h:mm a")
    val parseDateTime = LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME)
    val outputDateTime = parseDateTime.atOffset(ZoneOffset.UTC).format(dateFormatter)
    view.text = outputDateTime?:""
}

@BindingAdapter("loadUrl")
fun loadUrl(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()){
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}