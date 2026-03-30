# GoodGame Experience!! (GGXP)

<h2>🎮 Sobre o Projeto</h2>

<p>O GGXP é um organizador pessoal de jogatinas desenvolvido como projeto final para a Disciplina de Desenvolvimento Mobile Profissional (PUCPR). O app permite que usuários cataloguem seus jogos, acompanhem o progresso e registrem reviews.</p>

<hr>

<h2>🛠 Atendimento aos Requisitos Técnicos</h2>

<h3>1. Arquitetura de Software (MVVM)</h3>
<p>O projeto utiliza o padrão MVVM (Model-View-ViewModel) para garantir a separação de responsabilidades.</p>

<ul>
	<li>View: <code>MyGamesActivity</code> e <code>GamesAddActivity</code> (Gerenciamento de UI via ViewBinding).</li>
	<li>ViewModel: <code>GameViewModel</code> (Gerencia o estado da UI e a comunicação com o repositório).</li>
	<li>Model: <code>Game</code> (Entidade de dados do Room).</li>
	<li>Data Flow: Utilização de Kotlin Flow e <code>collectLatest</code> para atualizações reativas da interface.</li>
</ul>

<hr>

<h3>2. Injeção de Dependência (Hilt)</h3>
<p>Implementação do Hilt para desacoplar as dependências e facilitar a testabilidade.</p>

<ul>
	<li>Application Class: <code>GGXPApplication</code> anotada com <code>@HiltAndroidApp</code>.</li>
	<li>Modules: <code>DatabaseModule</code> provê as instâncias de <code>AppDatabase</code> e <code>GameDao</code>.</li>
	<li>Injection: Uso de <code>@AndroidEntryPoint</code> nas Activities e <code>@Inject</code> nos ViewModels/Repositories.</li>
</ul>

<hr>

<h3>3. Clean Code    </h3>
<p>O código segue as diretrizes de Clean Code para legibilidade e manutenção:</p>

<ul>
	<li>Nomes Significativos: Funções como <code>setupRecyclerView()</code> e <code>showRemoveDialog()</code> deixam claro seu propósito.</li>
	<li>Funções Pequenas: Divisão de responsabilidades (Ex: uma função para gestos, outra para configuração do adapter).</li>
	<li>DRY (Don't Repeat Yourself): Centralização da lógica de banco de dados no ViewModel.</li>
</ul>

<hr>

<h3>4. Design Patterns</h3>
<p>Além do MVVM, o projeto aplica padrões como:</p>

<ul>
	<li>Adapter Pattern: <code>GameAdapter</code> para adaptar a lista de objetos <code>Game</code> para a <code>RecyclerView</code>.</li>
	<li>Singleton: Garantido pelo Hilt/Room para a instância do Banco de Dados.</li>
	<li>Observer: Implementado via <code>Flow</code> para observar mudanças nos dados em tempo real.</li>
</ul>

<hr>

<h3>5. Testes Unitários</h3>
<p>O projeto contém um total de 5 testes unitários localizados em <code>GoodGameXP\app\src\test\java\com\rafael\appdev\goodgamexp/GameEntityTest.kt</code>.</p>

<ul>
	<li>Os testes validam a integridade da entidade <code>Game</code>, formatação de strings de progresso e comportamento de IDs automáticos.</li>
</ul>

<hr>

<h3>6. Interface (3 Telas Funcionais)</h3>
<p>O fluxo do aplicativo é composto por:</p>

<ul>
	<li>LaunchScreen: Tela de Splash para identidade visual e carregamento.</li>
	<li>MyGamesActivity: Dashboard principal com a listagem de jogos (RecyclerView).</li>
	<li>GamesAddActivity: Tela dual-mode (Adição/Edição) com formulários, Spinners e Sliders.</li>
</ul>

<hr>

<h2>🚀 Como Executar</h2>

<ol>
	<li>Clone o repositório.</li>
	<li>Configuração do Ambiente: utilize o JDK 21 (Recomendado: JetBrains Runtime 21.0.9)</li>
	<li>Sincronize o Gradle: utilize Kotlin 2.x e Room 2.x.</li>
</ol>

<hr>

<h3>🔗 Links</h3>

<ul>
	<li><a href="https://github.com/Rafael-sPontes/GoodGameXP" target="_self" rel="author"><i>Repositório do GitHub</i></a></li>
	<li><a href="https://youtu.be/65kqA5gDUKo" target="_blank" rel="author"><i>Video no YouTube</i></a></li>
	<li><a href="https://drive.google.com/file/d/1Je0WwywiQoBAvhe-dVOywRs_M83Q5O-S/view?usp=sharing" target="_blank" rel="author"><i>Video no GoogleDrive</i></a></li>
</ul>

<hr>

<h3>⌨️ Informações do Aluno</h3>

<ul>
	<li>Nome: Rafael Santos Pontes</li>
	<li>ID do Aluno: 40117760</li>
	<li>Turma: U</li>
	<li>Curso: Desenvolvimento de Aplicativos Móveis</li>
	<li>Disciplina: Desenvolvimento Mobile Profissional</li>
</ul>
