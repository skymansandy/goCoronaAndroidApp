package dev.skymansandy.base.ui.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import dev.skymansandy.base.R
import kotlinx.android.synthetic.main.layout_wizard_row_tile.view.*

class WizardRowTileView(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    private var title: String? = ""
    private var iconDrawable: Drawable? = null

    init {
        init(context, attributeSet)
    }

    constructor(context: Context) : this(context, null) {
        init(context, null)
    }

    private fun drawUi() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_wizard_row_tile, this, true)
    }

    private fun init(
        context: Context,
        attributeSet: AttributeSet? = null
    ) {
        drawUi().also {
            with(context.obtainStyledAttributes(attributeSet, R.styleable.WizardRowTileView)) {
                title = getString(R.styleable.WizardRowTileView_title)
                iconDrawable = getDrawable(R.styleable.WizardRowTileView_icon)
                recycle()
            }
            tv_title.text = title
            iv_icon.setImageDrawable(iconDrawable)
        }
    }
}