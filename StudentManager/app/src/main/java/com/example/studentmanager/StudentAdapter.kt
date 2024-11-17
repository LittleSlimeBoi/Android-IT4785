package com.example.studentmanager

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(private val students: MutableList<StudentModel>,
                     private val context: Context,
                     private val view: RecyclerView)
                     : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder(itemView: View, private val adapter: StudentAdapter): RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)

        init{
            imageEdit.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION)
                    adapter.ShowEditDialog(position)
            }
            imageRemove.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    adapter.ShowDeleteDialog(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
            parent, false)
        return StudentViewHolder(itemView, this)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.textStudentName.text = student.studentName
        holder.textStudentId.text = student.studentId
    }

    fun ShowEditDialog(position: Int){
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_student_dialog)
        val title = dialog.findViewById<TextView>(R.id.title)
        val editName = dialog.findViewById<EditText>(R.id.edit_name)
        val editId = dialog.findViewById<EditText>(R.id.edit_id)

        if(position >= 0){
            title.text = "Sửa thông tin:"
            editName.text.append(students[position].studentName)
            editId.text.append(students[position].studentId)
        }else{
            title.text = "Thêm mới"
        }

        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            val newName = editName.text.toString()
            val newId = editId.text.toString()
            if(position >= 0){
                students[position].studentName = newName
                students[position].studentId = newId
                notifyItemChanged(position)
            }else{
                val newStudent = StudentModel(newName, newId)
                students.add(newStudent)
                notifyItemChanged(position)
            }
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener{
            dialog.dismiss()
        }
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    var isUndoClicked = false
    fun ShowDeleteDialog(position: Int){
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Xóa bản ghi sinh viên")
        dialogBuilder.setMessage("Bạn có muốn xóa bản ghi của sinh viên này không?")
        dialogBuilder.setPositiveButton("Đồng ý") {_, _ ->
            val deletedStudent = students[position]
            students.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, students.size - position)

            isUndoClicked = false
            Snackbar.make(view, "Xóa bản ghi sinh viên", Snackbar.LENGTH_LONG)
                .setAction("Hoàn tác") {
                    students.add(position, deletedStudent)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, students.size - position)
                }.show()
        }
        dialogBuilder.setNegativeButton("Hủy", null)
        val dialog = dialogBuilder.create()
        dialog.show()
    }
}