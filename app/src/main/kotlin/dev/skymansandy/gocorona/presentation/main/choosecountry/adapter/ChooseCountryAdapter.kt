package dev.skymansandy.gocorona.presentation.main.choosecountry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.skymansandy.gocorona.databinding.ItemCountryBinding
import dev.skymansandy.gocorona.presentation.main.home.adapter.CovidStat
import javax.inject.Inject

class CountryItemAdapter(
    private val countryClickListener: CountryClickListener
) : ListAdapter<CountryItem, CountryViewHolder>(
    CountryDiffCallBack()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CountryViewHolder(
            ItemCountryBinding.inflate(inflater, parent, false),
            countryClickListener
        )
    }

    override fun onBindViewHolder(
        holder: CountryViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}

/**
 * Country click listener
 */
interface CountryClickListener {
    fun onCountryClick(covidStat: CovidStat)
}

/**
 * Country ViewHolder
 */
class CountryViewHolder(
    private val binding: ItemCountryBinding,
    private val countryClickListener: CountryClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            binding.country?.let { country ->
                countryClickListener.onCountryClick(
                    CovidStat(
                        name = country.name,
                        code = country.code
                    )
                )
            }
        }
    }

    fun bind(countryItem: CountryItem) {
        binding.country = countryItem
    }
}

/**
 * Country Diff Callback
 */
class CountryDiffCallBack @Inject constructor() :
    DiffUtil.ItemCallback<CountryItem>() {
    override fun areItemsTheSame(
        oldItem: CountryItem,
        newItem: CountryItem
    ) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: CountryItem,
        newItem: CountryItem
    ) = oldItem == newItem
}

data class CountryItem(
    val code: String = "",
    val name: String = "",
    val flag: String = ""
)