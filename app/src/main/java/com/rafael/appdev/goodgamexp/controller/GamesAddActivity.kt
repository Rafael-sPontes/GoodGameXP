package com.rafael.appdev.goodgamexp.controller

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.rafael.appdev.goodgamexp.R
import com.rafael.appdev.goodgamexp.databinding.ActivityGamesAddBinding
import com.rafael.appdev.goodgamexp.model.AppDatabase
import com.rafael.appdev.goodgamexp.model.Game
import com.rafael.appdev.goodgamexp.model.GameDao
import kotlinx.coroutines.launch

class GamesAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamesAddBinding

    //Declaração de Resources (TitleImage)
    private lateinit var imgCoverResources: Array<Int>

    //Declaração de Resources (PlatformImage)
    private lateinit var imgPlatformResources: Array<Int>

    //Declaração de variáveis da classe SpinnerTitleItems
    private lateinit var spinnerTitleItems: Array<String>

    //Declaração de variáveis da classe SpinnerPlatformItems
    private lateinit var spinnerPlatformItems: Array<String>

    //Variáveis para edição
    private var isEditMode: Boolean = false
    private var editingGamePosition: Int = -1
    private var originalGame: Game? = null

    // Variável para o ID do jogo sendo editado
    private var editingGameId: Int = -1 //Novo

    //Adiciona o DAO
    private lateinit var gameDao: GameDao //Novo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGamesAddBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Inicializar o DAO
        gameDao = AppDatabase.getDatabase(this).gameDao() // Novo

        //Configuração do SpinnerTitle
        setupTitleSpinner()
        //Configuração do SpinnerPlatform
        setupPlatformSpinner()
        //Configuração da Imagem do SpinnerTitle (Cover)
        setupTitleImage()
        //Configuração da Imagem do SpinnerPlatform
        setupPlatformImage()
        //Configuração do SliderProgress
        setupProgressSlider()
        //Configuração do SliderRate
        setupRateSlider()
        //Configuração do editMode
        setupEditMode()

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

        //RESULT - Cancelar
        btnCancel()
        //RESULT - Concluir (Lógica unificada para Add e Edit)
        btnAccept()
    }

    //Configuração do SpinnerTitle
    private fun setupTitleSpinner() {
        //Referência do SpinnerTitle
        val spinnerTitle: Spinner = binding.spinnerTitle
        //Criação da Lista do SpinnerTitle
        spinnerTitleItems = arrayOf(
            "Agony Unrated", "Alisa Developer's Cut", "Brutal Legend",
            "Conker's Bad Fur Day", "God of War", "Heavenly Sword", "King's Field (1995)", "KOF94",
            "Megaman X4", "Metal Slug Anthology", "Patapon 3", "Skyblazer", "Unravel"
        )
        //Criação do ArrayAdapter e Layout customizado
        val adapterTitle = ArrayAdapter(
            this,
            R.layout.spinner_selected_item, //Layout customizado para o item selecionado
            spinnerTitleItems
        )
        //Layout customizado para o DROPDOWN
        adapterTitle.setDropDownViewResource(R.layout.spinner_dropdown_item)
        //Associação do Adapter ao SpinnerTitle
        spinnerTitle.adapter = adapterTitle
    }

    //Configuração do SpinnerPlatform
    private fun setupPlatformSpinner() {
        //Referência do SpinnerPlatform
        val spinnerPlatform: Spinner = binding.spinnerPlatform
        //Criação da Lista do SpinnerPlatform
        spinnerPlatformItems = arrayOf(
            "ARCADE", "Nintendo 64", "Nintendo Switch",
            "PC Steam", "Playstation 1", "Playstation 2", "Playstation 3",
            "Playstation 4", "Playstation Portable", "Super Nintendo"
        )
        //Criação do ArrayAdapter e Layout Customizado
        val adapterPlatform = ArrayAdapter(
            this,
            R.layout.spinner_selected_item, //Layout customizado para o item selecionado
            spinnerPlatformItems
        )
        //Layout customizado para o DROPDOWN
        adapterPlatform.setDropDownViewResource(R.layout.spinner_dropdown_item)
        //Associação do Adapter ao SpinnerPlatform
        spinnerPlatform.adapter = adapterPlatform
    }

    //Configuração da Imagem do SliderTitle (Cover)
    private fun setupTitleImage() {
        imgCoverResources = arrayOf(
            R.drawable.agony_unrated_cover,
            R.drawable.alisa_developers_cut_cover,
            R.drawable.brutal_legend_cover,
            R.drawable.conkers_bad_fur_day_cover,
            R.drawable.god_of_war_cover,
            R.drawable.heavenly_sword_cover,
            R.drawable.kings_filed_1995_cover,
            R.drawable.kof94_cover,
            R.drawable.megaman_x4_cover,
            R.drawable.metal_slug_anthology_cover,
            R.drawable.patapon3_cover,
            R.drawable.skyblazer_cover,
            R.drawable.unravel_cover
        )
    }

    //Configuração da Imagem do SpinnerPlatform
    private fun setupPlatformImage() {
        imgPlatformResources = arrayOf(
            R.drawable.arcade_platform,
            R.drawable.nintendo_64_platform,
            R.drawable.nintendo_switch_platform,
            R.drawable.pc_steam_platform,
            R.drawable.playstation_1_platform,
            R.drawable.playstation_2_platform,
            R.drawable.playstation_3_platform,
            R.drawable.playstation_4_platform,
            R.drawable.playstation_portable_platform,
            R.drawable.super_nintendo_platform
        )
    }

    //Configuração do SliderProgress (0-100 / Passo 5)
    private fun setupProgressSlider() {
        //Atribuição do SliderProgress e TextProgress com viewBinding
        val sliderProgress = binding.sliderProgress
        val txtProgress = binding.txtProgress

        //Inicialização do TextProgress com o valor inicial do SliderProgress (30)
        updateProgressText(sliderProgress.value.toInt(), txtProgress)

        //Associação do SliderProgress ao TextProgress (Atualização Dinâmica)
        sliderProgress.addOnChangeListener { _, value, _ ->
            updateProgressText(value.toInt(), txtProgress)
        }
    }

    //Configuração do TextProgress (0-100 / Passo 5)
    private fun updateProgressText(progress: Int, txtProgress: TextView) {
        //Exibe o Progress como porcentagem (ex: 50%)
        txtProgress.text = "$progress%"
    }

    //Configuração do SliderRate (0-5 / Passo 0.5)
    private fun setupRateSlider() {
        //Atribuição do SliderRate e TextRate com viewBinding
        val sliderRate = binding.sliderRate
        val txtRate = binding.txtRate

        //Inicialização do TextRate com o valor inicial do SliderRate (2.0)
        updateRateText(sliderRate.value, txtRate)

        //Associação do SliderRate ao TextRate (Atualização Dinâmica)
        sliderRate.addOnChangeListener { _, value, _ ->
            updateRateText(value, txtRate)
        }
    }

    //Configuração do TextRate (0-5 / Passo 0.5)
    private fun updateRateText(rate: Float, txtRate: TextView) {
        //Exibe a Rate formatado com 1 casa decimal (ex: 2.0)
        txtRate.text = String.format("%.1f", rate)
    }

    //Configuração do editMode
    private fun setupEditMode() {
        val mode = intent.getStringExtra("mode")
        if (mode == "edit") {
            isEditMode = true
            binding.btnAccept.text =
                getString(R.string.bnt_salvar_alteracoes) //Altera o text do botão

            //Recebe os dados de gamePosition/gameId e gameData
            editingGameId = intent.getIntExtra("gameId", -1) //Novo: Recebe o ID

            //Usa getSerializableExtra (Serializable constructor)
            originalGame = intent.getSerializableExtra("gameData") as? Game

            //Preenche os campos com os dados do jogo original
            originalGame?.let { game ->
                //Preenche o SpinnerTitle
                val titleIndex = spinnerTitleItems.indexOf(game.title)
                if (titleIndex != -1) binding.spinnerTitle.setSelection(titleIndex)

                //Preenche o SpinnerPlatform
                val platformIndex = spinnerPlatformItems.indexOf(game.platform)
                if (platformIndex != -1) binding.spinnerPlatform.setSelection(platformIndex)

                //Preenche o SliderProgress
                binding.sliderProgress.value = game.progress.toFloat()
                //Preenche o SliderRate
                binding.sliderRate.value = game.txtRate.replace(",", ".").toFloatOrNull() ?: 2.0f

                //Preenche o txtReview
                binding.txtReview.setText(game.txtReview)
            }
        } else {
            isEditMode = false
            binding.btnAccept.text = getString(R.string.btn_adicionar_jogo) //Altera o text do botão
        }
    }

    private fun btnSearchBar() {
        //SearchBar - Barra de Pesquisa (Button Menu)
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
        //NavBar - Ícone Buscar
        binding.btnSearch.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun btnCommunity() {
        //NavBar - Ícone Comunidade
        binding.btnCommunity.setOnClickListener {
            startActivity(Intent(this, CommunityActivity::class.java))
            finish()
        }
    }

    private fun btnMyGames() {
        //NavBar - Ícone Meus Jogos
        binding.btnMyGames.setOnClickListener {
            startActivity(Intent(this, MyGamesActivity::class.java))
            finish()
        }
    }

    private fun btnAdd() {
        //NavBar - Ícone Adicionar
        binding.btnAdd.setOnClickListener {
            Toast.makeText(this, R.string.toast_activity_atual, Toast.LENGTH_SHORT).show()
        }
    }

    private fun btnCancel() {
        //RESULT - Cancelar
        binding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun btnAccept() {
        //RESULT - Concluir (Lógica unificada para Add e Edit)
        binding.btnAccept.setOnClickListener {
            //Coleta dos dados do formulário
            val game = binding.spinnerTitle.selectedItem.toString()
            val platform = binding.spinnerPlatform.selectedItem.toString()
            val progress = binding.sliderProgress.value.toInt()
            val txtProgress = "${progress}%"
            val rate = binding.sliderRate.value
            val txtRate = String.format("%.1f", rate)
            val imgCover = imgCoverResources[binding.spinnerTitle.selectedItemPosition]
            val imgPlatform = imgPlatformResources[binding.spinnerPlatform.selectedItemPosition]
            val txtReview = binding.txtReview.text.toString()

            //Criação do newGameData
            val newGameData = Game(
                title = game,
                platform = platform,
                progress = progress,
                txtProgress = txtProgress,
                txtRate = txtRate,
                imgCover = imgCover,
                imgPlatform = imgPlatform,
                txtReview = txtReview
            )

            //Coroutine para interagir com o Room
            lifecycleScope.launch {
                if (isEditMode && editingGameId != -1) {
                    //editMode: Atualiza o jogo
                    newGameData.id = editingGameId //Garante que o ID é o do jogo editado
                    gameDao.update(newGameData) //Atualiza no Room
                } else {
                    //addMode: Adiciona um novo jogo
                    gameDao.insert(newGameData) //Insere no Room
                }

                //Retorna o resultado para MyGamesActivity
                Intent().apply {
                    this.putExtra("gameTitle", game)
                    setResult(RESULT_OK, this)
                }
                finish()
            }
        }
    }
}