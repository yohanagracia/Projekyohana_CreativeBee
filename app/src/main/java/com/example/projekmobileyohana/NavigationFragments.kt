package com.example.projekmobileyohana

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

private fun createBeePage(
    fragment: Fragment,
    title: String,
    description: String,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null,
    showLogout: Boolean = false
): View {
    val context = fragment.requireContext()

    return LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        setPadding(24, 24, 24, 24)
        setBackgroundColor(0xFFFFF9E8.toInt())

        addView(TextView(context).apply {
            text = "🐝"
            textSize = 60f
            gravity = Gravity.CENTER
        })

        addView(TextView(context).apply {
            text = title
            textSize = 28f
            setTextColor(0xFF80552D.toInt())
            gravity = Gravity.CENTER
            setTypeface(null, android.graphics.Typeface.BOLD)
        })

        addView(TextView(context).apply {
            text = description
            textSize = 16f
            setTextColor(0xFF80552D.toInt())
            gravity = Gravity.CENTER
            setPadding(0, 12, 0, 20)
        })

        if (buttonText != null && onButtonClick != null) {
            addView(Button(context).apply {
                text = buttonText
                setOnClickListener { onButtonClick() }
            })
        }

        if (showLogout) {
            addView(Button(context).apply {
                text = "Logout"

                setOnClickListener {
                    AlertDialog.Builder(context)
                        .setTitle("Konfirmasi Logout")
                        .setMessage("Apakah kamu yakin ingin keluar?")
                        .setNegativeButton("Tidak", null)
                        .setPositiveButton("Ya") { _, _ ->
                            fragment.requireActivity()
                                .getSharedPreferences(
                                    "user_pref",
                                    android.content.Context.MODE_PRIVATE
                                )
                                .edit()
                                .clear()
                                .apply()

                            val intent = Intent(
                                context,
                                AuthActivity::class.java
                            ).apply {
                                flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }

                            fragment.startActivity(intent)
                            fragment.requireActivity().finish()
                        }
                        .show()
                }
            })
        }
    }
}

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return createBeePage(
            fragment = this,
            title = "Creative Bee",
            description = "Creative Clay Workshop\nSelamat datang di Creative Bee!"
        )
    }
}

class CatalogFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return createBeePage(
            fragment = this,
            title = "Katalog Produk",
            description = "Lihat koleksi produk clay Creative Bee.",
            buttonText = "Buka Katalog Lengkap",
            onButtonClick = {
                startActivity(
                    Intent(requireContext(), CatalogActivity::class.java)
                )
            }
        )
    }
}

class WorkshopFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return createBeePage(
            fragment = this,
            title = "Workshop Clay",
            description = "Temukan kelas dan kegiatan workshop clay.",
            buttonText = "Lihat Workshop",
            onButtonClick = {
                startActivity(
                    Intent(requireContext(), WorkshopActivity::class.java)
                )
            }
        )
    }
}

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return createBeePage(
            fragment = this,
            title = "Profil",
            description = "Creative Bee By Carla\nInformasi aplikasi dan pengembang.",
            buttonText = "Profil Developer",
            onButtonClick = {
                startActivity(
                    Intent(
                        requireContext(),
                        ProfileDeveloperActivity::class.java
                    )
                )
            },
            showLogout = true
        )
    }
}
