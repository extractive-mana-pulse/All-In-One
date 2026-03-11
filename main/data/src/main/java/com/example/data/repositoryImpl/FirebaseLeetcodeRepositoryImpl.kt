package com.example.data.repositoryImpl

import com.example.data.LeetcodeEntity
import com.example.domain.model.Leetcode
import com.example.domain.repository.LeetcodeRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreLeetcodeRepository(
    private val db: FirebaseFirestore
) : LeetcodeRepository {

    companion object {
        const val LEETCODE_COLLECTION = "leetcode"
    }

    override fun getAllAlgorithms(): Flow<List<Leetcode>> = callbackFlow {
        val listener = db
            .collection(LEETCODE_COLLECTION)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val leetcode = snapshot?.documents.orEmpty().mapNotNull { document ->
                    document.toObject(LeetcodeEntity::class.java)
                        ?.toLeetcode(id = document.id)
                }

                trySend(leetcode)
            }

        awaitClose { listener.remove() }
    }

    override fun getLeetCodeById(id: String): Flow<Leetcode?> = callbackFlow {
        val listener = db
            .collection(LEETCODE_COLLECTION)
            .document(id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }

                val leetcode = snapshot
                    ?.toObject(LeetcodeEntity::class.java)
                    ?.toLeetcode(id = snapshot.id)

                trySend(leetcode)
            }

        awaitClose { listener.remove() }
    }

    override fun getLeetCodeByTitle(title: String): Flow<Leetcode?> = callbackFlow {
        val listener = db
            .collection(LEETCODE_COLLECTION)
            .whereEqualTo("title", title)
            .limit(1)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }

                val leetcode = snapshot
                    ?.documents
                    ?.firstOrNull()
                    ?.let { document ->
                        document.toObject(LeetcodeEntity::class.java)
                            ?.toLeetcode(id = document.id)
                    }

                trySend(leetcode)
            }

        awaitClose { listener.remove() }
    }
}