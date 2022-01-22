package com.github.eliascoelho911.youplay.infrastructure.data.repositories

import com.github.eliascoelho911.youplay.util.Resource
import com.github.eliascoelho911.youplay.util.callbackFlowResource
import com.github.eliascoelho911.youplay.util.catchExceptions
import com.github.eliascoelho911.youplay.util.flowResource
import com.github.eliascoelho911.youplay.util.serializeToMap
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

    override fun observeRoomById(id: ID) = callbackFlowResource<Room> {
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
    }.catchExceptions()

    override fun getRoomById(id: ID) = flowResource<Room> {
        val document = collection.document(id).get().await()
        val entity = document.toObject(RoomDocument::class.java)!!.toEntity(id)
        emit(Resource.success(entity))
    }.catchExceptions()

    override suspend fun add(room: Room) {
        collection.document(room.id).set(room.toDocument()).await()
    }

    override suspend fun deleteRoomById(id: ID) {
        collection.document(id).delete().await()
    }

    override suspend fun updateRoom(room: Room) {
        collection.document(room.id).update(room.toDocument().serializeToMap())
    }
}