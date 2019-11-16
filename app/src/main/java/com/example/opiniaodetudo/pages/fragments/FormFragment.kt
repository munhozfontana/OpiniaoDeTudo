package com.example.opiniaodetudo.pages.fragments

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.domain.Review
import com.example.opiniaodetudo.infra.repositories.ReviewRepository
import com.example.opiniaodetudo.pages.ListActivity
import java.io.File

class FormFragment : Fragment() {

    private lateinit var mainView: View

    companion object {
        val TAKE_PICTURE_RESULT = 101
    }

    private var file: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainView = inflater.inflate(R.layout.new_review_form_layout, null)
        val buttonSave = mainView.findViewById<Button>(R.id.button_save)
        val textViewName = mainView.findViewById<TextView>(R.id.input_nome)
        val textViewReview = mainView.findViewById<TextView>(R.id.input_review)
        val reviewToEdit =
            (activity!!.intent?.getSerializableExtra("item") as Review?)?.also { review ->
                textViewName.text = review.name
                textViewReview.text = review.review
            }
        buttonSave.setOnClickListener {
            val name = textViewName.text
            val review = textViewReview.text
            object : AsyncTask<Void, Void, Unit>() {
                override fun doInBackground(vararg params: Void?) {
                    val repository = ReviewRepository(activity!!.applicationContext)
                    if (reviewToEdit == null) {
                        repository.save(name.toString(), review.toString())
                        val i = Intent(activity!!.applicationContext, ListActivity::class.java)
                        startActivity(i)
                    } else {
                        repository.update(
                            Review(
                                reviewToEdit.id,
                                name.toString(),
                                review.toString()
                            )
                        )
                        activity!!.finish()
                    }
                }
            }.execute()
            true
        }
        return mainView
    }

    private fun configurePhotoClick() {
        mainView.findViewById<ImageView>(R.id.photo).setOnClickListener {
            val fileName = "${System.nanoTime()}.jpg"
            file = File(activity!!.filesDir, fileName)
            val uri = FileProvider.getUriForFile(
                activity!!,
                "com.androiddesenv.opiniaodetudo.fileprovider",
                file!!
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, TAKE_PICTURE_RESULT)
        }
    }

}