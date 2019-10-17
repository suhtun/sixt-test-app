package mm.com.sumyat.sixt_testapp.ui.util

sealed class BrowseState(val resourceState: ResourceState,
                         val data: List<Any>? = null,
                         val errorMessage: String? = null) {

    data class Success(private val bufferoos: List<Any>): BrowseState(ResourceState.SUCCESS,
            bufferoos)

    data class Error(private val message: String? = null): BrowseState(ResourceState.SUCCESS,
            errorMessage = message)

    object Loading: BrowseState(ResourceState.LOADING)
}