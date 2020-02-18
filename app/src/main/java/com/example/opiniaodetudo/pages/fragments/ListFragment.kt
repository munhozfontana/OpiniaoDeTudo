package com.example.opiniaodetudo.pages.fragments

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.domain.Review
import com.example.opiniaodetudo.infra.repositories.ReviewRepository
import com.example.opiniaodetudo.pages.EditReviewViewModel
import com.example.opiniaodetudo.pages.MainActivity
import com.example.opiniaodetudo.pages.fragments.dialog.EditDialogFragment
import java.util.*

class ListFragment : Fragment() {

    private lateinit var reviews: MutableList<Review>
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.list_review_layout, null)

        val listView = rootView.findViewById<ListView>(R.id.list_recyclerview)
        initList(listView)
        configureOnLongClick(listView)
        configureOnClick(listView)
        configureListObserver()

        return rootView
    }

    private fun configureListObserver() {
        val reviewViewModel = ViewModelProviders.of(activity!!).get(EditReviewViewModel::class.java)
        reviewViewModel.data.observe(this, Observer {
            onResume()
        })
    }

    private fun initList(listView: ListView) {
        object : AsyncTask<Void, Void, ArrayAdapter<Review>>() {
            override fun doInBackground(vararg params: Void?): ArrayAdapter<Review> {

                reviews = ReviewRepository(activity!!.applicationContext).listAll().toMutableList()
                return object : ArrayAdapter<Review>(activity!!, -1, reviews) {
                    override fun getView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val itemView =
                            layoutInflater.inflate(R.layout.review_list_item_layout, null)
                        val item = reviews[position]
                        val textViewName = itemView.findViewById<TextView>(R.id.item_name)
                        val textViewReview = itemView.findViewById<TextView>(R.id.item_review)
                        textViewName.text = item.name
                        textViewReview.text = item.review

                        if (item.thumbnail != null) {
                            val thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
                            val bitmap = BitmapFactory.decodeByteArray(
                                item.thumbnail,
                                0,
                                item.thumbnail.size
                            )
                            thumbnail.setImageBitmap(bitmap)
                        }


                        return itemView
                    }
                }
            }

            override fun onPostExecute(adapter: ArrayAdapter<Review>) {
                listView.adapter = adapter
            }
        }.execute()
    }


    private fun configureOnLongClick(listView: ListView?) {
        listView?.setOnItemLongClickListener { _, view, position, _ ->

            val popupMenu = PopupMenu(activity!!, view)
            popupMenu.inflate(R.menu.list_review_item_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_list_delete -> askForDelete(reviews[position])
                    R.id.item_list_edit -> this@ListFragment.openItemForEdition(reviews[position])
                    R.id.item_list_map -> openMap(reviews[position])
                }
                true
            }

            reviews[position].apply {
                if (latitude != null && longitude != null) {
                    val geocoder = Geocoder(activity!!, Locale.getDefault())
                    for (address in geocoder.getFromLocation(latitude!!, longitude!!, 1)) {
                        Log.d("GEOCODER", address.toString())
                    }
                    val item = popupMenu.menu.findItem(R.id.item_list_map)
                    item.isVisible = true
                }
            }
            popupMenu.show()
            true
        }
    }


      private fun configureOnClick(listView: ListView) {
        listView.setOnItemClickListener { parent, view, position, id ->
            val reviewViewModel =
                ViewModelProviders.of(activity!!).get(EditReviewViewModel::class.java)
            val data = reviewViewModel.data
            data.value = reviews[position]

            (activity!! as MainActivity).navigateWithBackStack(ShowReviewFragment())
        }
    }


    private fun delete(item: Review) {
        object : AsyncTask<Unit, Void, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                ReviewRepository(this@ListFragment.context!!.applicationContext).delete(item)
                reviews.remove(item)
            }

            override fun onPostExecute(result: Unit?) {
                val listView = activity!!.findViewById<ListView>(R.id.list_recyclerview)
                val adapter = listView.adapter as ArrayAdapter<Review>
                adapter.notifyDataSetChanged()
            }
        }.execute()
    }


    private fun openItemForEdition(item: Review) {
        val reviewViewModel = ViewModelProviders.of(activity!!).get(EditReviewViewModel::class.java)
        val data = reviewViewModel.data
        data.value = item

        EditDialogFragment().show(fragmentManager, "edit_dialog")
    }

    override fun onResume() {
        super.onResume()
        object : AsyncTask<Unit, Void, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                reviews.clear()
                reviews.addAll(ReviewRepository(activity!!.applicationContext).listAll())
            }

            override fun onPostExecute(result: Unit?) {
                val listView = rootView.findViewById<ListView>(R.id.list_recyclerview)
                val adapter = listView.adapter as ArrayAdapter<Review>
                adapter.notifyDataSetChanged()
            }
        }.execute()
    }

    private fun askForDelete(item: Review) {
        AlertDialog.Builder(activity)
            .setMessage(R.string.delete_confirmation)
            .setPositiveButton(R.string.ok) { _, _ -> this.delete(item) }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private fun openMap(review: Review) {
        val uri = Uri.parse("geo:${review.latitude},${review.longitude}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        activity!!.startActivity(intent)
    }


}