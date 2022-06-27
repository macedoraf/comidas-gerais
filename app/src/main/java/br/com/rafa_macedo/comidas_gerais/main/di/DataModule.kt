package br.com.rafa_macedo.comidas_gerais.main.di

import br.com.rafa_macedo.comidas_gerais.core.BuildInfoProvider
import br.com.rafa_macedo.comidas_gerais.core.data.CredentialInterceptor
import br.com.rafa_macedo.comidas_gerais.core.service.RecipesService
import br.com.rafa_macedo.comidas_gerais.main.data.datasource.RecipeDataRemoteSource
import br.com.rafa_macedo.comidas_gerais.main.data.datasource.RecipeDataSource
import br.com.rafa_macedo.comidas_gerais.main.data.repository.Repository
import br.com.rafa_macedo.comidas_gerais.main.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    fun provideRecipesService(
        retrofit: Retrofit
    ): RecipesService {
        return retrofit
            .create(RecipesService::class.java)
    }

    @Provides
    fun provideDispatcher(): Dispatcher =
        Dispatcher().apply { maxRequests = 1 }

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideMainDispatcher(): MainCoroutineDispatcher = Dispatchers.Main


    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com")
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideOkHttpClient(
        credentialInterceptor: CredentialInterceptor,
        dispatcher: Dispatcher
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(credentialInterceptor)
            .dispatcher(dispatcher)
            .build()

    @Provides
    fun provideRecipeRemoteDataSource(recipesService: RecipesService): RecipeDataSource {
        return RecipeDataRemoteSource(recipesService)
    }

    @Provides
    fun provideRepository(dataSource: RecipeDataSource): Repository {
        return RepositoryImpl(dataSource)
    }

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideApiInfoInterceptor(buildInfoProvider: BuildInfoProvider): CredentialInterceptor =
        CredentialInterceptor(buildInfoProvider)

    @Provides
    fun provideBuildInfoProvider() = BuildInfoProvider
}