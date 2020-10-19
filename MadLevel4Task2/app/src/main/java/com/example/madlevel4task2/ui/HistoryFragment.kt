package com.example.madlevel4task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.databinding.FragmentHistoryBinding
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private lateinit var binding: FragmentHistoryBinding

    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val games = arrayListOf<Game>()
    private val historyAdapter = HistoryAdapter(games)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        gameRepository = GameRepository(requireContext())
        getGamesFromDatabase()

        initRV()

        binding.ivDelete.setOnClickListener { deleteGamesFromDatabase() }

        binding.ivReturn.setOnClickListener {
            (activity as AppCompatActivity).supportActionBar?.show()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun initRV() {
        rvHistory.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvHistory.adapter = historyAdapter
        rvHistory.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun getGamesFromDatabase() {
        mainScope.launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }

            this@HistoryFragment.games.clear()
            this@HistoryFragment.games.addAll(games)
            historyAdapter.notifyDataSetChanged()
        }
    }

    private fun deleteGamesFromDatabase() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.clearGameHistory()
            }
            getGamesFromDatabase()
        }
    }
}