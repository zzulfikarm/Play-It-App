package com.id.zul.playit.ui.favorite.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.id.zul.playit.R

class FavoriteTvFragment : Fragment() {

    companion object {
        fun getInstance(): Fragment {
            return FavoriteTvFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false)
    }

}