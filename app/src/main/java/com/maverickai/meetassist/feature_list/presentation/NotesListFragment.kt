package com.maverickai.meetassist.feature_list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.maverickai.meetassist.MainActivity
import com.maverickai.meetassist.R
import com.maverickai.meetassist.common.Constants.BUNDLE_KEY_NOTE
import com.maverickai.meetassist.databinding.FragmentNotesListBinding
import com.maverickai.meetassist.feature_list.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class NotesListFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: NotesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "My Notes"
        }
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NotesListViewModel::class.java]
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let { error ->
                Toast.makeText(context, "Error - $error", Toast.LENGTH_SHORT).show()
                binding.textNotesError1.visibility = VISIBLE
                binding.textNotesError2.visibility = VISIBLE
            }
        }
        viewModel.notes.observe(viewLifecycleOwner) {
            it?.let { notes ->
                binding.textNotesError1.visibility = GONE
                binding.textNotesError2.visibility = GONE
                binding.recyclerNotes.adapter =
                    NotesRecyclerviewAdapter(notes, object : OnNotesClickListener {
                        override fun onNoteClicked(note: Note) {
                            findNavController().navigate(
                                R.id.action_FirstFragment_to_SecondFragment,
                                Bundle().apply {
                                    putParcelable(BUNDLE_KEY_NOTE, note)
                                }
                            )
                        }
                    })
            } ?: let {
                binding.textNotesError1.visibility = VISIBLE
                binding.textNotesError2.visibility = VISIBLE
            }
        }
        viewModel.getNotes()
        binding.fab.setOnClickListener { _ ->
            activity?.findNavController(R.id.nav_host_fragment_content_main)
                ?.navigate(R.id.SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}