package com.example.opiniaodetudo.pages.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.pages.EditReviewViewModel
import com.github.chrisbanes.photoview.PhotoView
import java.io.File

class ShowReviewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_review, null).apply {
            val imageView = findViewById<ImageView>(R.id.expandedImage)
            val reviewViewModel =
                ViewModelProviders.of(activity!!).get(EditReviewViewModel::class.java)

            val value = reviewViewModel?.data?.value

            value?.photoPath?.apply {
                val bitmap = BitmapFactory.decodeFile(
                    File(
                        activity!!.filesDir,
                        this
                    ).absolutePath
                )
                imageView.setImageBitmap(bitmap)
            }

            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            toolbar.title = value?.name

            val activity = (activity!! as AppCompatActivity)
            activity.setSupportActionBar(toolbar)

            activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            val textView = findViewById<TextView>(R.id.review)
            textView.text = value?.review

            value?.photoPath?.apply {
                val bitmap = BitmapFactory.decodeFile(File(activity!!.filesDir, this).absolutePath)
                imageView.setImageBitmap(bitmap)
                configureImageClick(imageView, inflater, bitmap)
            }
        }

    }

    private fun configureImageClick(
        imageView: ImageView,
        inflater: LayoutInflater,
        bitmap: Bitmap?
    ) {
        imageView.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            val inflate = inflater.inflate(R.layout.image_dialog, null)
            val photoView = inflate.findViewById<PhotoView>(R.id.imageView)
            photoView.setImageBitmap(bitmap)
            builder.setView(photoView)
            builder.create().show()
        }
    }

}
