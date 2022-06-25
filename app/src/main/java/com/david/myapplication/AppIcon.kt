package com.david.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.app_icon.view.*

class AppIcon @SuppressLint("CustomViewStyleable") @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.app_icon, this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.AppIconAttrs, 0, 0)
            val image = typedArray.getDrawable(R.styleable.AppIconAttrs_appicon_image)
            val title = typedArray.getString(R.styleable.AppIconAttrs_appicon_title)
            val gradient = typedArray.getDrawable(R.styleable.AppIconAttrs_appicon_gradient)
            typedArray.recycle()

            ivIcon.setImageDrawable(image)
            tvIcon.text = title
            flIcon.background = gradient
        }
    }
}