package com.maverickai.meetassist.feature_list.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.maverickai.meetassist.R
import com.maverickai.meetassist.databinding.FragmentFirstBinding
import com.maverickai.meetassist.feature_list.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class NotesListFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerNotes.adapter =
            NotesRecyclerviewAdapter(emptyList(), object : OnNotesClickListener {
                override fun onNoteClicked(note: Note) {
                    findNavController().navigate(
                        R.id.action_FirstFragment_to_SecondFragment,
                        Bundle().apply {
                            putParcelable("Note", note)

                        }
                    )

                }
            })
        binding.fab.setOnClickListener { view ->
            activity?.findNavController(R.id.nav_host_fragment_content_main)
                ?.navigate(R.id.SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}