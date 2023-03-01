package com.maverickai.meetassist.feature_recording.presentation

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
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maverickai.meetassist.R
import com.maverickai.meetassist.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class CreateNoteFragment : Fragment() {
    private val RecordAudioRequestCode = 1
    private var speechRecognizer: SpeechRecognizer? = null
    private var editText: EditText? = null
    private var micButton: ImageView? = null

    private var _binding: FragmentSecondBinding? = null
    private lateinit var viewModel: CreateNoteViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateNoteViewModel::class.java]
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it == true) binding.progressBar.visibility = VISIBLE
            else binding.progressBar.visibility = GONE
        }
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let { error ->
                Toast.makeText(context, "Error - $error", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.gptResponse.observe(viewLifecycleOwner) {
            it?.let { response ->
                binding.textOutput.visibility = VISIBLE
                binding.textOutput.text = "Output :\n $response"
            }
        }

        editText = binding.text
        micButton = binding.buttonMic
        binding.buttonSubmit.setOnClickListener {
            if (editText?.text.toString().isNotEmpty())
                viewModel.getChatGPTData(editText?.text.toString())
            else
                Toast.makeText(context, "Speech text cannot be empty", Toast.LENGTH_SHORT).show()
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {}
            override fun onBeginningOfSpeech() {
                editText?.setText("")
                editText?.hint = "Listening..."
                binding.textOutput.visibility = GONE
            }

            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray) {}
            override fun onEndOfSpeech() {}
            override fun onError(i: Int) {}
            override fun onResults(bundle: Bundle) {
                micButton?.setImageResource(R.drawable.ic_mic_black_off)
                val data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                editText?.setText(data!![0])
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle) {}
        })

        micButton?.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                speechRecognizer?.stopListening()
            }
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                micButton?.setImageResource(R.drawable.ic_mic_black_24dp)
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
                requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO), RecordAudioRequestCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(
                requireContext(), "Permission Granted", Toast.LENGTH_SHORT
            ).show()
        }
    }
}