package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentPosBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.pos.delegate.PosDelegate


class PosFragment : BaseFragment() {

    private var _binding: FragmentPosBinding? = null
    private val binding get() = _binding!!

    override fun getResourceId(): Int {
        return R.layout.fragment_pos
    }

    override fun onCreateView() {
        val delegate = activity as PosDelegate

        binding.fragmentPosAddBtn.setOnClickListener { delegate.openTable() }
    }

    override fun getTitle(): String {
        return getString(R.string.sales)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}