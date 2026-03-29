package com.rafael.appdev.goodgamexp.controller

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rafael.appdev.goodgamexp.R
import com.rafael.appdev.goodgamexp.databinding.ActivityMyGamesBinding
import com.rafael.appdev.goodgamexp.model.AppDatabase
import com.rafael.appdev.goodgamexp.model.Game
import com.rafael.appdev.goodgamexp.model.GameDao
import com.rafael.appdev.goodgamexp.view.GameAdapter
import com.rafael.appdev.goodgamexp.viewmodel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint //Injeção de Dependência
class MyGamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyGamesBinding
    private lateinit var adapter: GameAdapter
    private lateinit var gesture: GestureDetector

    //Substitui GameDao por GameViewModel:
    private val viewModel: GameViewModel by viewModels()

    //Rastreio da position do jogo em editGameForResult
    private var editingPosition: Int = -1

    //Requisição addGameForResult
    val addGameForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged()
            result.data?.let { data ->
                data.getStringExtra("gameTitle")?.let { game ->
                    showCreatedGameMessage(game)
                }
            }
        }
    }

    //Requisição editGameForResult
    val editGameForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            //O jogo já foi editado pela GamesAddActivity no DataStore
            if (editingPosition != -1) {
                adapter.notifyItemChanged(editingPosition) //Notifica apenas o item editado
            } else {
                adapter.notifyDataSetChanged()
            }
            editingPosition = -1 //Limpa a position de edição
            result.data?.let { data ->
                data.getStringExtra("gameTitle")?.let { game ->
                    showUpdatedGameMessage(game)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyGamesBinding.inflate(layoutInflater)

        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Configuração dos Gestos
        setupGestures()
        //Configuração do RecycleViewEvents
        configureRecycleViewWithEvents()

        //NOVO - RecyclerView
        setupRecyclerView()

        //NOVO - lifecycleScope / adapter:
        lifecycleScope.launch {
            viewModel.allGames.collectLatest { gamesList ->
                adapter.games = gamesList.toMutableList()
                adapter.notifyDataSetChanged()
            }
        }

        //SearchBar - Barra de Pesquisa
        btnSearchBar()
        //SearchBar - Ícone Menu da SearchBar (Superior Direito)
        btnIconAccount()

        //NavBar - Botão Buscar
        btnSearch()
        //NavBar - Botão Comunidade
        btnCommunity()
        //NavBar - Botão Meus Jogos
        btnMyGames()
        //NavBar - Botão Adicionar
        btnAdd()

        //Diálogo de Instruções
        showInstructionsDialog()
    }

    private fun setupGestures() {
        gesture = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {

            override fun onDoubleTap(e: MotionEvent): Boolean {
                binding.rcvMyGames.findChildViewUnder(e.x, e.y).also { view ->
                    view?.let { child ->
                        binding.rcvMyGames.getChildAdapterPosition(child).also { position ->
                            val game = adapter.games[position]
                            AlertDialog.Builder(this@MyGamesActivity).also { dialog ->
                                dialog.setTitle("${game.title} Good Review!")
                                dialog.setMessage(game.txtReview)
                                dialog.setNegativeButton(android.R.string.cancel, null)
                                dialog.show()
                            }
                        }
                    }
                }
                return super.onDoubleTap(e)
            }

            override fun onLongPress(e: MotionEvent) {
                binding.rcvMyGames.findChildViewUnder(e.x, e.y).also { view ->
                    view?.let { child ->
                        binding.rcvMyGames.getChildAdapterPosition(child).also { position ->
                            //Acessa o jogo da lista atual do adapter
                            val game = adapter.games[position]
                            showRemoveDialog(game)
                        }
                    }
                }
                super.onLongPress(e)
            }
        })
    }

    fun configureRecycleViewWithEvents() {
        binding.rcvMyGames.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                rv.findChildViewUnder(e.x, e.y).also { view ->
                    return (view != null && gesture.onTouchEvent(e))
                }
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

        })
    }

    private fun btnSearchBar() {
        //SearhBar - Barra de Pesquisa (Button Menu)
        binding.searchBar.setOnClickListener {
            Toast.makeText(this, "search_bar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun btnIconAccount() {
        //SearhBar - Ícone Perfil da SearchBar (Superior Direito)
        binding.searchBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.icon_account -> {
                    Toast.makeText(this, "account_icon", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    Toast.makeText(this, "account_icon inativo", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
    }

    private fun btnSearch() {
        //NavBar - Botão Buscar
        binding.btnSearch.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun btnCommunity() {
        //NavBar - Botão Comunidade
        binding.btnCommunity.setOnClickListener {
            startActivity(Intent(this, CommunityActivity::class.java))
            finish()
        }
    }

    private fun btnMyGames() {
        //NavBar - Botão Meus Jogos
        binding.btnMyGames.setOnClickListener {
            Toast.makeText(this, R.string.toast_activity_atual, Toast.LENGTH_SHORT).show()
        }
    }

    private fun btnAdd() {
        //NavBar - Botão Adicionar (extra 'mode')
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, GamesAddActivity::class.java).apply {
                putExtra("mode", "add")
            }
            addGameForResult.launch(intent)
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.main,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    //Diálogo de Instruções
    private fun showInstructionsDialog() {
        AlertDialog.Builder(this).also { dialog ->
            dialog.setTitle(getString(R.string.dial_instrucoes_e_botoes))
            dialog.setMessage(
                getString(R.string.dial_mensagem_ajuda)
            )
            dialog.setPositiveButton(android.R.string.ok, null)
            dialog.show()
        }
    }

    //Mensagem de Jogo Adicionado
    private fun showCreatedGameMessage(game: String) {
        showMessage("${getString(R.string.snack_jogatina)} ${game} ${getString(R.string.snack_jogo_criado)}")
    }

    //Mensagem de Jogo Atualizado
    private fun showUpdatedGameMessage(game: String) {
        showMessage("${getString(R.string.snack_jogatina)} ${game} ${getString(R.string.snack_jogo_atualizado)}")
    }

    //Diálogo de Exclusão
    private fun showRemoveDialog(game: Game) {
        AlertDialog.Builder(this@MyGamesActivity).also { dialog ->
            dialog.setTitle(game.title)
            dialog.setMessage("${getString(R.string.dial_deseja_excluir)} ${game.title}?")
            dialog.setPositiveButton(android.R.string.ok) { _, _ ->
                // USANDO O VIEWMODEL AGORA
                viewModel.delete(game)

                this@MyGamesActivity.showMessage(
                    "${getString(R.string.snack_jogatina)} ${game.title} ${
                        getString(R.string.dial_jogo_removido)
                    }"
                )
            }
            dialog.setNegativeButton(android.R.string.cancel, null)
            dialog.show()
        }
    }

    //setupRecyclerView - Envia os nomes para GamesAddActivity
    private fun setupRecyclerView() {
        adapter = GameAdapter(mutableListOf()) { game, position ->
            editingPosition = position

            val intent = Intent(this, GamesAddActivity::class.java).apply {
                putExtra("mode", "edit")
                putExtra("gameData", game)
                putExtra("gameId", game.id)
            }
            editGameForResult.launch(intent)
        }

        binding.rcvMyGames.apply {
            adapter = this@MyGamesActivity.adapter
            layoutManager = LinearLayoutManager(this@MyGamesActivity)
        }
    }
}