package me.arkteek.worsetagram.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import me.arkteek.worsetagram.data.repository.AuthRepositoryImpl
import me.arkteek.worsetagram.data.repository.PostRepositoryImpl
import me.arkteek.worsetagram.data.repository.StorageRepositoryImpl
import me.arkteek.worsetagram.data.repository.UserRepositoryImpl
import me.arkteek.worsetagram.domain.repository.AuthRepository
import me.arkteek.worsetagram.domain.repository.PostRepository
import me.arkteek.worsetagram.domain.repository.StorageRepository
import me.arkteek.worsetagram.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
  @Binds
  @Singleton
  abstract fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

  @Binds
  @Singleton
  abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

  @Binds
  @Singleton
  abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

  @Binds
  @Singleton
  abstract fun bindStorageRepository(
    storageRepositoryImpl: StorageRepositoryImpl
  ): StorageRepository

  companion object {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
      return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
      return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
      return FirebaseStorage.getInstance()
    }
  }
}
