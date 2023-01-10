package me.project.marvelapp.data.di

import android.content.Context
import androidx.room.Room
import me.project.marvelapp.data.local.MarvelDatabase
import me.project.marvelapp.data.remote.MarvelWebService
import me.project.marvelapp.data.repository.MarvelRepository
import me.project.marvelapp.data.repository.MarvelRepositoryImpl
import me.project.marvelapp.domain.useCases.*
import me.project.marvelapp.presentation.ui.detail.DetailViewModel
import me.project.marvelapp.presentation.ui.favorite.FavoriteViewModel
import me.project.marvelapp.presentation.ui.list.ListCharacterViewModel
import me.project.marvelapp.presentation.ui.search.SearchViewModel
import me.project.marvelapp.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


fun viewModelModule() = module {

    viewModel { ListCharacterViewModel(getListCharacterUseCase = get()) }
    viewModel { SearchViewModel(getCharacterSearchUseCase = get()) }
    viewModel { DetailViewModel(getComicsUseCase = get(), saveCharacterUseCase = get()) }
    viewModel { FavoriteViewModel(getAllCharacterUseCase = get(), deleteCharacterUseCase = get()) }

}

fun useCasesModule() = module {

    factory<GetListCharacterUseCase> { GetListCharacterUseCaseImpl(repository = get()) }
    factory<GetComicsUseCase> { GetcomicsUseCaseImpl(repository = get()) }
    factory<GetCharacterSearchUseCase> { GetCharacterSarchUseCaseImpl(repository = get()) }
    factory<GetAllCharacterUseCase> { GetAllCharacterUseCaseImpl(repository = get()) }
    factory<SaveCharacterUseCase> { SaveCharacterUseCaseImpl(repository = get()) }
    factory<DeleteCharacterUseCase> { DeleteCharacterUseCaseImpl(repository = get()) }

}

fun marvelRepositoryModule() = module {

    single<MarvelRepository> { MarvelRepositoryImpl(serviceapi = get(), dao = get()) }
}

fun remoteModule() = module {

    factory { provideOkHttpClient() }
    factory { provideRetrofit(get()) }
    factory { provideMarvelWebservice(get()) }

}

fun localModule() = module {

    single { provideMarvelDatabase(get()) }
    single { provideMarvelDao(get()) }

}

fun provideMarvelDatabase(
    context: Context
) = Room.databaseBuilder(context, MarvelDatabase::class.java, Constants.DATABASE_NAME).build()

fun provideMarvelDao(database: MarvelDatabase) = database.marvelDao()


fun provideRetrofit(client: OkHttpClient): Retrofit {

    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

}

fun provideMarvelWebservice(retrofit: Retrofit): MarvelWebService {
    return retrofit.create(MarvelWebService::class.java)
}

fun provideOkHttpClient(): OkHttpClient {

    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val currentTimestamp = System.currentTimeMillis()
            val newUrl = chain.request().url
                .newBuilder()
                .addQueryParameter(Constants.TS, currentTimestamp.toString())
                .addQueryParameter(Constants.APIKEY, Constants.PUBLIC_KEY)
                .addQueryParameter(
                    Constants.HASH,
                    provideToMd5Hash(currentTimestamp.toString() + Constants.PRIVATE_KEY + Constants.PUBLIC_KEY)
                )
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
        .addInterceptor(logging)
        .build()
}

fun provideToMd5Hash(encrypted: String): String {

    var pass = encrypted
    var encryptedString: String? = null
    val md5: MessageDigest
    try {
        md5 = MessageDigest.getInstance("MD5")
        md5.update(pass.toByteArray(), 0, pass.length)
        pass = BigInteger(1, md5.digest()).toString(16)
        while (pass.length < 32) {
            pass = "0$pass"
        }
        encryptedString = pass
    } catch (e1: NoSuchAlgorithmException) {
        e1.printStackTrace()
    }
    return encryptedString ?: ""
}





