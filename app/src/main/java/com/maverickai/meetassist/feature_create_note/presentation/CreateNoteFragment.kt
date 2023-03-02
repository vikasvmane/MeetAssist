package com.maverickai.meetassist.feature_create_note.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maverickai.meetassist.MainActivity
import com.maverickai.meetassist.R
import com.maverickai.meetassist.common.Constants.BUNDLE_KEY_NOTE
import com.maverickai.meetassist.databinding.FragmentCreateNoteBinding
import com.maverickai.meetassist.feature_list.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class CreateNoteFragment : Fragment() {
    private var speechRecognizer: SpeechRecognizer? = null

    private var _binding: FragmentCreateNoteBinding? = null
    private lateinit var viewModel: CreateNoteViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var noteToSave: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateNoteViewModel::class.java]
        // Checks record permission
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        }
        // Makes the fragment readonly if note is passed by clicking on my notes
        arguments?.let {
            val note: Note? = it.getParcelable(BUNDLE_KEY_NOTE)
            if (note != null) {
                createReadOnlyForm()
                setReadOnlyData(note)
            }
        }
        // Loading indicator
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it == true) binding.progressBar.visibility = VISIBLE
            else binding.progressBar.visibility = GONE
        }
        // Error indicator
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let { error ->
                Toast.makeText(context, "Error - $error", Toast.LENGTH_SHORT).show()
            }
        }
        // ChatGPT api response
        viewModel.gptResponse.observe(viewLifecycleOwner) {
            it?.let { response ->
                // Output is only visible when its a success response
                binding.textOutput.visibility = VISIBLE
                if (!response.choices.isNullOrEmpty()) {
                    binding.tilSpeechDisplay.hint = getString(R.string.speech_transcript_hint)
                    // Choices contains the actual output to be shown to the user
                    binding.textOutput.text = "$OUTPUT_TEXT${response.choices[0]?.text}"
                }
            }
        }

        binding.buttonProcess.setOnClickListener {
            if (binding.editSpeechDisplay.text.toString().isNotEmpty())
                viewModel.getChatGPTData(binding.editSpeechDisplay.text.toString())
            else
                Toast.makeText(context, getString(R.string.empty_speech_error), Toast.LENGTH_SHORT)
                    .show()
        }

        binding.buttonSaveNote.setOnClickListener {
            if (!binding.textOutput.text.isNullOrEmpty()) {
                // Checks if title is present, if not, insist to add title
                if (binding.editNotesTitle.text.toString().isNotBlank()) {
                    noteToSave = Note(
                        title = binding.editNotesTitle.text.toString(),
                        transcript = binding.editSpeechDisplay.text.toString(),
                        summary = binding.textOutput.text.toString().replace(OUTPUT_TEXT, "")
                    )
                    viewModel.saveNote(noteToSave!!)
                    activity?.onBackPressed()
                } else
                    Toast.makeText(
                        context,
                        getString(R.string.empty_title_error),
                        Toast.LENGTH_SHORT
                    ).show()
            } else
                Toast.makeText(context, getString(R.string.empty_output_error), Toast.LENGTH_SHORT)
                    .show()
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizerListenerSetup()
        micIconListenerSetup()
    }

    private fun createReadOnlyForm() {
        binding.buttonProcess.visibility = INVISIBLE
        binding.buttonSaveNote.visibility = GONE
        binding.buttonMic.visibility = INVISIBLE
        binding.editNotesTitle.visibility = GONE
    }

    private fun setReadOnlyData(note: Note) {
        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = note.title
        }
        binding.tilSpeechDisplay.hint = getString(R.string.speech_transcript_hint)
        binding.textOutput.text = "$OUTPUT_TEXT${note.summary}"
        binding.editSpeechDisplay.setText(note.transcript)
        binding.editNotesTitle.setText(note.title)
    }

    private fun speechRecognizerListenerSetup() {
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {}
            override fun onBeginningOfSpeech() {
                binding.editSpeechDisplay.setText("")
                binding.tilSpeechDisplay.hint = getString(R.string.speech_listening_hint)
                binding.textOutput.visibility = GONE
            }

            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray) {}
            override fun onEndOfSpeech() {}
            override fun onError(i: Int) {}
            override fun onResults(bundle: Bundle) {
                binding.buttonMic.setImageResource(R.drawable.ic_mic_black_off)
                val data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                binding.editSpeechDisplay.setText(data!![0])
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle) {}
        })
    }

    private fun micIconListenerSetup() {
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        binding.buttonMic?.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                speechRecognizer?.stopListening()
            }
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                binding.buttonMic?.setImageResource(R.drawable.ic_mic_black_24dp)
                speechRecognizer?.startListening(speechRecognizerIntent)
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        speechRecognizer?.destroy()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(
                requireContext(), getString(R.string.record_permission_granted), Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val OUTPUT_TEXT = "Output :\n"
        const val RECORD_AUDIO_REQUEST_CODE = 1
    }
}