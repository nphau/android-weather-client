package au.com.nab.android.shared.screens.views

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import au.com.nab.android.shared.R
import au.com.nab.android.shared.common.extensions.safeClick

typealias SharedToolBarCallBack = () -> Unit

class SharedToolBar : LinearLayout {

    private var textTitle: TextView? = null
    private var buttonLeft: AppCompatImageView? = null
    private var buttonRight: AppCompatImageView? = null
    private var progressBar: ProgressBar? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs == null)
            return
        val array = context.obtainStyledAttributes(attrs, R.styleable.SharedToolBar)
        try {

            inflate(context, R.layout.layout_tool_bar, this)
            // region FindViewById
            textTitle = findViewById(R.id.text_view)
            buttonLeft = findViewById(R.id.button_left)
            buttonRight = findViewById(R.id.button_right)
            // endregion

            // region Title
            textTitle!!.setTextColor(
                array.getColor(
                    R.styleable.SharedToolBar_shared_titleColor,
                    ActivityCompat.getColor(context, android.R.color.white)
                )
            )
            val title = array.getString(R.styleable.SharedToolBar_shared_title)
            if (!TextUtils.isEmpty(title))
                setTitle(title)
            val textSize = array.getDimension(R.styleable.SharedToolBar_shared_titleSize, -1f)
            if (textSize != -1f)
                textTitle!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            // endregion

            if (array.hasValue(R.styleable.SharedToolBar_shared_background)) {
                setBackgroundResource(
                    array.getResourceId(
                        R.styleable.SharedToolBar_shared_background,
                        ActivityCompat.getColor(context, android.R.color.transparent)
                    )
                )
            }

            // region Button
            val l = array.getResourceId(R.styleable.SharedToolBar_shared_drawableLeft, -1)
            val r = array.getResourceId(R.styleable.SharedToolBar_shared_drawableRight, -1)
            if (l != -1)
                setDrawableLeft(l)
            if (r != -1)
                setDrawableRight(r)
            // endregion

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            array.recycle()
        }
    }

    private fun setTitle(title: String?) {
        textTitle?.text = title
        setBinding(Integer.MIN_VALUE, Integer.MIN_VALUE, null, null)
    }

    fun setTitle(title: String?, isOnly: Boolean = true) {
        if (isOnly)
            textTitle?.text = title
        else {
            setTitle(title)
        }
    }

    fun setTitleColor(color: Int) {
        textTitle?.setTextColor(ActivityCompat.getColor(context, color))
    }

    fun onLeftClickListener(listener: SharedToolBarCallBack?) {
        buttonLeft?.safeClick { listener?.invoke() }
    }

    fun onRightClickListener(listener: SharedToolBarCallBack?) {
        buttonRight?.safeClick { listener?.invoke() }
    }

    fun setDrawableLeft(icon: Int) {
        if (icon == Integer.MIN_VALUE) {
            buttonLeft?.visibility = View.INVISIBLE
        } else {
            buttonLeft?.visibility = View.VISIBLE
            buttonLeft?.setImageResource(icon)
        }
    }

    fun setDrawableRight(icon: Int) {
        if (icon == Integer.MIN_VALUE) {
            buttonRight?.visibility = View.INVISIBLE
        } else {
            buttonRight?.visibility = View.VISIBLE
            buttonRight?.setImageResource(icon)
        }
    }

    fun setBinding(
        drawableLeft: Int,
        drawableRight: Int,
        leftListener: SharedToolBarCallBack?,
        rightListener: SharedToolBarCallBack?
    ) {
        onLeftClickListener(leftListener)
        onRightClickListener(rightListener)
        setDrawableLeft(drawableLeft)
        setDrawableRight(drawableRight)
    }

    fun disableLeft() {
        setDrawableLeft(Integer.MIN_VALUE)
        onLeftClickListener {}
    }

    fun disableRight() {
        setDrawableRight(Integer.MIN_VALUE)
        onRightClickListener { }
    }

    fun showProgress(isShow: Boolean) {
        progressBar?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
    }

}
