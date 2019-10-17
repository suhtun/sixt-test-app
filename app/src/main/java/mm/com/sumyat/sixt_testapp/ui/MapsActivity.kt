package mm.com.sumyat.sixt_testapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import mm.com.sumyat.sixt_testapp.R
import mm.com.sumyat.sixt_testapp.network.model.Car
import mm.com.sumyat.sixt_testapp.ui.util.BrowseState
import org.koin.android.viewmodel.ext.android.viewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val viewModel: MapsViewModel by viewModel()

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
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(list: List<Car>) {

    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
