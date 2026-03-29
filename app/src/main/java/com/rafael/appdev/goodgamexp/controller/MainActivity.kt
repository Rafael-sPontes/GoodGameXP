package com.rafael.appdev.goodgamexp.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rafael.appdev.goodgamexp.R
import com.rafael.appdev.goodgamexp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //SearchBar - Barra de Pesquisa
        btnSearchBar()
        //SearchBar - Ícone Menu da SearchBar (Superior Direito)
        btnIconMenu()

        //NavBar - Botão Buscar
        btnSearch()
        //NavBar - Botão Comunidade
        btnCommunity()
        //NavBar - Botão Meus Jogos
        btnMyGames()
        //NavBar - Botão Adicionar
        btnAdd()
    }

    private fun btnSearchBar() {
        //SearchBar - Barra de Pesquisa
        binding.searchBar.setOnClickListener {
            Toast.makeText(this, "search_bar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun btnIconMenu() {
        //SearchBar - Ícone Menu da SearchBar (Superior Direito)
        binding.searchBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.icon_menu -> {
                    Toast.makeText(this, "menu_icon", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    Toast.makeText(this, "menu_icon inativo", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
    }

    private fun btnSearch() {
        //NavBar - Botão Buscar
        binding.btnSearch.setOnClickListener {
            Toast.makeText(this, R.string.toast_activity_atual, Toast.LENGTH_SHORT).show()
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
            startActivity(Intent(this, MyGamesActivity::class.java))
            finish()
        }
    }

    private fun btnAdd() {
        //NavBar - Botão Adicionar
        binding.btnAdd.setOnClickListener {
            Toast.makeText(this, R.string.toast_acesse_em_meus_jogos, Toast.LENGTH_SHORT).show()
        }
    }
}