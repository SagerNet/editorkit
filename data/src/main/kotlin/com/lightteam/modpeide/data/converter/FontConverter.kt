/*
 * Licensed to the Light Team Software (Light Team) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Light Team licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lightteam.modpeide.data.converter

import com.lightteam.modpeide.data.feature.font.FontModel
import com.lightteam.modpeide.database.entity.font.FontEntity

object FontConverter {

    fun toModel(entity: FontEntity): FontModel {
        return FontModel(
            fontName = entity.fontName,
            fontPath = entity.fontPath,
            supportLigatures = entity.supportLigatures,
            isExternal = entity.isExternal,
            isPaid = entity.isPaid
        )
    }

    fun toEntity(fontModel: FontModel): FontEntity {
        return FontEntity(
            fontName = fontModel.fontName,
            fontPath = fontModel.fontPath,
            supportLigatures = fontModel.supportLigatures,
            isExternal = fontModel.isExternal,
            isPaid = fontModel.isPaid
        )
    }
}