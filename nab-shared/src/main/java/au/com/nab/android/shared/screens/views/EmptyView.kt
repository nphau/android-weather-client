package au.com.nab.android.shared.screens.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import au.com.nab.android.shared.R
import au.com.nab.android.shared.common.extensions.formatHtml
import au.com.nab.android.shared.common.extensions.onClick

typealias EmptyViewCallBack = () -> Unit

class EmptyView : LinearLayout {

    private var textMessage: TextView? = null
    private var textTitle: TextView? = null
    private var buttonOK: Button? = null
    private var buttonCancel: Button? = null
    private var imageIcon: AppCompatImageView? = null
    private var mOkCallBack: EmptyViewCallBack? = null
    private var mCancelCallBack: EmptyViewCallBack? = null

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
        val array = context.obtainStyledAttributes(attrs, R.styleable.EmptyView)
        try {
            inflate(context, R.layout.view_empty, this)
            //
            buttonOK = findViewById(R.id.button_ok)
            imageIcon = findViewById(R.id.image_view)
            textTitle = findViewById(R.id.text_title)
            textMessage = findViewById(R.id.text_message)
            buttonCancel = findViewById(R.id.button_cancel)
            //
            setTitle(array.getString(R.styleable.EmptyView_shared_title))
            setMessage(array.getString(R.styleable.EmptyView_shared_message))
            setIcon(array.getResourceId(R.styleable.EmptyView_shared_icon, Integer.MIN_VALUE))
            setOkActionTitle(array.getString(R.styleable.EmptyView_shared_action_title))
            setCancelActionTitle(array.getString(R.styleable.EmptyView_shared_cancel_action_title))
            // Auto show
            visibility = if (array.getBoolean(R.styleable.EmptyView_shared_auto_show, false))
                View.VISIBLE
            else
                View.GONE
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            array.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        buttonOK?.onClick {
            mOkCallBack?.invoke()
            visibility = View.INVISIBLE
        }
        buttonCancel?.onClick { mCancelCallBack?.invoke() }
    }

    fun show(okCallBack: EmptyViewCallBack? = null) {
        mOkCallBack = okCallBack
        hide(false)
    }

    fun show(okCallBack: EmptyViewCallBack? = null, cancelCallBack: EmptyViewCallBack? = null) {
        mOkCallBack = okCallBack
        mCancelCallBack = cancelCallBack
        hide(false)
    }

    fun show(message: String, okCallBack: EmptyViewCallBack? = null) {
        setMessage(message)
        hide(false)
        mOkCallBack = okCallBack
    }

    fun setMessage(message: String?) {
        if (message == null) {
            textMessage?.visibility = View.GONE
        } else {
            textMessage?.visibility = View.VISIBLE
            textMessage?.formatHtml(message)
        }
    }

    fun setTitle(title: String?) {
        if (title == null) {
            textTitle?.visibility = View.GONE
        } else {
            textTitle?.visibility = View.VISIBLE
            textTitle?.formatHtml(title)
        }
    }

    fun setIcon(imageRes: Int) {
        if (imageRes == Integer.MIN_VALUE) {
            imageIcon?.visibility = View.GONE
        } else {
            imageIcon?.visibility = View.VISIBLE
            imageIcon?.setImageResource(imageRes)
        }
    }

    fun setOkActionTitle(actionTitle: String? = null) {
        if (actionTitle == null) {
            buttonOK?.visibility = View.GONE
        } else {
            buttonOK?.visibility = View.VISIBLE
            buttonOK?.formatHtml(actionTitle)
        }
    }

    fun setCancelActionTitle(actionTitle: String? = null) {
        if (actionTitle == null) {
            buttonCancel?.visibility = View.GONE
        } else {
            buttonCancel?.visibility = View.VISIBLE
            buttonCancel?.formatHtml(actionTitle)
        }
    }

    fun show() {
        hide(false)
    }

    fun hide() {
        hide(true)
    }

    private fun hide(isHidden: Boolean = true) {
        visibility = if (isHidden) View.GONE else View.VISIBLE
    }
}