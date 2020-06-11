package dev.skymansandy.gocorona.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.skymansandy.gocorona.databinding.ItemCovidStatRowBinding
import java.text.NumberFormat
import javax.inject.Inject

class CovidStatAdapter(
    private val covidStatClickListener: CovidStatClickListener
) : ListAdapter<CovidStat, StatRowViewHolder>(StatRowDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatRowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StatRowViewHolder(
            ItemCovidStatRowBinding.inflate(inflater, parent, false),
            covidStatClickListener
        )
    }

    override fun onBindViewHolder(holder: StatRowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

/**
 * StatRow click listener
 */
interface CovidStatClickListener {

}

/**
 * StatRow ViewHolder
 */
class StatRowViewHolder(
    private val binding: ItemCovidStatRowBinding,
    private val covidStatClickListener: CovidStatClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
        }
    }

    fun bind(covidStat: CovidStat?) {
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

data class CovidStat(
    val name: String = "",
    val confirmed: Int = 0,
    val active: Int = 0,
    val recovered: Int = 0,
    val deceased: Int = 0
)