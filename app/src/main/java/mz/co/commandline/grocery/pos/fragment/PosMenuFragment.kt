package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.adapter.MenuAdapter
import mz.co.commandline.grocery.databinding.FragmentPosMenuBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.menu.MenuItem
import mz.co.commandline.grocery.pos.delegate.PosDelegate

class PosMenuFragment : BaseFragment(), ClickListner<MenuItem> {

    private var _binding: FragmentPosMenuBinding? = null
    private val binding get() = _binding!!

    private var delegate: PosDelegate? = null

    override fun onCreateView() {
        delegate = activity as PosDelegate

        val menuItems = listOf(
            MenuItem(R.string.pos_manage_table, R.mipmap.ic_table),
            MenuItem(R.string.pos_manage_dept, R.mipmap.ic_dept),
        )

        val adapter = MenuAdapter(activity, menuItems)

        adapter.setItemClickListner(this)

        binding.posMenuRecycleview.adapter = adapter
    }

    override fun getResourceId(): Int {
       return R.layout.fragment_pos_menu
    }

    override fun getTitle(): String {
        return getString(R.string.pos_menu)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPosMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickListner(menuItem: MenuItem?) {
        delegate?.selectedPosMenuItem(menuItem!!.iconId)
    }
}