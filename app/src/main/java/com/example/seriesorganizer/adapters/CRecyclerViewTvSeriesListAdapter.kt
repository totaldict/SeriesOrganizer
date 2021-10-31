package com.example.seriesorganizer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seriesorganizer.databinding.TvseriesListItemBinding
import com.example.seriesorganizer.model.CTvSeries

class CRecyclerViewTvSeriesListAdapter  (
    private val tvSeriesList: ArrayList<CTvSeries>,
    private val listener: IClickListener?
) :
    RecyclerView.Adapter<CRecyclerViewTvSeriesListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder( val binding: TvseriesListItemBinding)
        : RecyclerView.ViewHolder(binding.root)
    {
        var tvSeries: CTvSeries? = null
        var index : Int = -1
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = TvseriesListItemBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.binding.tvSeriesName.text = tvSeriesList[position].name
        viewHolder.binding.tvSeriesInfo.text = tvSeriesList[position].description

        viewHolder.tvSeries = tvSeriesList[position]
        viewHolder.index = position
    }

    fun interface IClickListener {
        fun onItemClick(lesson : CTvSeries, index: Int)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = tvSeriesList.size

}