package dev.skymansandy.gocorona.presentation.main.india.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.skymansandy.gocorona.databinding.ItemCovidStatRowBinding
import kotlinx.android.parcel.Parcelize
import java.text.NumberFormat
import javax.inject.Inject

class CovidStatListAdapter(
    private val covidStatClickListener: CovidStatClickListener,
    private val covidStatListType: CovidStatListType
) : PagedListAdapter<CovidStat, StatRowViewHolder>(StatRowDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatRowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCovidStatRowBinding.inflate(inflater, parent, false)
        val holder = StatRowViewHolder(binding)
        binding.root.setOnClickListener {
            getItem(holder.adapterPosition)?.let {
                when (covidStatListType) {
                    CovidStatListType.DISTRICT -> covidStatClickListener.onDistrictClicked(it)
                    CovidStatListType.STATE -> covidStatClickListener.onStateClicked(it)
                    CovidStatListType.COUNTRY -> covidStatClickListener.onCountryClicked(it)
                }
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: StatRowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

/**
 * StatRow click listener
 */
interface CovidStatClickListener {
    fun onCountryClicked(covidStat: CovidStat)
    fun onStateClicked(covidStat: CovidStat)
    fun onDistrictClicked(covidStat: CovidStat)
}

/**
 * StatRow ViewHolder
 */
class StatRowViewHolder(
    private val binding: ItemCovidStatRowBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(covidStat: CovidStat?) {
//        binding.stat = covidStat
        covidStat?.let {
            val numberFormat = NumberFormat.getInstance()
            binding.tvTitle.text = it.name
            binding.tvConfirmed.text = numberFormat.format(it.confirmed)
            binding.tvActive.text = numberFormat.format(it.active)
            binding.tvRecovered.text = numberFormat.format(it.recovered)
            binding.tvDeceased.text = numberFormat.format(it.deceased)
        }
    }
}

/**
 * StatRow Diff Callback
 */
class StatRowDiffCallback @Inject constructor() : DiffUtil.ItemCallback<CovidStat>() {
    override fun areItemsTheSame(oldItem: CovidStat, newItem: CovidStat) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: CovidStat, newItem: CovidStat) = oldItem == newItem
}

@Parcelize
data class CovidStat(
    val code: String = "",
    val name: String = "",
    val confirmed: Int = 0,
    val active: Int = 0,
    val recovered: Int = 0,
    val deceased: Int = 0
) : Parcelable

enum class CovidStatListType { DISTRICT, STATE, COUNTRY }