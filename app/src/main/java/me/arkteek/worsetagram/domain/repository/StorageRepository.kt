package me.arkteek.worsetagram.domain.repository

import android.net.Uri

interface StorageRepository {
  suspend fun uploadFile(uri: Uri, path: String): String
  suspend fun getFileUrl(path: String): String}
