package com.reactivemobile.rainfall.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.reactivemobile.rainfall.R
import com.reactivemobile.rainfall.application.RainfallApplication
import com.reactivemobile.rainfall.presentation.gone
import com.reactivemobile.rainfall.presentation.ui.details.StationDetailsViewModel.StationDetailsEvent.StationDetailsSuccess
import kotlinx.android.synthetic.main.fragment_station_details.*
import javax.inject.Inject

const val EXTRA_STATION_ID = "EXTRA_STATION_ID"

class StationDetailsFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: StationDetailsViewModelFactory

    private val viewModel: StationDetailsViewModel by viewModels { viewModelFactory.apply { bind(this@StationDetailsFragment) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.application as RainfallApplication).appComponent.inject(this)
        return inflater.inflate(R.layout.fragment_station_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        viewModel.fetchStationDetails()
    }

    private fun setupObservers() {
        viewModel.stationDetails.observe(viewLifecycleOwner, { stationDetailsEvent ->
            progressBar.gone()
            if (stationDetailsEvent is StationDetailsSuccess) {
                with(stationDetailsEvent)
                {
                    name.text = depth?.let { getString(R.string.latest_reading, depth, unit, date) } ?: getString(R.string.reading_unavailable)
                }
            } else {
                name.text = getString(R.string.error_loading_details)
            }
        })
    }

    companion object {
        fun newInstance(stationId: String) = StationDetailsFragment().apply { arguments = Bundle().apply { putString(EXTRA_STATION_ID, stationId) } }
    }
}