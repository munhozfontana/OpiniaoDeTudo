package com.example.opiniaodetudo.pages.fragments.dialog

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.domain.Review
import com.example.opiniaodetudo.infra.repositories.ReviewRepository
import com.example.opiniaodetudo.pages.EditReviewViewModel

class EditDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.Cus)
        val view = inflater.inflate(R.layout.new_review_form_layout, null)

        populateView(view)
        configureSaveButton(view)
        return view
    }

    private fun configureSaveButton(view: View) {
        val textName = view.findViewById<EditText>(R.id.input_nome)
        val textReview = view.findViewById<EditText>(R.id.input_review)
        val button = view.findViewById<Button>(R.id.button_save)
        val viewModel = ViewModelProviders.of(activity!!).get(EditReviewViewModel::class.java)
        var review = viewModel.data.value!!
        button.setOnClickListener {
            val review = Review(review.id, textName.text.toString(), textReview.text.toString())

            object : AsyncTask<Void, Void, Unit>() {
                override fun doInBackground(vararg params: Void?) {
                    val repository = ReviewRepository(activity!!.applicationContext)
                        repository.update(review.id, review.name, review.review!!)
                }
            }.execute()

            viewModel.data.value = review
            this.dismiss()
        }
    }

    private fun populateView(view: View) {
        val review =
            ViewModelProviders.of(activity!!).get(EditReviewViewModel::class.java).data.value
        view.findViewById<EditText>(R.id.input_nome).setText(review!!.name)
        view.findViewById<EditText>(R.id.input_review).setText(review!!.review)
    }

    override fun onResume() {
        dialog.window?.attributes = dialog.window!!.attributes.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT

        }
        super.onResume()
    }
}


