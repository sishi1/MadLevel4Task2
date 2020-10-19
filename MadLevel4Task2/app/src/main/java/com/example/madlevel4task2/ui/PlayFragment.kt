package com.example.madlevel4task2.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.madlevel4task2.R
import com.example.madlevel4task2.databinding.FragmentPlayBinding
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.RockPaperScissors
import com.example.madlevel4task2.model.Winner
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Math.floorMod
import java.time.LocalDateTime.now
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {

    private lateinit var binding: FragmentPlayBinding
    private lateinit var gameRepository: GameRepository

    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val RPS = RockPaperScissors.values()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())

        binding.ivRock.setOnClickListener { play(RockPaperScissors.ROCK) }
        binding.ivPaper.setOnClickListener { play(RockPaperScissors.PAPER) }
        binding.ivScissors.setOnClickListener { play(RockPaperScissors.SCISSORS) }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun play(playerRPS: RockPaperScissors) {
        decideWinner(playerRPS, RPS[Random.nextInt(RPS.size)])
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun decideWinner(player: RockPaperScissors, computer: RockPaperScissors) {
        val winner = when {
            player == computer -> {
                Winner.DRAW
            }
            floorMod((player.ordinal - 1), RPS.size) == computer.ordinal -> {
                Winner.PLAYER
            }
            else -> {
                Winner.COMPUTER
            }
        }

        binding.ivYou.setImageResource(getImageResource(player.ordinal))
        binding.ivComputer.setImageResource(getImageResource(computer.ordinal))
        binding.tvResult.setText(getResultString(winner.ordinal))

        val game = Game(
            player = player.ordinal,
            computer = computer.ordinal,
            winner = winner.ordinal,
            date = now().toString()
        )

        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }

        updateStats()
    }

    private fun updateStats() {
        mainScope.launch {
            val win = withContext(Dispatchers.IO) {
                gameRepository.getAllPlayerWins()
            }
            val draw = withContext(Dispatchers.IO) {
                gameRepository.getAllDraws()
            }
            val computerWin = withContext(Dispatchers.IO) {
                gameRepository.getAllComputerWins()
            }
            binding.tvStatistics.text = getString(
                R.string.statistics, win, draw, computerWin
            )
        }
    }

    private fun getResultString(winner: Int): Int {
        return when (Winner.values()[winner]) {
            Winner.PLAYER -> R.string.you_win
            Winner.DRAW -> R.string.draw
            Winner.COMPUTER -> R.string.computer_wins
        }
    }

    fun getImageResource(rps: Int): Int {
        return when (RockPaperScissors.values()[rps]) {
            RockPaperScissors.PAPER -> R.drawable.paper
            RockPaperScissors.ROCK -> R.drawable.rock
            RockPaperScissors.SCISSORS -> R.drawable.scissors
        }
    }
}