package com.example.projekmobileyohana

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar
import java.util.Locale

class CalendarWorkshopFragment : Fragment() {

    private data class WorkshopSchedule(
        val id: Long,
        val name: String,
        val date: String,
        val time: String,
        val location: String
    )

    private val schedules = mutableListOf<WorkshopSchedule>()

    private lateinit var scheduleList: TextView
    private lateinit var selectedDateTitle: TextView
    private lateinit var calendarView: CalendarView
    private lateinit var mainLayout: LinearLayout

    private var selectedDate = ""

    private val isAdmin: Boolean
        get() = requireContext()
            .getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            .getString("userRole", "customer") == "admin"

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val context = requireContext()

        loadSchedules()

        mainLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            setBackgroundColor(Color.rgb(255, 249, 232))
        }

        val title = TextView(context).apply {
            text = "Kalender Workshop"
            textSize = 24f
            setTextColor(Color.rgb(128, 85, 45))
            gravity = Gravity.CENTER
            setPadding(0, 16, 0, 20)
        }

        calendarView = CalendarView(context)

        selectedDateTitle = TextView(context).apply {
            text = "Jadwal Workshop"
            textSize = 19f
            setTextColor(Color.rgb(128, 85, 45))
            setPadding(0, 24, 0, 12)
        }

        scheduleList = TextView(context).apply {
            textSize = 16f
            setTextColor(Color.DKGRAY)
            setPadding(12, 12, 12, 12)
        }

        mainLayout.addView(title)

        mainLayout.addView(
            calendarView,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        // Tombol khusus Admin
        if (isAdmin) {
            val addButton = Button(context).apply {
                text = "Tambah Jadwal Workshop"
                setOnClickListener {
                    showScheduleForm()
                }
            }
            mainLayout.addView(addButton)
        }

        mainLayout.addView(selectedDateTitle)
        mainLayout.addView(scheduleList)

        calendarView.setOnDateChangeListener { _, year, month, day ->
            selectedDate = String.format(
                Locale.US,
                "%04d-%02d-%02d",
                year,
                month + 1,
                day
            )
            showSchedule(year, month, day)
        }

        val today = Calendar.getInstance()
        selectedDate = String.format(
            Locale.US,
            "%04d-%02d-%02d",
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH) + 1,
            today.get(Calendar.DAY_OF_MONTH)
        )

        showSchedule(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )

        return ScrollView(context).apply {
            addView(mainLayout)
        }
    }

    private fun showSchedule(year: Int, month: Int, day: Int) {

        selectedDateTitle.text = "Jadwal: $day/${month + 1}/$year"

        val dateSchedules = schedules.filter {
            it.date == selectedDate
        }

        if (dateSchedules.isEmpty()) {
            scheduleList.text =
                "Belum ada jadwal workshop pada tanggal ini."
            return
        }

        val context = requireContext()
        scheduleList.text = ""

        dateSchedules.forEach { schedule ->

            val itemLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(12, 12, 12, 12)
            }

            val details = TextView(context).apply {
                text = """
                    ${schedule.name}
                    Jam: ${schedule.time}
                    Lokasi: ${schedule.location}
                """.trimIndent()
                textSize = 16f
                setTextColor(Color.DKGRAY)
            }

            itemLayout.addView(details)

            if (isAdmin) {
                val buttonLayout = LinearLayout(context).apply {
                    orientation = LinearLayout.HORIZONTAL
                }

                val editButton = Button(context).apply {
                    text = "Edit"
                    setOnClickListener {
                        showScheduleForm(schedule)
                    }
                }

                val deleteButton = Button(context).apply {
                    text = "Hapus"
                    setOnClickListener {
                        deleteSchedule(schedule)
                    }
                }

                buttonLayout.addView(editButton)
                buttonLayout.addView(deleteButton)
                itemLayout.addView(buttonLayout)
            }

            mainLayout.addView(itemLayout)
        }
    }

    private fun showScheduleForm(
        oldSchedule: WorkshopSchedule? = null
    ) {

        val context = requireContext()

        val formLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 8, 24, 8)
        }

        val nameInput = EditText(context).apply {
            hint = "Nama Workshop"
            setText(oldSchedule?.name ?: "")
        }

        val dateInput = EditText(context).apply {
            hint = "Tanggal (YYYY-MM-DD)"
            setText(oldSchedule?.date ?: selectedDate)
            isFocusable = false
        }

        val timeInput = EditText(context).apply {
            hint = "Jam (contoh: 10.00 WIB)"
            setText(oldSchedule?.time ?: "")
        }

        val locationInput = EditText(context).apply {
            hint = "Lokasi Workshop"
            setText(oldSchedule?.location ?: "")
        }

        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()

            if (dateInput.text.isNotEmpty()) {
                val parts = dateInput.text.toString().split("-")
                if (parts.size == 3) {
                    calendar.set(
                        parts[0].toIntOrNull() ?: calendar.get(Calendar.YEAR),
                        (parts[1].toIntOrNull() ?: 1) - 1,
                        parts[2].toIntOrNull() ?: 1
                    )
                }
            }

            DatePickerDialog(
                context,
                { _, year, month, day ->
                    dateInput.setText(
                        String.format(
                            Locale.US,
                            "%04d-%02d-%02d",
                            year,
                            month + 1,
                            day
                        )
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        formLayout.addView(nameInput)
        formLayout.addView(dateInput)
        formLayout.addView(timeInput)
        formLayout.addView(locationInput)

        val dialog = AlertDialog.Builder(context)
            .setTitle(
                if (oldSchedule == null) {
                    "Tambah Jadwal Workshop"
                } else {
                    "Edit Jadwal Workshop"
                }
            )
            .setView(formLayout)
            .setNegativeButton("Batal", null)
            .setPositiveButton("Simpan", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener {

                    val name = nameInput.text.toString().trim()
                    val date = dateInput.text.toString().trim()
                    val time = timeInput.text.toString().trim()
                    val location = locationInput.text.toString().trim()

                    if (
                        name.isEmpty() ||
                        date.isEmpty() ||
                        time.isEmpty() ||
                        location.isEmpty()
                    ) {
                        nameInput.error = "Semua data wajib diisi"
                        return@setOnClickListener
                    }

                    val dateParts = date.split("-")
                    val validDate = if (dateParts.size == 3) {
                        val year = dateParts[0].toIntOrNull()
                        val month = dateParts[1].toIntOrNull()
                        val day = dateParts[2].toIntOrNull()

                        if (year != null && month != null && day != null) {
                            val check = Calendar.getInstance()
                            check.isLenient = false
                            try {
                                check.set(year, month - 1, day)
                                check.time
                                true
                            } catch (e: Exception) {
                                false
                            }
                        } else false
                    } else false

                    if (!validDate) {
                        dateInput.error = "Format tanggal tidak valid"
                        return@setOnClickListener
                    }

                    if (oldSchedule != null) {
                        schedules.removeAll { it.id == oldSchedule.id }
                    }

                    schedules.add(
                        WorkshopSchedule(
                            id = oldSchedule?.id ?: System.currentTimeMillis(),
                            name = name,
                            date = date,
                            time = time,
                            location = location
                        )
                    )

                    saveSchedules()
                    refreshSchedule()

                    dialog.dismiss()
                }
        }

        dialog.show()
    }

    private fun deleteSchedule(schedule: WorkshopSchedule) {

        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Jadwal")
            .setMessage("Apakah jadwal ${schedule.name} ingin dihapus?")
            .setNegativeButton("Batal", null)
            .setPositiveButton("Hapus") { _, _ ->
                schedules.removeAll { it.id == schedule.id }
                saveSchedules()
                refreshSchedule()
            }
            .show()
    }

    private fun refreshSchedule() {
        val parts = selectedDate.split("-")
        if (parts.size == 3) {
            showSchedule(
                parts[0].toInt(),
                parts[1].toInt() - 1,
                parts[2].toInt()
            )
        }
    }

    private fun saveSchedules() {

        val jsonArray = JSONArray()

        schedules.forEach { schedule ->
            val item = JSONObject().apply {
                put("id", schedule.id)
                put("name", schedule.name)
                put("date", schedule.date)
                put("time", schedule.time)
                put("location", schedule.location)
            }
            jsonArray.put(item)
        }

        requireContext()
            .getSharedPreferences("workshop_schedule_pref", Context.MODE_PRIVATE)
            .edit()
            .putString("schedules", jsonArray.toString())
            .apply()
    }

    private fun loadSchedules() {

        val preferences = requireContext()
            .getSharedPreferences("workshop_schedule_pref", Context.MODE_PRIVATE)

        val savedData = preferences.getString("schedules", null)

        if (savedData != null) {
            try {
                val jsonArray = JSONArray(savedData)

                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)

                    schedules.add(
                        WorkshopSchedule(
                            id = item.getLong("id"),
                            name = item.getString("name"),
                            date = item.getString("date"),
                            time = item.getString("time"),
                            location = item.getString("location")
                        )
                    )
                }
            } catch (e: Exception) {
                schedules.clear()
            }
        } else {
            // Jadwal contoh dari kode sebelumnya
            schedules.add(
                WorkshopSchedule(
                    1L,
                    "Clay Flower Workshop",
                    "2026-11-15",
                    "10.00 WIB",
                    "Creative Bee Studio"
                )
            )

            schedules.add(
                WorkshopSchedule(
                    2L,
                    "Miniature Clay Workshop",
                    "2026-11-22",
                    "13.00 WIB",
                    "Creative Bee Studio"
                )
            )

            saveSchedules()
        }
    }
}
