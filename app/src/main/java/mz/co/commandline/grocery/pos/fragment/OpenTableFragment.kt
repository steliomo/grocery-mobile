package mz.co.commandline.grocery.pos.fragment

import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.generics.fragment.BaseFragment


class OpenTableFragment : BaseFragment() {


    override fun getResourceId(): Int {
        return R.layout.fragment_open_table
    }

    override fun onCreateView() {
    }

    override fun getTitle(): String {
        return getString(R.string.open_table)
    }
}