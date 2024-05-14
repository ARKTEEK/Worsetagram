package me.arkteek.worsetagram.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton
import me.arkteek.worsetagram.data.repository.UserRepositoryImpl
import me.arkteek.worsetagram.domain.repository.AuthRepository
import me.arkteek.worsetagram.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
  @Provides
  @Singleton
  fun provideUserRepository(
    database: FirebaseFirestore,
    authRepository: Provider<AuthRepository>,
  ): UserRepository {
    return UserRepositoryImpl(database, authRepository)
  }
}
