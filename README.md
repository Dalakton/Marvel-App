<h1 align="center">Marvel App</h1>

<p align="center">
 <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0 -blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=23"><img src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat" border="0" alt="API"></a>
  <br>
  <a href="https://wa.me/+5581996308063"><img alt="WhatsApp" src="https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo =whatsapp&logoColor=white"/></a>
  <a href="https://www.linkedin.com/in/dalakton-rodrigues-0319121b6/"><img alt="Linkedin" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for -the-badge&logo=linkedin&logoColor=white"/></a>
  <a href="mailto:DalaktonRodrigues@gmail.com"><img alt="Gmail" src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor= branco"/></a>
</p>

<p align="center">  

⭐ Esse é um projeto para demonstrar meu conhecimento técnico no desenvolvimento Android nativo com Kotlin. Mais informações técnicas abaixo.

App que lista personagens da marvel, trazendo a tela de detalhes com algumas informações do personagens, incluindo os comics/quadrinhos em que tal personagem está inserido.
Com funcionalidade de busca e favoritar personagem.  
</p>

</br>

<p float="left" align="center">
<img alt="screenshot" width"5%" src="screenshots/Screenshot_01.marvelapp.png"/>
<img alt="screenshot" width"5%" src="screenshots/Screenshot_02.marvelapp.png"/>
<img alt="screenshot" width"5%" src="screenshots/Screenshot_03.marvelapp.png"/>
<img alt="screenshot" width"5%" src="screenshots/Screenshot_04.marvelapp.png"/>
</p>

## Download

Ou faça o download da <a href="apk/app-debug.apk?raw=true">APK diretamente</a>. Você pode ver <a href="https://www.google.com/search?q=como+instalar+um+apk+no+android">aqui</a> como instalar uma APK no seu aparelho android.

## Tecnologias usadas e bibliotecas de código aberto

- Nível mínimo do SDK : 23
- [Linguagem Kotlin](https://kotlinlang.org/) 

- Jetpack Compose
  - Lifecycle: Observe os ciclos de vida do Android e manipule os estados da interface do usuário após as alterações do ciclo de vida.
  - ViewModel: Gerencia o detentor de dados relacionados à interface do usuário e o ciclo de vida. Permite que os dados sobrevivam a alterações de configuração, como rotações de tela.
  - ViewBinding: Liga os componentes do XML no Kotlin através de uma classe que garante segurança de tipo e outras vantagens.
  - Sala: Biblioteca de abstração do banco de dados SQLite que garante segurança em tempo de construção e facilidade de uso.
  - Custom Views: View customizadas feitas do zero usando XML.
 

- Arquitetura
  - MVVM (View - ViewModel - Model)
  - Comunicação da ViewModel com a View através de LiveData
  - Comunicação da ViewModel com a Model através de Kotlin Flow
  - Repositories para abstração da comunicação com a camada de dados.
  
- Bibliotecas
  - [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Para realizar requisições seguindo o padrão HTTP.
  - [Glide](https://github.com/bumptech/glide): Para carregamento de imagens e cacheamento das mesmas.
 
 
## Arquitetura

**Marvel app** utiliza a arquitetura MVVM e o padrão de Repositories, que segue as [recomendações oficiais do Google](https://developer.android.com/topic/architecture).
</br></br>


## API de terceiros

[Marvel Api](https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0)

