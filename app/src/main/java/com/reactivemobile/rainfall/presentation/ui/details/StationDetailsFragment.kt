package com.reactivemobile.rainfall.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.reactivemobile.rainfall.R
import com.reactivemobile.rainfall.presentation.gone
import com.reactivemobile.rainfall.presentation.ui.details.StationDetailsViewModel.StationDetailsEvent.StationDetailsSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_station_details.*

const val EXTRA_STATION_ID = "EXTRA_STATION_ID"

@AndroidEntryPoint
class StationDetailsFragment : DialogFragment() {

    private val viewModel by viewModels<StationDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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