package mm.com.sumyat.sixt_testapp.ui.show_cars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import mm.com.sumyat.sixt_testapp.R
import mm.com.sumyat.sixt_testapp.network.model.Car
import mm.com.sumyat.sixt_testapp.ui.model.CommonMarker
import mm.com.sumyat.sixt_testapp.ui.util.BrowseState
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    val viewModel: MapsViewModel by viewModel()

    val itemAdapter: ItemAdapter by inject()

    val INITIAL_ZOOM = 12f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.masterLiveData.observe(this, Observer<BrowseState> {
            if (it != null) this.handleDataState(it)
        })

        viewModel.fetchMaster()

        recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = itemAdapter
    }

    private fun updateListView(cars: List<Car>) {
        itemAdapter.cars = cars
        itemAdapter.notifyDataSetChanged()
    }

    private fun handleDataState(browseState: BrowseState) {
        when (browseState) {
            is BrowseState.Loading -> setupScreenForLoadingState()
            is BrowseState.Success -> setupScreenForSuccess(browseState.data as List<Car>)
            is BrowseState.Error -> setupScreenForError(browseState.errorMessage)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<Car>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            showMarkers(data.map { CommonMarker(it.name, LatLng(it.latitude, it.longitude)) })
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun showMarkers(commonMarker: List<CommonMarker>) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(commonMarker[0].location, INITIAL_ZOOM))

        for (marker in commonMarker) {
            newMarker(marker.title, marker.location)
        }
    }

    private fun newMarker(title: String, location: LatLng) {
        mMap.addMarker(
            MarkerOptions().position(location).title(title).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.car)
            )
        )
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

}
