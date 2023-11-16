package com.example.cryptocurrency.utils

import androidx.fragment.app.Fragment

fun Fragment.cutOffPoint(text: Double) : String{
    return String.format("%.2f",text)
}