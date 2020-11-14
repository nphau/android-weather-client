package au.com.nab.android.shared.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import au.com.nab.android.shared.common.autoCleared

open class BindingSharedFragment<B : ViewDataBinding>(@LayoutRes val layoutId: Int) : SharedFragment() {

    protected var binding by autoCleared<B>()

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                layoutId,
                container,
                false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}