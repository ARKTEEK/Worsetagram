package me.arkteek.worsetagram.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import javax.inject.Inject
import javax.inject.Singleton
import me.arkteek.worsetagram.common.utilities.await
import me.arkteek.worsetagram.domain.repository.StorageRepository

@Singleton
class StorageRepositoryImpl @Inject constructor(private val storage: FirebaseStorage) :
  StorageRepository {

  override suspend fun uploadFile(uri: Uri, path: String): String {
    val storageRef = storage.reference.child(path)
    storageRef.putFile(uri).await()
    return storageRef.downloadUrl.await().toString()
  }

  override suspend fun getFileUrl(path: String): String {
    return try {
      storage.reference.child(path).downloadUrl.await().toString()
    } catch (e: StorageException) {
      if (e.errorCode == StorageException.ERROR_OBJECT_NOT_FOUND) {
        Log.e("StorageRepository", "File not found at path: $path")
        ""
      } else {
        throw e
      }
    }
  }
}
