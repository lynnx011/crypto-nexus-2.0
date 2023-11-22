package com.example.cryptocurrency.utils

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

fun Fragment.cutOffPoint(text: Double): String {
    return String.format("%.2f", text)
}

fun Fragment.navigateTo(@IdRes resId: Int) = findNavController().navigate(resId)

fun Fragment.navigateWithBundle(@IdRes resId: Int, bundle: Bundle) =
    findNavController().navigate(resId, bundle)

fun Fragment.popBack() = findNavController().popBackStack()
fun Fragment.showToast(text: String) =
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()

fun Context.showToast(text: String) = Toast.makeText(this,text,Toast.LENGTH_SHORT).show()

fun CardView.backgroundRes(res: Int) = this.setBackgroundResource(res)

fun ImageView.setImgDrawable(context: Context, @DrawableRes res: Int) = this.setImageDrawable(
    ContextCompat.getDrawable(
        context,
        res
    )
)

fun View.setBackgroundColor(context: Context, @ColorRes res: Int) = this.setBackgroundColor(
    ContextCompat.getColor(
        context,
        res
    )
)

fun TextView.setTextColor(context: Context, @ColorRes res: Int) =
    this.setTextColor(ContextCompat.getColor(context, res))

fun ImageView.loadImage(context: Context, url: String, @DrawableRes res: Int) =
    Glide.with(context).load(url).thumbnail(Glide.with(context).load(res)).into(this)

fun TextInputEditText.setEdtBackgroundColor(context: Context, @ColorRes res: Int) =
    this.setBackgroundColor(
        ContextCompat.getColor(
            context,
            res
        )
    )

fun Button.setBtnBackground(context: Context, @ColorRes res: Int) = this.setBackgroundColor(
    ContextCompat.getColor(
        context,
        res
    )
)

fun ImageView.loadImg(context: Context, url: String) =
    Glide.with(context).load(url).into(this)

fun Fragment.loopAndWork(work: () -> Unit, millisSec: Long) {
    Handler(Looper.getMainLooper()).postDelayed({ work() }, millisSec)
}

fun cryptoLogoUrl(id: Int?) : String = "https://s2.coinmarketcap.com/static/img/coins/64x64/$id.png"

fun cryptoSparklineUrl(id: Int?) : String = "https://s3.coinmarketcap.com/generated/sparklines/web/1d/usd/$id.png"