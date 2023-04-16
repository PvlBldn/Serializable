package com.example.serializable

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.serializable.databinding.ActivityMainBinding
import java.io.Serializable
import java.lang.Thread.State
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var state : State

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        binding.bIncrement.setOnClickListener {  }
        binding.bSwitch.setOnClickListener {  }
        binding.bRandom.setOnClickListener {  }

        state = if (savedInstanceState == null) {
            State(
                counterValue = 0,
                counterTextColor = ContextCompat.getColor(this, R.color.teal_200),
                counterIsVisible = true
            )
        } else {
            savedInstanceState.getSerializable(KEY_STATE) as State
        }
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_STATE, state)
    }

    private fun increment() {
        state.counterValue++
        renderState()
    }

    private fun setRandomColor() {
        state.counterTextColor = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        renderState()
    }

    private fun switchVisibility() {
        state.counterIsVisible = !state.counterIsVisible
        renderState()
    }

    private fun renderState() = with(binding) {
        textView.setText(state.counterValue.toString())
        textView.setTextColor(state.counterTextColor)
        textView.visibility = if (state.counterIsVisible) View.VISIBLE else View.INVISIBLE
    }

    class State(
        var counterValue: Int,
        var counterTextColor: Int,
        var counterIsVisible: Boolean
    ) : Serializable

    companion object {
        @JvmStatic private val KEY_STATE = "STATE"
    }
}