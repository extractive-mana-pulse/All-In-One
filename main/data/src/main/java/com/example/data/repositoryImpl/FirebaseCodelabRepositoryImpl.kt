package com.example.data.repositoryImpl

import com.example.data.CodelabEntity
import com.example.domain.model.Codelab
import com.example.domain.repository.CodelabsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreCodelabsRepository(
    private val db: FirebaseFirestore
) : CodelabsRepository {

    companion object {
        const val CODELABS_COLLECTION = "codelabs"
    }



    override fun getAllCodelabs(): Flow<List<Codelab>> = callbackFlow {
        val listener = db
            .collection(CODELABS_COLLECTION)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val codelabs = snapshot?.documents.orEmpty().mapNotNull { document ->
                    document.toObject(CodelabEntity::class.java)
                        ?.toCodelab(id = document.id)
                }

                trySend(codelabs)
            }

        awaitClose { listener.remove() }
    }

    override fun getCodelabById(id: String): Flow<Codelab?> = callbackFlow {
        val listener = db
            .collection(CODELABS_COLLECTION)
            .document(id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }

                val codelab = snapshot
                    ?.toObject(CodelabEntity::class.java)
                    ?.toCodelab(id = snapshot.id)

                trySend(codelab)
            }

        awaitClose { listener.remove() }
    }

    override fun getCodelabByTitle(title: String): Flow<Codelab?> = callbackFlow {
        val listener = db
            .collection(CODELABS_COLLECTION)
            .whereEqualTo("title", title)
            .limit(1)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }

                val codelab = snapshot
                    ?.documents
                    ?.firstOrNull()
                    ?.let { document ->
                        document.toObject(CodelabEntity::class.java)
                            ?.toCodelab(id = document.id)
                    }

                trySend(codelab)
            }

        awaitClose { listener.remove() }
    }
}