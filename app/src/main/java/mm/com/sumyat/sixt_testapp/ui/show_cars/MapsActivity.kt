package mm.com.sumyat.sixt_testapp.ui.show_cars

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
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
import androidx.viewpager2.widget.ViewPager2

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    val viewModel: MapsViewModel by viewModel()

    val itemAdapter: ItemAdapter by inject()

    val INITIAL_ZOOM = 12f

    override fun onStart() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
        super.onStart()
    }

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
            is BrowseState.Success -> {
                val data = browseState.data as List<Car>
                view_error.visibility = View.GONE
                progress.visibility = View.GONE
                if (data != null && data.isNotEmpty()) {
                    itemAdapter.cars = data
                    view_pager.adapter = itemAdapter

                    view_pager.registerOnPageChangeCallback(object :
                        ViewPager2.OnPageChangeCallback() {

                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            val car = data[position]
                            val location = LatLng(car.latitude, car.longitude)
                            mMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    location,
                                    INITIAL_ZOOM
                                )
                            )
                        }

                    })
                    showMarkers(data.map {
                        CommonMarker(
                            it.name,
                            LatLng(it.latitude, it.longitude)
                        )
                    })
                } else {
                    view_empty.visibility = View.VISIBLE
                }

            }
            is BrowseState.Error -> setupScreenForError(browseState.errorMessage)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
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
                BitmapDescriptorFactory.fromResource(R.drawable.location)
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


        with(mMap) {
            setOnMarkerClickListener(this@MapsActivity)
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        view_pager.currentItem = viewModel.findPagerPosition(marker?.title)
        return false
    }

}
