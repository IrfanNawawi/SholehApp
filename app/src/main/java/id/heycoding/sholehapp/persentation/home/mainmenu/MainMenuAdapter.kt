package id.heycoding.sholehapp.persentation.home.mainmenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sholehapp.data.source.entity.model.MainMenu
import id.heycoding.sholehapp.databinding.ItemMenuMainBinding
import id.heycoding.sholehapp.persentation.home.HomeCallback

class MainMenuAdapter(private val callback: HomeCallback) :
    RecyclerView.Adapter<MainMenuAdapter.ViewHolder>() {
    private val listMainMenu = ArrayList<MainMenu>()

    inner class ViewHolder(private val binding: ItemMenuMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onMainMenu: MainMenu) {
            binding.apply {
                Glide.with(itemView.context).load(onMainMenu.imageMenu).into(imgMenuHome)
                tvMenuHome.text = onMainMenu.titleMenu

                itemView.setOnClickListener {
                    callback.onNavigateMainMenu(
                        onMainMenu.idMenu
                    )
                }
            }
        }
    }

    fun setOnMainMenu(onMainMenu: List<MainMenu>) {
        listMainMenu.clear()
        listMainMenu.addAll(onMainMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMenuMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMainMenu[position])
    }

    override fun getItemCount(): Int = listMainMenu.size
}