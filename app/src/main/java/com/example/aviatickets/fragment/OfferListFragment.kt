import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aviatickets.R
import com.example.aviatickets.adapter.OfferListAdapter
import com.example.aviatickets.databinding.FragmentOfferListBinding
import com.example.aviatickets.model.entity.Offer
import com.example.aviatickets.model.service.FakeService
class OfferListFragment : Fragment() {

    companion object {
        fun newInstance() = OfferListFragment()
    }

    private var _binding: FragmentOfferListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: OfferListAdapter by lazy {
        OfferListAdapter()
    }

    private var offerList: List<Offer> = FakeService.offerList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        displayOfferList(offerList)
    }

    private fun setupUI() {
        with(binding) {
            offer_list.layoutManager = LinearLayoutManager(requireContext())
            offer_list.adapter = adapter

            sortRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
                when (checkedId) {
                    R.id.sort_by_price -> {
                        offerList = offerList.sortedBy { it.price }
                        adapter.updateList(offerList)
                    }
                    R.id.sort_by_duration -> {
                        offerList = offerList.sortedBy { it.flight.duration }
                        adapter.updateList(offerList)
                    }
                }
            }
        }
    }


    private fun displayOfferList(list: List<Offer>) {
        adapter.updateList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
