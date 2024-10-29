import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.SinhVien

class SinhVienAdapter(private var sinhVienList: List<SinhVien>) : RecyclerView.Adapter<SinhVienAdapter.SinhVienViewHolder>() {

    class SinhVienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHoTen: TextView = itemView.findViewById(R.id.tvHoTen)
        val tvMSSV: TextView = itemView.findViewById(R.id.tvMSSV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinhVienViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sinhvien, parent, false)
        return SinhVienViewHolder(view)
    }

    override fun onBindViewHolder(holder: SinhVienViewHolder, position: Int) {
        val sinhVien = sinhVienList[position]
        holder.tvHoTen.text = sinhVien.hoTen
        holder.tvMSSV.text = sinhVien.mssv
    }

    override fun getItemCount() = sinhVienList.size

    fun updateList(newList: List<SinhVien>) {
        sinhVienList = newList
        notifyDataSetChanged()
    }
}
