package mm.com.sumyat.sixt_testapp.ui.show_cars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mm.com.sumyat.sixt_testapp.R
import mm.com.sumyat.sixt_testapp.network.model.Car

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    var cars: List<Car> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = cars[position]

        holder.infoTxt.text = "$ ${car.licensePlate} , ${car.make}"

        Glide.with(holder.itemView.context)
            .load(car.carImageUrl)
            .into(holder.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.car_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView
        var infoTxt: TextView

        init {
            imageView = view.findViewById(R.id.img)
            infoTxt = view.findViewById(R.id.txt_info)
        }
    }

}