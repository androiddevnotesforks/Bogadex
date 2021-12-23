/*
 * Copyright (c) 2021, Boitakub
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of mosquitto nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package fr.boitakub.bogadex.boardgame.mapper

import fr.boitakub.architecture.Mapper
import fr.boitakub.bgg.client.UserBoardGame
import fr.boitakub.bgg.client.UserCollection
import fr.boitakub.bogadex.boardgame.model.CollectionItem
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionMapper @Inject constructor(private val statusMapper: BoardGameStatusMapper) :
    Mapper<fr.boitakub.bogadex.boardgame.model.Collection, UserCollection> {

    override fun map(source: UserCollection): fr.boitakub.bogadex.boardgame.model.Collection {
        return fr.boitakub.bogadex.boardgame.model.Collection(map(source.games))
    }

    private fun map(entity: UserBoardGame): CollectionItem =
        CollectionItem(
            entity.objectid,
            entity.name,
            entity.yearpublished,
            entity.thumbnail,
            Date(),
            statusMapper.map(entity.status)
        )

    private fun map(list: List<UserBoardGame>): List<CollectionItem> = list.map { map(it) }
}
