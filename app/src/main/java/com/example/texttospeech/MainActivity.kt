package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeech.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech?= null
    private var binding: ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        // Initialize TTS, which is a text to Speech. Which means we need to assign text to speech to it.
        tts = TextToSpeech(this,this)

        binding?.btnSpeak?.setOnClickListener {
            if (binding?.etEnteredText?.text!!.isEmpty()){
                Toast.makeText(this,"Hey, please enter a text",Toast.LENGTH_LONG).show()
            }else{
                speakOUt(binding?.etEnteredText?.text.toString())
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){

            }else{
                Log.e("TTS","Initialization Failed")
            }
        }
    }
// You should use the onDestroy method to really make sure that you don't have any data leakage.
    private fun speakOUt(comeText:String){
        tts?.speak(comeText,TextToSpeech.QUEUE_ADD,null,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts != null){
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }
}