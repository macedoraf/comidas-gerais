package br.com.rafa_macedo.comidas_gerais.di

import br.com.rafa_macedo.comidas_gerais.BuildInfoProvider
import br.com.rafa_macedo.comidas_gerais.data.CredentialInterceptor
import br.com.rafa_macedo.comidas_gerais.data.service.RecipesService
import br.com.rafa_macedo.comidas_gerais.data.datasource.RecipeDataRemoteSource
import br.com.rafa_macedo.comidas_gerais.data.datasource.RecipeDataSource
import br.com.rafa_macedo.comidas_gerais.data.repository.Repository
import br.com.rafa_macedo.comidas_gerais.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
    fun provideOkHttpClient(credentialInterceptor: CredentialInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(credentialInterceptor)
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