package hamm.android.project.model

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.HORIZONTAL
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hamm.android.project.R
import hamm.android.project.adapters.ImageAdapter
import hamm.android.project.viewmodels.RestaurantImagesFragmentViewModel

class RestaurantImagesFragment : Fragment(), ImageAdapter.Listener {

    private val args: RestaurantImagesFragmentArgs by navArgs()
    private lateinit var mViewModel: RestaurantImagesFragmentViewModel
    private lateinit var mViewPagerAdapter: ImageAdapter

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         mViewModel = ViewModelProvider(this).get(RestaurantImagesFragmentViewModel::class.java)
         mViewModel.images = args.ext.images
        return inflater.inflate(R.layout.fragment_restaurant_images, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.let {view ->
            val recyclerView = view.findViewById<RecyclerView>(R.id.view_pager_restaurant_images)
            mViewPagerAdapter = ImageAdapter(this)

            recyclerView.adapter = mViewPagerAdapter
            recyclerView.layoutManager =LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


            mViewPagerAdapter.submitList(mViewModel.images)
        }
    }

    override fun onImageRemove(image: RestaurantPhoto) {
        mViewModel.images = mViewModel.images.filter{ it != image }
        mViewPagerAdapter.submitList(mViewModel.images)
    }
}