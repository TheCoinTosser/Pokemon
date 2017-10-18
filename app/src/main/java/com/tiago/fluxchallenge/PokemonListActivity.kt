package com.tiago.fluxchallenge

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tiago.fluxchallenge.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import kotlinx.android.synthetic.main.pokemon_list.*
import kotlinx.android.synthetic.main.pokemon_list_content.view.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [PokemonDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class PokemonListActivity : AppCompatActivity() {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private var mTwoPane: Boolean = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_pokemon_list)

		setSupportActionBar(toolbar)
		toolbar.title = title

		if (pokemon_detail_container != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-w900dp).
			// If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true
		}

		setupRecyclerView(pokemon_list)
	}

	private fun setupRecyclerView(recyclerView: RecyclerView) {
		recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane)
	}

	class SimpleItemRecyclerViewAdapter(private val mParentActivity: PokemonListActivity,
										private val mValues: List<DummyContent.DummyItem>,
										private val mTwoPane: Boolean) :
			RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

		private val mOnClickListener: View.OnClickListener

		init {
			mOnClickListener = View.OnClickListener { v ->
				val item = v.tag as DummyContent.DummyItem
				if (mTwoPane) {
					val fragment = PokemonDetailFragment().apply {
						arguments = Bundle()
						arguments.putString(PokemonDetailFragment.ARG_ITEM_ID, item.id)
					}
					mParentActivity.supportFragmentManager
							.beginTransaction()
							.replace(R.id.pokemon_detail_container, fragment)
							.commit()
				} else {
					val intent = Intent(v.context, PokemonDetailActivity::class.java).apply {
						putExtra(PokemonDetailFragment.ARG_ITEM_ID, item.id)
					}
					v.context.startActivity(intent)
				}
			}
		}

		override fun onCreateViewHolder(parent: ViewGroup,
										viewType: Int): ViewHolder {
			val view = LayoutInflater.from(parent.context)
					.inflate(R.layout.pokemon_list_content, parent, false)
			return ViewHolder(view)
		}

		override fun onBindViewHolder(holder: ViewHolder,
									  position: Int) {
			val item = mValues[position]
			holder.mIdView.text = item.id
			holder.mContentView.text = item.content

			with(holder.itemView) {
				tag = item
				setOnClickListener(mOnClickListener)
			}
		}

		override fun getItemCount(): Int {
			return mValues.size
		}

		inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
			val mIdView: TextView = mView.id_text
			val mContentView: TextView = mView.content
		}
	}
}
