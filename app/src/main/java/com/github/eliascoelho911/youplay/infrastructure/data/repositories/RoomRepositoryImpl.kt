package com.github.eliascoelho911.youplay.infrastructure.data.repositories

import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.common.callbackFlowResource
import com.github.eliascoelho911.youplay.common.emitErrors
import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import com.github.eliascoelho911.youplay.infrastructure.data.documents.RoomDocument
import com.github.eliascoelho911.youplay.infrastructure.data.mappers.toDocument
import com.github.eliascoelho911.youplay.infrastructure.data.mappers.toEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.tasks.await

private const val CollectionName = "room"

class RoomRepositoryImpl(
    db: FirebaseFirestore,
) : RoomRepository {
    private val collection = db.collection(CollectionName)

    override fun fetchRoomById(id: ID) = callbackFlowResource<Room> {
        collection.document(id).addSnapshotListener { snapshot, error ->
            if (error != null)
                cancel(message = error.message.toString(), cause = error)
            else if (snapshot != null && snapshot.exists()) {
                val roomDocument = snapshot.toObject(RoomDocument::class.java)!!
                trySend(Resource.success(roomDocument.toEntity(id)))
            }
        }.let { subscription ->
            awaitClose { subscription.remove() }
        }
    }.emitErrors()

    override suspend fun add(room: Room) {
        collection.document(room.id).set(room.toDocument()).await()
    }
}