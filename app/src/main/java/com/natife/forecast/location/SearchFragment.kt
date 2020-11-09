package com.natife.forecast.location

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.natife.forecast.R
import com.natife.forecast.location.data.SearchPresentation
import kotlinx.android.synthetic.main.fragment_location_item_list.*
import kotlinx.android.synthetic.main.fragment_location_item_list.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A fragment representing a list of Items.
 */
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by activityViewModels()

    private var dat: ArrayList<SearchPresentation> = ArrayList()
    private lateinit var localAdapter: SearchRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputManager: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.SHOW_FORCED
        )

        back.setOnClickListener {
            findNavController().navigate(R.id.action_locationFragment_to_forecastFragment)
        }


        val mTextWatcher: TextWatcher = object : TextWatcher {
            private var timer = Timer()
            private val DELAY: Long = 500L

            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotBlank()) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            viewModel.getSearchList(s.toString())
                        }
                    }, DELAY)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        }
        editTextAddress.addTextChangedListener(mTextWatcher)

        observe()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_location_item_list, container, false)

        // Set the adapter

        if (view.list is RecyclerView) {
            with(view.list) {
                layoutManager = LinearLayoutManager(context)
                localAdapter = SearchRecyclerViewAdapter(dat) { lat: Double, lon: Double ->

                    findNavController().navigate(
                        SearchFragmentDirections.actionLocationFragmentToForecastFragment(
                            lat.toFloat(),
                            lon.toFloat()
                        )
                    )
                }
                adapter = localAdapter
            }
        }
        return view
    }

    private fun observe() {
        viewModel.searchLiveData.observe(viewLifecycleOwner, { searchData ->
            run {
                val searches: ArrayList<SearchPresentation> = ArrayList()
                for (searchDataItem in searchData) {
                    searches.add(SearchPresentation(searchDataItem))
                }
                dat.clear()
                dat.addAll(searches)

                localAdapter.notifyDataSetChanged()
            }

        })
    }
}