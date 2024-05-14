package me.arkteek.worsetagram.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.arkteek.worsetagram.data.repository.AuthRepositoryImpl
import me.arkteek.worsetagram.domain.repository.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
  @Provides fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

  @Provides fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl
}
