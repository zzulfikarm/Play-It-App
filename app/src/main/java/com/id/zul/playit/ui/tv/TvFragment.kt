package com.id.zul.playit.ui.tv

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.id.zul.playit.R
import com.id.zul.playit.adapter.tv.TvItemAdapter
import com.id.zul.playit.model.tv.Tv
import com.id.zul.playit.ui.detail.DetailActivity
import com.id.zul.playit.viewmodel.factory.ViewModelFactory
import com.id.zul.playit.viewmodel.ui.tv.TvViewModel
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class TvFragment : Fragment() {

    private lateinit var viewModel: TvViewModel
    private lateinit var adapterMovie: TvItemAdapter
    private lateinit var shimmer: SkeletonScreen

    private var listData: MutableList<Tv> = mutableListOf()

    private var page = 1

    companion object {
        fun getInstance(): Fragment {
            return TvFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            initializeToolbar()
            viewModel = initializeViewModel(requireActivity())
            setRecyclerView(view)

            getMovie()
        }

    }

    private fun initializeToolbar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        toolbar.title = resources.getString(R.string.tv_toolbar_title)
    }

    private fun initializeViewModel(activity: FragmentActivity): TvViewModel {
        val factory = ViewModelFactory.getInstance()
        return ViewModelProviders.of(activity, factory).get(TvViewModel::class.java)
    }

    private fun setShimmer() {
        shimmer = Skeleton.bind(rv_tv_show)
            .adapter(adapterMovie)
            .load(R.layout.skeleton_item_list)
            .color(R.color.shimmerColor)
            .angle(45)
            .frozen(false)
            .duration(1000)
            .show()
    }

    private fun getMovie() {
        setShimmer()
        viewModel.getOnAir(page).observe(
            this,
            Observer {
                if (it.isNullOrEmpty())
                    toast("Check your internet connection")
                else {
                    setDataToViews(it)
                    shimmer.hide()
                }
            }
        )
    }

    private fun setRecyclerView(view: View) {
        adapterMovie = TvItemAdapter(view.context) {
            startActivity<DetailActivity>(
                "identify" to "tv",
                "data_id" to it.id
            )
        }

        if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            rv_tv_show.layoutManager = GridLayoutManager(context, 4)
        else
            rv_tv_show.layoutManager = GridLayoutManager(context, 2)

        rv_tv_show.adapter = adapterMovie
    }

    private fun setDataToViews(data: List<Tv>) {
        listData.clear()
        listData.addAll(data)
        adapterMovie.setData(listData)
    }

}