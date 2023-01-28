package mz.co.commandline.grocery.rent.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentDevolutionsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.adapter.DevolutionItemAdapter
import mz.co.commandline.grocery.rent.delegate.RentDelegate
import mz.co.commandline.grocery.rent.dto.RentItemDTO


class DevolutionsFragment : BaseFragment(), ClickListner<RentItemDTO>, View.OnClickListener {

    private var _binding: FragmentDevolutionsBinding? = null
    private val binding get() = _binding!!

    private var delegate: RentDelegate? = null


    override fun getResourceId(): Int {
        return R.layout.fragment_devolutions
    }

    override fun onCreateView() {
        delegate = activity as RentDelegate

        val devolutionsRecyclerView = binding.devolutionsRecyclerView

        val adapter = DevolutionItemAdapter(activity, delegate!!.rent.rentItemsDTO)
        adapter.setItemClickListner(this)

        devolutionsRecyclerView.adapter = adapter
        devolutionsRecyclerView.addItemDecoration(DividerItemDecoration(devolutionsRecyclerView.context, DividerItemDecoration.VERTICAL))

        binding.devolutionsBtn.setOnClickListener(this)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentDevolutionsBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getTitle(): String {
        return getString(R.string.devolution_guide)
    }

    override fun onClickListner(rentItemDTO: RentItemDTO) {
        delegate!!.selectedRentItem(rentItemDTO)
    }

    override fun onClick(view: View?) {
        delegate?.issueReturnGuide()
    }
}